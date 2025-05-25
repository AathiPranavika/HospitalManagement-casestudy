package com.hexaware.HospitalManagement.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hexaware.HospitalManagement.DTO.PrescriptionDTO;
import com.hexaware.HospitalManagement.entity.Prescription;
import com.hexaware.HospitalManagement.exception.MedicalRecordNotFoundException;
import com.hexaware.HospitalManagement.exception.PrescriptionNotFoundException;
import com.hexaware.HospitalManagement.service.IPrescriptionService;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    @Autowired
    private IPrescriptionService prescriptionService;

    @PostMapping("/add")
    public Prescription createPrescription(@RequestBody PrescriptionDTO dto)
            throws MedicalRecordNotFoundException {
        return prescriptionService.createPrescription(dto);
    }

    @GetMapping("/getBy/{id}")
    public Prescription getPrescriptionById(@PathVariable Long id)
            throws PrescriptionNotFoundException {
        return prescriptionService.getPrescriptionById(id);
    }

    @PutMapping("/update")
    public Prescription updatePrescription(@RequestBody PrescriptionDTO dto)
            throws PrescriptionNotFoundException {
        return prescriptionService.updatePrescription(dto);
    }

    @DeleteMapping("delete/{id}")
    public String deletePrescription(@PathVariable Long id)
            throws PrescriptionNotFoundException {
        prescriptionService.deletePrescription(id);
        return "Prescription deleted successfully.";
    }

    @GetMapping("/patient/{patientId}")
    public List<Prescription> getPrescriptionsByPatientId(@PathVariable Long patientId) {
        return prescriptionService.getPrescriptionsByPatientId(patientId);
    }

    @GetMapping("/appointment/{appointmentId}")
    public List<Prescription> getPrescriptionsByAppointmentId(@PathVariable Long appointmentId) {
        return prescriptionService.getPrescriptionsByAppointmentId(appointmentId);
    }

   
}
