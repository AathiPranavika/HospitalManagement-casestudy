package com.hexaware.hospital_manage.service;


import com.hexaware.hospital_manage.entity.Patient;
import java.util.List;

public interface IPatientService {

    Patient addPatient(Patient patient);

    Patient getPatientById(int patientId);

    List<Patient> getAllPatients();

    Patient updatePatient(Patient patient);

    boolean deletePatient(int patientId);

    List<Patient> findPatientsByBloodGroup(String bloodGroup);

   
}
