package com.hexaware.HospitalManagement.service;
/**
 * Service interface for managing prescription-related operations in the Hospital Management System.
 * * 
 * @author Aathi Pranavika
 * @version 1.0
 */
import com.hexaware.HospitalManagement.DTO.PrescriptionDTO;
import com.hexaware.HospitalManagement.entity.Prescription;
import com.hexaware.HospitalManagement.exception.MedicalRecordNotFoundException;
import com.hexaware.HospitalManagement.exception.PrescriptionNotFoundException;

import java.util.List;

public interface IPrescriptionService {

    Prescription createPrescription(PrescriptionDTO prescriptionDTO) throws MedicalRecordNotFoundException;

    Prescription getPrescriptionById(Long prescriptionId) throws PrescriptionNotFoundException;

    Prescription updatePrescription(PrescriptionDTO prescriptionDTO) throws PrescriptionNotFoundException;

    boolean deletePrescription(Long prescriptionId) throws PrescriptionNotFoundException;

    List<Prescription> getPrescriptionsByPatientId(Long patientId);

    List<Prescription> getPrescriptionsByAppointmentId(Long appointmentId);
}
