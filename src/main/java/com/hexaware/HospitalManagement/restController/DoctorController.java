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

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

    @Autowired
    IDoctorService doctorService;

    @PostMapping("/register")
    public ResponseEntity<Doctor> registerDoctor(@RequestBody DoctorDTO dto) throws UserNotFoundException, InvalidRoleException, DuplicateDoctorException {
        Doctor doctor = doctorService.registerDoctor(dto);
        return new ResponseEntity<>(doctor, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @GetMapping("/getById/{id}")
    public Doctor getDoctorById(@PathVariable Long id) {
        return doctorService.getDoctorById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id, @RequestBody DoctorDTO dto) throws UserNotFoundException {
        Doctor updatedDoctor = doctorService.updateDoctor(id, dto);
        return ResponseEntity.ok(updatedDoctor);
    }
    @DeleteMapping("/deleteById/{id}")
    public String deleteDoctor(@PathVariable Long id) {
        if (doctorService.deleteDoctor(id)) {
            return "Doctor deleted successfully";
        }
        return "Invalid doctor ID";
    }

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

    // Placeholder endpoints for advanced features

    @GetMapping("/{doctorId}/appointments")
    public List<Appointment> getAppointments(@PathVariable Long doctorId) {
        return doctorService.getAppointments(doctorId);
    }

    @GetMapping("/{doctorId}/appointments/upcoming")
    public List<Appointment> getUpcomingAppointments(@PathVariable Long doctorId) {
        return doctorService.getUpcomingAppointments(doctorId);
    }

    @PostMapping("/addMedicalRecord/{appointmentId}")
    public MedicalRecord addMedicalRecord(@PathVariable Long appointmentId, @RequestBody MedicalRecordDTO medicalRecordDTO) throws AppointmentNotFoundException {
        return doctorService.addMedicalRecord(medicalRecordDTO);
    }

    @PutMapping("/updateMedicalRecord/{recordId}")
    public MedicalRecord updateMedicalRecord(@PathVariable Long recordId, @RequestBody MedicalRecordDTO updatedRecordDTO) throws MedicalRecordNotFoundException, AppointmentNotFoundException {
        return doctorService.updateMedicalRecord(updatedRecordDTO);
    }

    @PostMapping("/addPrescription")
    public Prescription addPrescription(@RequestBody PrescriptionDTO prescription) throws MedicalRecordNotFoundException {
        return doctorService.addPrescription(prescription);
    }

    @PutMapping("/updatePrescription/{prescriptionId}")
    public Prescription updatePrescription(@PathVariable Long prescriptionId, @RequestBody PrescriptionDTO updatedPrescription) throws PrescriptionNotFoundException {
        return doctorService.updatePrescription(updatedPrescription);
    }


    @GetMapping("/patientHistory/{patientId}")
    public List<MedicalRecord> getPatientMedicalHistory(@PathVariable Long patientId) {
        return doctorService.getPatientMedicalHistory(patientId);
    }

    @PostMapping("/sendMessage/{doctorId}/{patientId}")
    public boolean sendMessage(@PathVariable Long doctorId, @PathVariable Long patientId, @RequestBody String messageContent) {
        return doctorService.sendMessage(doctorId, patientId, messageContent);
    }

    @GetMapping("/messages/{doctorId}/{patientId}")
    public List<Message> getMessagesBetweenDoctorAndPatient(@PathVariable Long doctorId, @PathVariable Long patientId) {
        return doctorService.getMessagesBetweenDoctorAndPatient(doctorId, patientId);
    }
}
