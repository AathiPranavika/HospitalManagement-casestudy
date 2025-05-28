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

import com.hexaware.HospitalManagement.DTO.DoctorDTO;
import com.hexaware.HospitalManagement.DTO.MedicalRecordDTO;
import com.hexaware.HospitalManagement.DTO.MessageDTO;
import com.hexaware.HospitalManagement.DTO.PrescriptionDTO;
import com.hexaware.HospitalManagement.entity.Appointment;
import com.hexaware.HospitalManagement.entity.Doctor;
import com.hexaware.HospitalManagement.entity.MedicalRecord;
import com.hexaware.HospitalManagement.entity.Message;
import com.hexaware.HospitalManagement.entity.Prescription;
import com.hexaware.HospitalManagement.exception.AppointmentNotFoundException;
import com.hexaware.HospitalManagement.exception.DuplicateDoctorException;
import com.hexaware.HospitalManagement.exception.InvalidRoleException;
import com.hexaware.HospitalManagement.exception.MedicalRecordNotFoundException;
import com.hexaware.HospitalManagement.exception.PrescriptionNotFoundException;
import com.hexaware.HospitalManagement.exception.UserNotFoundException;
import com.hexaware.HospitalManagement.service.IDoctorService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/doctor")
@Slf4j
public class DoctorController {

    @Autowired
    IDoctorService doctorService;

    @PostMapping("/register")
    public ResponseEntity<Doctor> registerDoctor(@RequestBody DoctorDTO dto)
            throws UserNotFoundException, InvalidRoleException, DuplicateDoctorException {
        log.info("Registering doctor with userId: {}", dto.getUserId());
        Doctor doctor = doctorService.registerDoctor(dto);
        return new ResponseEntity<>(doctor, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public List<Doctor> getAllDoctors() {
        log.info("Fetching all doctors");
        return doctorService.getAllDoctors();
    }

    @GetMapping("/getById/{id}")
    public Doctor getDoctorById(@PathVariable Long id) {
        log.info("Fetching doctor with ID: {}", id);
        return doctorService.getDoctorById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id, @RequestBody DoctorDTO dto)
            throws UserNotFoundException {
        log.info("Updating doctor with ID: {}", id);
        Doctor updatedDoctor = doctorService.updateDoctor(id, dto);
        return ResponseEntity.ok(updatedDoctor);
    }

    @DeleteMapping("/deleteById/{id}")
    public String deleteDoctor(@PathVariable Long id) {
        log.info("Deleting doctor with ID: {}", id);
        if (doctorService.deleteDoctor(id)) {
            return "Doctor deleted successfully";
        }
        return "Invalid doctor ID";
    }

    @GetMapping("/bySpecialization/{specialization}")
    public List<Doctor> getDoctorsBySpecialization(@PathVariable String specialization) {
        log.info("Searching doctors by specialization: {}", specialization);
        return doctorService.getDoctorsBySpecialization(specialization);
    }

    @GetMapping("/byDesignation/{designation}")
    public List<Doctor> getDoctorsByDesignation(@PathVariable String designation) {
        log.info("Searching doctors by designation: {}", designation);
        return doctorService.getDoctorsByDesignation(designation);
    }

    @GetMapping("/byGender/{gender}")
    public List<Doctor> getDoctorsByGender(@PathVariable String gender) {
        log.info("Searching doctors by gender: {}", gender);
        return doctorService.getDoctorsByGender(gender);
    }

    @GetMapping("/searchByName/{name}")
    public List<Doctor> searchDoctorsByName(@PathVariable String name) {
        log.info("Searching doctors by name: {}", name);
        return doctorService.searchDoctorsByName(name);
    }

    @GetMapping("/{doctorId}/appointments")
    public List<Appointment> getAppointments(@PathVariable Long doctorId) {
        log.info("Fetching all appointments for doctorId: {}", doctorId);
        return doctorService.getAppointments(doctorId);
    }

    @GetMapping("/{doctorId}/appointments/upcoming")
    public List<Appointment> getUpcomingAppointments(@PathVariable Long doctorId) {
        log.info("Fetching upcoming appointments for doctorId: {}", doctorId);
        return doctorService.getUpcomingAppointments(doctorId);
    }

    @PostMapping("/addMedicalRecord/{appointmentId}")
    public MedicalRecord addMedicalRecord(@PathVariable Long appointmentId,
                                          @RequestBody MedicalRecordDTO medicalRecordDTO)
            throws AppointmentNotFoundException {
        log.info("Adding medical record for appointmentId: {}", appointmentId);
        return doctorService.addMedicalRecord(medicalRecordDTO);
    }

    @PutMapping("/updateMedicalRecord/{recordId}")
    public MedicalRecord updateMedicalRecord(@PathVariable Long recordId,
                                             @RequestBody MedicalRecordDTO updatedRecordDTO)
            throws MedicalRecordNotFoundException, AppointmentNotFoundException {
        log.info("Updating medical record with ID: {}", recordId);
        return doctorService.updateMedicalRecord(updatedRecordDTO);
    }

    @PostMapping("/addPrescription")
    public Prescription addPrescription(@RequestBody PrescriptionDTO prescription)
            throws MedicalRecordNotFoundException {
        log.info("Adding prescription for medicalRecordId: {}", prescription.getMedicalRecordId());
        return doctorService.addPrescription(prescription);
    }

    @PutMapping("/updatePrescription/{prescriptionId}")
    public Prescription updatePrescription(@PathVariable Long prescriptionId,
                                           @RequestBody PrescriptionDTO updatedPrescription)
            throws PrescriptionNotFoundException {
        log.info("Updating prescription with ID: {}", prescriptionId);
        return doctorService.updatePrescription(updatedPrescription);
    }

    @GetMapping("/patientHistory/{patientId}")
    public List<MedicalRecord> getPatientMedicalHistory(@PathVariable Long patientId) {
        log.info("Fetching medical history for patientId: {}", patientId);
        return doctorService.getPatientMedicalHistory(patientId);
    }

    @PostMapping("/sendMessage")
    public Message sendMessage(@RequestBody MessageDTO messageDTO) {
        log.info("Doctor sending message to patientId: {}", messageDTO.getPatientId());
        return doctorService.sendMessage(messageDTO);
    }

    @GetMapping("/messages/{doctorId}/{patientId}")
    public List<Message> getMessagesBetweenDoctorAndPatient(@PathVariable int doctorId,
                                                            @PathVariable int patientId) {
        log.info("Fetching messages between doctorId {} and patientId {}", doctorId, patientId);
        return doctorService.getMessagesBetweenDoctorAndPatient(doctorId, patientId);
    }

    @GetMapping("/unreadMessages/{doctorId}")
    public List<Message> getUnreadMessagesForDoctor(@PathVariable int doctorId) {
        log.info("Fetching unread messages for doctorId: {}", doctorId);
        return doctorService.getUnreadMessagesForDoctor(doctorId);
    }

    @PutMapping("/markAsRead/{messageId}")
    public String markMessageAsRead(@PathVariable int messageId) {
        log.info("Marking message as read. Message ID: {}", messageId);
        boolean marked = doctorService.markMessageAsRead(messageId);
        return marked ? "Message marked as read" : "Message not found or already marked";
    }

    @GetMapping("/sentMessages/{doctorId}")
    public List<Message> getMessagesSentByDoctor(@PathVariable int doctorId) {
        log.info("Fetching sent messages for doctorId: {}", doctorId);
        return doctorService.getMessagesSentByDoctor(doctorId);
    }

}
