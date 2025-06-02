package com.hexaware.HospitalManagement.service;
/**
 * Service interface for managing medical record-related operations in the Hospital Management System.
 * * 
 * @author Aathi Pranavika
 * @version 1.0
 */
import com.hexaware.HospitalManagement.DTO.MedicalRecordDTO;
import com.hexaware.HospitalManagement.entity.MedicalRecord;
import com.hexaware.HospitalManagement.exception.AppointmentNotFoundException;
import com.hexaware.HospitalManagement.exception.MedicalRecordNotFoundException;

import java.util.List;

public interface IMedicalRecordService {

    MedicalRecord createMedicalRecord(MedicalRecordDTO medicalRecordDTO) throws AppointmentNotFoundException;

    MedicalRecord getMedicalRecordByAppointmentId(Long appointmentId) throws MedicalRecordNotFoundException;

    MedicalRecord getMedicalRecordById(Long recordId) throws MedicalRecordNotFoundException;

    List<MedicalRecord> getMedicalRecordsByPatientId(Long patientId);

    List<MedicalRecord> getMedicalRecordsByDoctorId(Long doctorId);

    MedicalRecord updateMedicalRecord(MedicalRecordDTO medicalRecordDTO) throws MedicalRecordNotFoundException, AppointmentNotFoundException;

    boolean deleteMedicalRecord(Long recordId) throws MedicalRecordNotFoundException;

    List<MedicalRecord> getAllMedicalRecords();
}
