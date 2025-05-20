package com.hexaware.hospital_manage.service;
import java.util.List;

import com.hexaware.hospital_manage.entity.MedicalRecord;

public interface IMedicalRecordService {

    MedicalRecord createMedicalRecord(MedicalRecord medicalRecord);

    MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord);

    boolean deleteMedicalRecord(int recordId);

    //MedicalRecord getMedicalRecordById(int recordId);

    MedicalRecord getMedicalRecordByAppointmentId(int appointmentId);

    List<MedicalRecord> getAllMedicalRecords();

    List<MedicalRecord> getMedicalRecordsByPatientId(int patientId);

    List<MedicalRecord> getMedicalRecordsByDoctorId(int doctorId);
}
