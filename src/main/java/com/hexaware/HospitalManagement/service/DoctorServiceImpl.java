package com.hexaware.HospitalManagement.service;
/**
 * Service implementation for managing doctor operations in the Hospital Management System.
 * @author Aathi Pranavika
 * @version 1.0
 */
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hexaware.HospitalManagement.DTO.DoctorDTO;
import com.hexaware.HospitalManagement.DTO.MedicalRecordDTO;
import com.hexaware.HospitalManagement.DTO.MessageDTO;
import com.hexaware.HospitalManagement.DTO.PrescriptionDTO;
import com.hexaware.HospitalManagement.entity.Appointment;
import com.hexaware.HospitalManagement.entity.Doctor;
import com.hexaware.HospitalManagement.entity.MedicalRecord;
import com.hexaware.HospitalManagement.entity.Message;
import com.hexaware.HospitalManagement.entity.Patient;
import com.hexaware.HospitalManagement.entity.Prescription;
import com.hexaware.HospitalManagement.entity.User;
import com.hexaware.HospitalManagement.exception.AppointmentNotFoundException;
import com.hexaware.HospitalManagement.exception.DoctorNotFoundException;
import com.hexaware.HospitalManagement.exception.DuplicateDoctorException;
import com.hexaware.HospitalManagement.exception.MedicalRecordNotFoundException;
import com.hexaware.HospitalManagement.exception.PrescriptionNotFoundException;
import com.hexaware.HospitalManagement.exception.UserNotFoundException;
import com.hexaware.HospitalManagement.repository.AppointmentRepository;
import com.hexaware.HospitalManagement.repository.DoctorRepository;
import com.hexaware.HospitalManagement.repository.PatientRepository;
import com.hexaware.HospitalManagement.repository.UserRepository;

@Service
public class DoctorServiceImpl implements IDoctorService {

	@Autowired
	DoctorRepository doctorRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	AppointmentRepository AppointmentRepo;

	@Autowired
	IMedicalRecordService MedicalRecordService;

	@Autowired
	IPrescriptionService PrescriptionService;

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private PatientRepository patientRepository;

	private final String messageServiceUrl = "http://localhost:8282/api/messages";

	@Override
	public Doctor registerDoctor(DoctorDTO dto) throws UserNotFoundException, DuplicateDoctorException {
	    Optional<User> userOpt = userRepo.findById(dto.getUserId());
	    if (userOpt.isEmpty()) {
	        throw new UserNotFoundException("User not found");
	    }

	    User user = userOpt.get();

	    if (user.getRole() != User.Role.DOCTOR) {
	        throw new IllegalArgumentException("User role is not DOCTOR");
	    }

	    Optional<Doctor> existingDoctor = doctorRepo.findById(dto.getUserId());
	    if (existingDoctor.isPresent()) {
	        throw new DuplicateDoctorException("Doctor already registered with this userId");
	    }

	    Doctor doctor = new Doctor();
	    doctor.setUser(user);
	    doctor.setSpecialization(dto.getSpecialization());
	    doctor.setExperienceYears(dto.getExperienceYears());
	    doctor.setQualification(dto.getQualification());
	    doctor.setDesignation(dto.getDesignation());

	    return doctorRepo.save(doctor);
	}


	@Override
	public Doctor updateDoctor(Long id, DoctorDTO dto) throws UserNotFoundException {
		Optional<Doctor> existing = doctorRepo.findById(id);
		if (existing.isEmpty()) {
			throw new DoctorNotFoundException("Doctor not found");
		}

		Optional<User> userOpt = userRepo.findById(dto.getUserId());
		if (userOpt.isEmpty()) {
			throw new UserNotFoundException("User not found");
		}

		Doctor updated = new Doctor();
		updated.setDoctorId(id);
		updated.setUser(userOpt.get());
		updated.setSpecialization(dto.getSpecialization());
		updated.setExperienceYears(dto.getExperienceYears());
		updated.setQualification(dto.getQualification());
		updated.setDesignation(dto.getDesignation());

		return doctorRepo.save(updated);
	}

	@Override
	public Doctor getDoctorById(Long doctorId) {
		return doctorRepo.findById(doctorId).orElse(null);
	}

	@Override
	public boolean deleteDoctor(Long doctorId) {
		if (doctorRepo.existsById(doctorId)) {
			doctorRepo.deleteById(doctorId);
			return true;
		}
		return false;
	}

	@Override
	public boolean checkDoctorExistsById(Long doctorId) {
		return doctorRepo.existsById(doctorId);
	}

	@Override
	public List<Doctor> getDoctorsBySpecialization(String specialization) {
		return doctorRepo.findBySpecialization(specialization);
	}

	@Override
	public List<Doctor> getDoctorsByDesignation(String designation) {
		return doctorRepo.findByDesignation(designation);
	}

