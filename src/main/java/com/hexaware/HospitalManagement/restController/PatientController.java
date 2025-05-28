package com.hexaware.HospitalManagement.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.HospitalManagement.DTO.AppointmentDTO;
import com.hexaware.HospitalManagement.DTO.MessageDTO;
import com.hexaware.HospitalManagement.DTO.PatientDTO;
import com.hexaware.HospitalManagement.entity.Appointment;
import com.hexaware.HospitalManagement.entity.MedicalRecord;
import com.hexaware.HospitalManagement.entity.Message;
import com.hexaware.HospitalManagement.entity.Patient;
import com.hexaware.HospitalManagement.entity.Prescription;
import com.hexaware.HospitalManagement.exception.AppointmentNotFoundException;
import com.hexaware.HospitalManagement.exception.DuplicatePatientException;
import com.hexaware.HospitalManagement.exception.InvalidRoleException;
import com.hexaware.HospitalManagement.exception.UserNotFoundException;
import com.hexaware.HospitalManagement.service.IPatientService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/patients")
@Slf4j
public class PatientController {

    @Autowired
    IPatientService patientService;

    @PostMapping("/register")
    public ResponseEntity<Patient> registerPatient(@RequestBody PatientDTO patientDto)
            throws UserNotFoundException, DuplicatePatientException, InvalidRoleException {
        log.info("Registering new patient");
        Patient saved = patientService.registerPatient(patientDto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) throws UserNotFoundException {
        log.info("Fetching patient by ID: {}", id);
        Patient patient = patientService.getPatientById(id);
        if (patient == null) {
            throw new UserNotFoundException("Patient not found with ID: " + id);
        }
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody PatientDTO dto) {
        log.info("Updating patient with ID: {}", id);
        Patient updated = patientService.updatePatient(id, dto);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable Long id) throws UserNotFoundException {
        log.info("Deleting patient with ID: {}", id);
        boolean deleted = patientService.deletePatient(id);
        if (deleted) {
            log.info("Patient deleted successfully: {}", id);
            return new ResponseEntity<>("Patient deleted successfully", HttpStatus.OK);
        } else {
            log.warn("Deletion failed for patient ID: {}", id);
            throw new UserNotFoundException("Patient not found with ID: " + id);
        }
    }

    @GetMapping("/getall")
    public List<Patient> getAllPatients() {
        log.info("Fetching all patients");
        return patientService.getAllPatients();
    }

    @GetMapping("/search/name/{name}")
    public List<Patient> searchPatientsByName(@PathVariable String name) {
        log.info("Searching patients by name: {}", name);
        return patientService.searchPatientsByName(name);
    }

    @GetMapping("/search/bloodgroup/{bloodGroup}")
    public List<Patient> searchPatientsByBloodGroup(@PathVariable String bloodGroup) {
        log.info("Searching patients by blood group: {}", bloodGroup);
        return patientService.searchPatientsByBloodGroup(bloodGroup);
    }

    @PostMapping("/bookAppointment")
    public ResponseEntity<Appointment> bookAppointment(@RequestBody AppointmentDTO dto)
            throws AppointmentNotFoundException {
        log.info("Booking appointment for patientId: {}", dto.getPatientId());
        Appointment appointment = patientService.bookAppointment(dto);
        return new ResponseEntity<>(appointment, HttpStatus.CREATED);
    }

    @DeleteMapping("/cancelAppointment/{id}")
    public ResponseEntity<String> cancelAppointment(@PathVariable Long id) throws AppointmentNotFoundException {
        log.info("Cancelling appointment with ID: {}", id);
        boolean cancelled = patientService.cancelAppointment(id);
        if (cancelled) {
            log.info("Appointment cancelled successfully: {}", id);
            return new ResponseEntity<>("Appointment cancelled successfully", HttpStatus.OK);
        } else {
            log.warn("Appointment cancellation failed: {}", id);
            return new ResponseEntity<>("Appointment cancellation failed", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/appointments/upcoming/{id}")
    public List<Appointment> getUpcomingAppointments(@PathVariable Long id) {
        log.info("Fetching upcoming appointments for patientId: {}", id);
        return patientService.getUpcomingAppointments(id);
    }

    @GetMapping("/medicalRecords/{patientid}")
    public List<MedicalRecord> getMedicalRecords(@PathVariable Long patientid) {
        log.info("Fetching medical records for patientId: {}", patientid);
        return patientService.getMedicalRecordsByPatientId(patientid);
    }

    @GetMapping("/Prescription/patientId/{patientId}")
    public List<Prescription> getPrescriptions(@PathVariable Long patientId) {
        log.info("Fetching prescriptions for patientId: {}", patientId);
        return patientService.getPrescriptionsByPatientId(patientId);
    }

    @GetMapping("/Prescription/AppointmentId/{appointmentId}")
    public List<Prescription> getPrescriptionsByAppointmentId(@PathVariable Long appointmentId) {
        log.info("Fetching prescriptions for appointmentId: {}", appointmentId);
        return patientService.getPrescriptionsByAppointmentId(appointmentId);
    }
    

    @PostMapping("/sendMessage")
    public Message sendMessage(@RequestBody MessageDTO messageDTO) {
        log.info("Sending message from patientId: {}", messageDTO.getPatientId());
        return patientService.sendMessage(messageDTO);
    }

    @GetMapping("/messages/between/{doctorId}/{patientId}")
    public List<Message> getMessagesBetweenDoctorAndPatient(@PathVariable int doctorId, @PathVariable int patientId) {
        log.info("Fetching messages between doctorId: {} and patientId: {}", doctorId, patientId);
        return patientService.getMessagesBetweenDoctorAndPatient(doctorId, patientId);
    }

    @GetMapping("/messages/unread/{patientId}")
    public List<Message> getUnreadMessagesForPatient(@PathVariable int patientId) {
        log.info("Fetching unread messages for patientId: {}", patientId);
        return patientService.getUnreadMessagesForPatient(patientId);
    }

    @PutMapping("/messages/markAsRead/{messageId}")
    public String markMessageAsRead(@PathVariable int messageId) {
        log.info("Marking message as read: {}", messageId);
        boolean updated = patientService.markMessageAsRead(messageId);
        return updated ? "Message marked as read" : "Message not found or already read";
    }

    @GetMapping("/messages/sentByPatient/{patientId}")
    public List<Message> getMessagesSentByPatient(@PathVariable int patientId) {
        log.info("Fetching messages sent by patientId: {}", patientId);
        return patientService.getMessagesSentByPatient(patientId);
    }

}
