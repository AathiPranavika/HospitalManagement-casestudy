package com.hexaware.HospitalManagement.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.hexaware.HospitalManagement.DTO.PrescriptionDTO;
import com.hexaware.HospitalManagement.entity.Prescription;
import com.hexaware.HospitalManagement.exception.MedicalRecordNotFoundException;
import com.hexaware.HospitalManagement.exception.PrescriptionNotFoundException;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@Rollback
@TestMethodOrder(OrderAnnotation.class)
class PrescriptionServiceImplTest {

    @Autowired
    private IPrescriptionService prescriptionService;


    @Test
    @Order(1)
    void testCreatePrescription() throws MedicalRecordNotFoundException {
        PrescriptionDTO dto = new PrescriptionDTO();
        dto.setMedicalRecordId(2L); 
        dto.setMedicineName("Paracetamol");
        dto.setDosage("500mg");
        dto.setRemarks("Take after food");
        
        Prescription created = prescriptionService.createPrescription(dto);

        assertNotNull(created);
        assertEquals("Paracetamol", created.getMedicineName());
    }

    @Test
    @Order(2)
    void testGetPrescriptionById() throws PrescriptionNotFoundException {
        Prescription prescription = prescriptionService.getPrescriptionById(4L); 
        assertNotNull(prescription);
    }

    @Test
    @Order(3)
    void testUpdatePrescription() throws PrescriptionNotFoundException {
        PrescriptionDTO dto = new PrescriptionDTO();
        dto.setPrescriptionId(4L); 
        dto.setMedicineName("Ibuprofen");
        dto.setDosage("400mg");
        dto.setRemarks("Take twice a day");

        Prescription updated = prescriptionService.updatePrescription(dto);
        assertNotNull(updated);
        assertEquals("Ibuprofen", updated.getMedicineName());
    }

    @Test
    @Order(4)
    void testGetPrescriptionsByPatientId() {
        List<Prescription> prescriptions = prescriptionService.getPrescriptionsByPatientId(4L); 
        assertNotNull(prescriptions);
        assertTrue(prescriptions.size() >= 0);
    }

    @Test
    @Order(5)
    void testGetPrescriptionsByAppointmentId() {
        List<Prescription> prescriptions = prescriptionService.getPrescriptionsByAppointmentId(2L); // Valid appointmentId
        assertNotNull(prescriptions);
        assertTrue(prescriptions.size() >= 0);
    }

    @Test
    @Order(6)
    void testDeletePrescription() throws PrescriptionNotFoundException {
        assertTrue(prescriptionService.deletePrescription(4L));
        
    }
}
