package com.hexaware.HospitalManagement.service;
/**
 * Service implementation for managing patient operations in the Hospital Management System.
 * @author Aathi Pranavika
 * @version 1.0
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hexaware.HospitalManagement.DTO.AppointmentDTO;
import com.hexaware.HospitalManagement.DTO.MessageDTO;
import com.hexaware.HospitalManagement.DTO.PatientDTO;
import com.hexaware.HospitalManagement.entity.Appointment;
import com.hexaware.HospitalManagement.entity.Doctor;
import com.hexaware.HospitalManagement.entity.MedicalRecord;
import com.hexaware.HospitalManagement.entity.Message;
import com.hexaware.HospitalManagement.entity.Patient;
import com.hexaware.HospitalManagement.entity.Prescription;
import com.hexaware.HospitalManagement.entity.User;
import com.hexaware.HospitalManagement.exception.AppointmentNotFoundException;
import com.hexaware.HospitalManagement.exception.DuplicatePatientException;
import com.hexaware.HospitalManagement.exception.UserNotFoundException;
import com.hexaware.HospitalManagement.repository.PatientRepository;
import com.hexaware.HospitalManagement.repository.UserRepository;

@Service
public class PatientServiceImpl implements IPatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserAccessService userAccessService;

    @Autowired
    private IDoctorService doctorService;

    @Autowired
    private IAppointmentService appointmentService;

    @Autowired
    private IPrescriptionService prescriptionService;

    @Autowired
    private IMedicalRecordService medicalRecordService;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RestTemplate restTemplate;

    private final String messageServiceUrl = "http://localhost:8282/api/messages";

    // Register patient
    @Override
    public Patient registerPatient(PatientDTO dto) throws DuplicatePatientException, UserNotFoundException {
        Optional<Patient> existing = patientRepository.findById(dto.getUserId());
        if (existing.isPresent()) {
            throw new DuplicatePatientException("Patient with this userId already exists");
        }
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        if (user.getRole() != User.Role.PATIENT) {
            throw new IllegalArgumentException("User role is not PATIENT");
        }

        Patient patient = new Patient();
        patient.setUser(user);
        patient.setAddress(dto.getAddress());
        patient.setEmergencyContact(dto.getEmergencyContact());
        patient.setBloodGroup(dto.getBloodGroup());
        patient.setMedicalHistory(dto.getMedicalHistory());

        return patientRepository.save(patient);
    }

    // Update patient details
    @Override
    public Patient updatePatient(Long patientId, PatientDTO dto) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new NoSuchElementException("Patient not found"));

        patient.setAddress(dto.getAddress());
        patient.setEmergencyContact(dto.getEmergencyContact());
        patient.setBloodGroup(dto.getBloodGroup());
        patient.setMedicalHistory(dto.getMedicalHistory());

        return patientRepository.save(patient);
    }

    @Override
    public Patient getPatientById(Long patientId) {
        return patientRepository.findById(patientId)
                .orElseThrow(() -> new NoSuchElementException("Patient not found"));
    }

    // Delete patient
    @Override
    public boolean deletePatient(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new NoSuchElementException("Patient not found"));

        patientRepository.delete(patient);
        return true;
    }

    @Override
    public boolean checkPatientExistsById(Long patientId) {
        return patientRepository.existsById(patientId);
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public List<Patient> searchPatientsByName(String name) {
        return patientRepository.findByUserNameContainingIgnoreCase(name);
    }

    @Override
    public List<Patient> searchPatientsByBloodGroup(String bloodGroup) {
        return patientRepository.findByBloodGroup(bloodGroup);
    }

    // Book appointment
    @Override
    public Appointment bookAppointment(AppointmentDTO dto) throws AppointmentNotFoundException {
        return appointmentService.bookAppointment(dto);
    }

    // Get upcoming appointments
    @Override
    public List<Appointment> getUpcomingAppointments(Long patientId) {
        return appointmentService.getUpcomingAppointmentsForPatient(patientId);
    }

    // Cancel appointment
    @Override
    public boolean cancelAppointment(Long appointmentId) throws AppointmentNotFoundException {
        return appointmentService.cancelAppointmentById(appointmentId) != null;
    }

    // Get medical records by patient
    @Override
    public List<MedicalRecord> getMedicalRecordsByPatientId(Long patientId) {
        return medicalRecordService.getMedicalRecordsByPatientId(patientId);
    }

    // Get prescriptions by patient
    @Override
    public List<Prescription> getPrescriptionsByPatientId(Long patientId) {
        return prescriptionService.getPrescriptionsByPatientId(patientId);
    }

    // Get doctors by specialization (public access)
    @Override
    public List<Doctor> getDoctorsBySpecialization(String specialization) {
        return doctorService.getDoctorsBySpecialization(specialization);
    }

    // Get doctors by designation (public access)
    @Override
    public List<Doctor> getDoctorsByDesignation(String designation) {
        return doctorService.getDoctorsByDesignation(designation);
    }

    // Get doctors by gender (public access)
    @Override
    public List<Doctor> getDoctorsByGender(String gender) {
        return doctorService.getDoctorsByGender(gender);
    }

    // Get all doctors (public access)
    @Override
    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    // Search doctors by name (public access)
    @Override
    public List<Doctor> searchDoctorsByName(String name) {
        return doctorService.searchDoctorsByName(name);
    }

    // Send message
    @Override
    public Message sendMessage(MessageDTO messageDTO) {
        doctorService.getDoctorById(messageDTO.getDoctorId());
        String url = messageServiceUrl + "/send";
        return restTemplate.postForObject(url, messageDTO, Message.class);
    }

    // Get messages between doctor and patient
    @Override
    public List<Message> getMessagesBetweenDoctorAndPatient(int doctorId, int patientId) {
        doctorService.getDoctorById((long) doctorId);
        String url = messageServiceUrl + "/messageBetween/doctor/" + doctorId + "/patient/" + patientId;
        Message[] messages = restTemplate.getForObject(url, Message[].class);
        return messages == null ? new ArrayList<>() : Arrays.asList(messages);
    }

    // Get unread messages for patient
    @Override
    public List<Message> getUnreadMessagesForPatient(int patientId) {
        String url = messageServiceUrl + "/unread/patient/" + patientId;
        Message[] messages = restTemplate.getForObject(url, Message[].class);
        return messages == null ? new ArrayList<>() : Arrays.asList(messages);
    }

    // Mark message as read
    @Override
    public boolean markMessageAsRead(int messageId) {
        try {
            String getByMessageIdUrl = messageServiceUrl + "/getbyid/" + messageId;
            Message message = restTemplate.getForObject(getByMessageIdUrl, Message.class);

            if (message == null) {
                throw new NoSuchElementException("Message not found");
            }

            String markUrl = messageServiceUrl + "/mark-read/" + messageId;
            restTemplate.put(markUrl, messageId);

            return true;

        } catch (Exception e) {
            System.err.println("Failed to mark message as read: " + e.getMessage());
            return false;
        }
    }

    // Get messages sent by patient
    @Override
    public List<Message> getMessagesSentByPatient(int patientId) {
        String url = messageServiceUrl + "/sent/patient/" + patientId;
        Message[] messages = restTemplate.getForObject(url, Message[].class);
        return messages == null ? new ArrayList<>() : Arrays.asList(messages);
    }
}
