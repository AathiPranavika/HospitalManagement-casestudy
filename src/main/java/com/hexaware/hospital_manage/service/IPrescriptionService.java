package com.hexaware.hospital_manage.service;

import java.util.List;

import com.hexaware.hospital_manage.entity.Prescription;

public interface IPrescriptionService 
{

    Prescription createPrescription(Prescription prescription);

    Prescription updatePrescription(Prescription prescription);

    boolean deletePrescription(int prescriptionId);

    //Prescription getPrescriptionById(int prescriptionId);

    List<Prescription> getAllPrescriptions();

    List<Prescription> getPrescriptionsByMedicalRecordId(int recordId);

    List<Prescription> getPrescriptionsByPatientId(int patientId);

    List<Prescription> getPrescriptionsByMedicineName(String medicineName);
}
