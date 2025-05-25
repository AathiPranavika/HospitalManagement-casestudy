package com.hexaware.HospitalManagement.service;

import com.hexaware.HospitalManagement.DTO.PrescriptionDTO;
import com.hexaware.HospitalManagement.entity.Prescription;
import com.hexaware.HospitalManagement.exception.MedicalRecordNotFoundException;
import com.hexaware.HospitalManagement.exception.PrescriptionNotFoundException;

import java.util.List;

public interface IPrescriptionService {

    Prescription createPrescription(PrescriptionDTO prescriptionDTO) throws MedicalRecordNotFoundException;

    Prescription getPrescriptionById(Long prescriptionId) throws PrescriptionNotFoundException;

    Prescription updatePrescription(PrescriptionDTO prescriptionDTO) throws PrescriptionNotFoundException;

    void deletePrescription(Long prescriptionId) throws PrescriptionNotFoundException;

    List<Prescription> getPrescriptionsByPatientId(Long patientId);

    List<Prescription> getPrescriptionsByAppointmentId(Long appointmentId);
}
