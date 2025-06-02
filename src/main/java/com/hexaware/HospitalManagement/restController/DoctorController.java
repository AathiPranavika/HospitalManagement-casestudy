package com.hexaware.HospitalManagement.restController;
/**
* REST controller for doctors-related operations in the Hospital Management System.
* * 
* @author Aathi Pranavika
* @version 1.0
* */
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.hexaware.HospitalManagement.entity.Patient;
import com.hexaware.HospitalManagement.entity.Prescription;
import com.hexaware.HospitalManagement.service.IDoctorService;

import jakarta.validation.Valid;
@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    IDoctorService doctorService;

    @PreAuthorize("hasRole('DOCTOR') and #dto.userId == principal.id")
    @PostMapping("/register")
    public ResponseEntity<Doctor> registerDoctor(@Valid @RequestBody DoctorDTO dto) throws Exception {
        Doctor doctor = doctorService.registerDoctor(dto);
        return new ResponseEntity<>(doctor, HttpStatus.CREATED);
    }

    // Anyone can get all doctors
    @GetMapping("/getAll")
    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @GetMapping("/exists/{doctorId}")
    public ResponseEntity<Boolean> checkDoctorExists(@PathVariable Long doctorId) {
        boolean exists = doctorService.checkDoctorExistsById(doctorId);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    public Doctor getDoctorById(@PathVariable Long id) {
        return doctorService.getDoctorById(id);
    }

    // Only doctor owner can update their info
    @PreAuthorize("@userAccessService.userOwnsDoctor(principal.id, #id)")
    @PutMapping("/update/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id, @Valid @RequestBody DoctorDTO dto) throws Exception {
        Doctor updatedDoctor = doctorService.updateDoctor(id, dto);
        return ResponseEntity.ok(updatedDoctor);
    }

    // Only doctor owner can delete doctor record
    @PreAuthorize("@userAccessService.userOwnsDoctor(principal.id, #id)")
    @DeleteMapping("/deleteById/{id}")
    public String deleteDoctor(@PathVariable Long id) {
        if (doctorService.deleteDoctor(id)) {
            return "Doctor deleted successfully";
        }
        return "Invalid doctor ID";
    }

    // Open access searches by specialization, designation, gender
    @GetMapping("/bySpecialization/{specialization}")
    public List<Doctor> getDoctorsBySpecialization(@PathVariable String specialization) {
        return doctorService.getDoctorsBySpecialization(specialization);
    }

    @GetMapping("/byDesignation/{designation}")
    public List<Doctor> getDoctorsByDesignation(@PathVariable String designation) {
        return doctorService.getDoctorsByDesignation(designation);
    }

    @GetMapping("/byGender/{gender}")
    public List<Doctor> getDoctorsByGender(@PathVariable String gender) {
        return doctorService.getDoctorsByGender(gender);
    }

    @GetMapping("/searchByName/{name}")
    public List<Doctor> searchDoctorsByName(@PathVariable String name) {
        return doctorService.searchDoctorsByName(name);
    }

    // Only doctor owner can get their appointments
    @PreAuthorize("@userAccessService.userOwnsDoctor(principal.id, #doctorId)")
    @GetMapping("/{doctorId}/appointments")
    public List<Appointment> getAppointments(@PathVariable Long doctorId) {
        return doctorService.getAppointments(doctorId);
    }

    // Only doctor owner can get upcoming appointments
    @PreAuthorize("@userAccessService.userOwnsDoctor(principal.id, #doctorId)")
    @GetMapping("/{doctorId}/appointments/upcoming")
    public List<Appointment> getUpcomingAppointments(@PathVariable Long doctorId) {
        return doctorService.getUpcomingAppointments(doctorId);
    }

    // Only doctor owner can add medical record for their appointment
    @PreAuthorize("@userAccessService.doctorUserOwnsAppointment(principal.id, #medicalRecordDTO.appointmentId)")
    @PostMapping("/addMedicalRecord/{appointmentId}")
    public MedicalRecord addMedicalRecord(@PathVariable Long appointmentId,
                                          @RequestBody MedicalRecordDTO medicalRecordDTO) throws Exception {
        System.out.println(" controller object: " );

        return doctorService.addMedicalRecord(medicalRecordDTO);
    }

    // Only doctor owner can update medical record
    @PreAuthorize("@userAccessService.userOwnsMedicalRecord(principal.id, #recordId)")
    @PutMapping("/updateMedicalRecord/{recordId}")
    public MedicalRecord updateMedicalRecord(@PathVariable Long recordId,
                                             @RequestBody MedicalRecordDTO updatedRecordDTO) throws Exception {
        return doctorService.updateMedicalRecord(updatedRecordDTO);
    }

    // Only doctor owner can add prescription
    @PreAuthorize("@userAccessService.userOwnsMedicalRecord(principal.id, #recordId)")
    @PostMapping("/addPrescription/{recordId}")
    public Prescription addPrescription(@PathVariable Long recordId, @Valid @RequestBody PrescriptionDTO prescription) throws Exception {
        return doctorService.addPrescription(prescription);
    }

    // Only doctor owner can update prescription
    @PreAuthorize("@userAccessService.userOwnsPrescription(principal.id, #prescriptionId)")
    @PutMapping("/updatePrescription/{prescriptionId}")
    public Prescription updatePrescription(@PathVariable Long prescriptionId,
                                           @RequestBody PrescriptionDTO updatedPrescription) throws Exception {
        return doctorService.updatePrescription(updatedPrescription);
    }

    // Open access for patient history (could restrict to doctor owner or patient owner if needed)
    @GetMapping("/patientHistory/{patientId}")
    public List<MedicalRecord> getPatientMedicalHistory(@PathVariable Long patientId) {
        return doctorService.getPatientMedicalHistory(patientId);
    }

    // Only doctor owner can send message
    @PreAuthorize("@userAccessService.userOwnsDoctor(principal.id, #messageDTO.doctorId)")
    @PostMapping("/sendMessage")
    public Message sendMessage(@RequestBody MessageDTO messageDTO) {
        return doctorService.sendMessage(messageDTO);
    }

    // Only doctor owner can get messages between doctor and patient
    @PreAuthorize("@userAccessService.userOwnsDoctor(principal.id, #doctorId)")
    @GetMapping("/messages/{doctorId}/{patientId}")
    public List<Message> getMessagesBetweenDoctorAndPatient(@PathVariable int doctorId,
                                                           @PathVariable int patientId) {
        return doctorService.getMessagesBetweenDoctorAndPatient(doctorId, patientId);
    }

    // Only doctor owner can get unread messages
    @PreAuthorize("@userAccessService.userOwnsDoctor(principal.id, #doctorId)")
    @GetMapping("/unreadMessages/{doctorId}")
    public List<Message> getUnreadMessagesForDoctor(@PathVariable int doctorId) {
        return doctorService.getUnreadMessagesForDoctor(doctorId);
    }

    // Only doctor owner can get sent messages
    @PreAuthorize("@userAccessService.userOwnsDoctor(principal.id, #doctorId)")
    @GetMapping("/sentMessages/{doctorId}")
    public List<Message> getMessagesSentByDoctor(@PathVariable int doctorId) {
        return doctorService.getMessagesSentByDoctor(doctorId);
    }

    // Open access or restrict as needed for patient search by doctor
    @GetMapping("/patient/searchByName/{name}")
    public List<Patient> searchPatientsByName(@PathVariable String name) {
        return doctorService.searchPatientsByName(name);
    }

    @GetMapping("/searchByBloodGroup/{bloodGroup}")
    public List<Patient> searchPatientsByBloodGroup(@PathVariable String bloodGroup) {
        return doctorService.searchPatientsByBloodGroup(bloodGroup);
    }
}
