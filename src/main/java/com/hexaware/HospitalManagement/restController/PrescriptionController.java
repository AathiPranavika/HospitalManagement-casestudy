package com.hexaware.HospitalManagement.restController;
/**
* REST controller for prescriptions-related operations in the Hospital Management System.
* * 
* @author Aathi Pranavika
* @version 1.0
* */
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hexaware.HospitalManagement.DTO.PrescriptionDTO;
import com.hexaware.HospitalManagement.entity.Prescription;
import com.hexaware.HospitalManagement.exception.MedicalRecordNotFoundException;
import com.hexaware.HospitalManagement.exception.PrescriptionNotFoundException;
import com.hexaware.HospitalManagement.service.IPrescriptionService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/prescriptions")
@Slf4j
public class PrescriptionController {

    @Autowired
    private IPrescriptionService prescriptionService;

    @PostMapping("/add")
    public Prescription createPrescription(@Valid @RequestBody PrescriptionDTO dto)
            throws MedicalRecordNotFoundException {
        log.info("Creating new prescription for medicalRecordId: {}", dto.getMedicalRecordId());
        return prescriptionService.createPrescription(dto);
    }

    @GetMapping("/getBy/{id}")
    public Prescription getPrescriptionById(@PathVariable Long id)
            throws PrescriptionNotFoundException {
        log.info("Fetching prescription with ID: {}", id);
        return prescriptionService.getPrescriptionById(id);
    }

    @PutMapping("/update")
    public Prescription updatePrescription(@Valid @RequestBody PrescriptionDTO dto)
            throws PrescriptionNotFoundException {
        log.info("Updating prescription with ID: {}", dto.getPrescriptionId());
        return prescriptionService.updatePrescription(dto);
    }

    @DeleteMapping("delete/{id}")
    public String deletePrescription(@PathVariable Long id) throws PrescriptionNotFoundException {
        log.info("Attempting to delete prescription with ID: {}", id);
        if (prescriptionService.deletePrescription(id)) {
            log.info("Prescription deleted successfully: {}", id);
            return "Prescription deleted successfully.";
        }
        log.warn("Prescription deletion failed: {}", id);
        return "Deletion failed";
    }

    @GetMapping("/patient/{patientId}")
    public List<Prescription> getPrescriptionsByPatientId(@PathVariable Long patientId) {
        log.info("Fetching prescriptions for patientId: {}", patientId);
        return prescriptionService.getPrescriptionsByPatientId(patientId);
    }

    @GetMapping("/appointment/{appointmentId}")
    public List<Prescription> getPrescriptionsByAppointmentId(@PathVariable Long appointmentId) {
        log.info("Fetching prescriptions for appointmentId: {}", appointmentId);
        return prescriptionService.getPrescriptionsByAppointmentId(appointmentId);
    }
}
