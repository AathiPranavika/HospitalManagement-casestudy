package com.hexaware.HospitalManagement.service;

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