	@Override
	public List<Doctor> getDoctorsByGender(String gender) {
		return doctorRepo.getDoctorsByGender(gender);
	}

	@Override
	public List<Doctor> getAllDoctors() {
		return doctorRepo.findAll();
	}

	@Override
	public List<Doctor> searchDoctorsByName(String name) {
		return doctorRepo.searchDoctorsByName(name);
	}

	@Override
	public List<Appointment> getAppointments(Long doctorId) {
		return AppointmentRepo.findByDoctorDoctorId(doctorId);
	}

	@Override
	public List<Appointment> getUpcomingAppointments(Long doctorId) {
		return AppointmentRepo.findByDoctorDoctorIdAndAppointmentDateAfter(doctorId, LocalDateTime.now());
	}

	@Override
	public MedicalRecord addMedicalRecord(MedicalRecordDTO medicalRecordDTO) throws AppointmentNotFoundException {
        System.out.println( " creating MedicalRecord object: ");

		return MedicalRecordService.createMedicalRecord(medicalRecordDTO);

	}

	@Override
	public MedicalRecord updateMedicalRecord(MedicalRecordDTO updatedRecord)
			throws MedicalRecordNotFoundException, AppointmentNotFoundException {
		return MedicalRecordService.updateMedicalRecord(updatedRecord);

	}

	@Override
	public List<MedicalRecord> getPatientMedicalHistory(Long patientId) {
		return MedicalRecordService.getMedicalRecordsByPatientId(patientId);
	}

	@Override
	public Prescription addPrescription(PrescriptionDTO prescriptionDTO) throws MedicalRecordNotFoundException {
		return PrescriptionService.createPrescription(prescriptionDTO);

	}

	@Override
	public Prescription updatePrescription(PrescriptionDTO prescriptionDTO) throws PrescriptionNotFoundException {
		return PrescriptionService.updatePrescription(prescriptionDTO);
	}
	
	@Override
	public Message sendMessage(MessageDTO messageDTO) {
	    //  Validate doctor exists
		doctorRepo.findById((long) messageDTO.getDoctorId());

	    //  Validate patient exists
	    patientRepository.findById((long) messageDTO.getPatientId())
	            .orElseThrow(() -> new NoSuchElementException("Patient not found"));

	    String url = messageServiceUrl + "/send";
	    return restTemplate.postForObject(url, messageDTO, Message.class);
	}

	@Override
	public List<Message> getMessagesBetweenDoctorAndPatient(int doctorId, int patientId) {
	    //  Validate doctor
		doctorRepo.findById((long) doctorId);

	    // Validate patient
	    patientRepository.findById((long) patientId)
	            .orElseThrow(() -> new NoSuchElementException("Patient not found"));

	    String url = messageServiceUrl + "/messageBetween/doctor/" + doctorId + "/patient/" + patientId;
	    Message[] messages = restTemplate.getForObject(url, Message[].class);
	    return messages == null ? new ArrayList<>() : Arrays.asList(messages);
	}

	@Override
	public List<Message> getUnreadMessagesForDoctor(int doctorId) {
	    //  Validate doctor
		doctorRepo.findById((long) doctorId);

	    String url = messageServiceUrl + "/unread/doctor/" + doctorId;
	    Message[] messages = restTemplate.getForObject(url, Message[].class);
	    return messages == null ? new ArrayList<>() : Arrays.asList(messages);
	}

	@Override
	public boolean markMessageAsRead(int messageId) {
	    try {
	        String getUrl = messageServiceUrl + "/getbyid/" + messageId;
	        Message message = restTemplate.getForObject(getUrl, Message.class);

	        if (message == null) {
	            throw new NoSuchElementException("Message not found");
	        }

	        // Validate doctor existence
	        doctorRepo.findById(message.getDoctorId());

	        String markUrl = messageServiceUrl + "/mark-read/" + messageId;
	        restTemplate.put(markUrl, messageId);

	        return true;
	    } catch (Exception e) {
	        System.err.println("Failed to mark message as read: " + e.getMessage());
	        return false;
	    }
	}

	@Override
	public List<Message> getMessagesSentByDoctor(int doctorId) {
		doctorRepo.findById((long) doctorId);

	    String url = messageServiceUrl + "/sent/doctor/" + doctorId;
	    Message[] messages = restTemplate.getForObject(url, Message[].class);
	    return messages == null ? new ArrayList<>() : Arrays.asList(messages);
	}


	@Override
	public List<Patient> searchPatientsByName(String name) {
		return patientRepository.findByUserNameContainingIgnoreCase(name);
	}


	@Override
	public List<Patient> searchPatientsByBloodGroup(String bloodGroup) {
		return patientRepository.findByBloodGroup(bloodGroup);
	}
}