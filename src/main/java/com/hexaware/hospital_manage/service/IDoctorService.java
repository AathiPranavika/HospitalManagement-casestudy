package com.hexaware.hospital_manage.service;

import java.util.List;

import com.hexaware.hospital_manage.entity.Doctor;

public interface IDoctorService {

    Doctor addDoctor(Doctor doctor);

    Doctor updateDoctor(Doctor doctor);

    boolean deleteDoctorById(int doctorId);

    List<Doctor> getAllDoctors();

    List<Doctor> getDoctorsBySpecialization(String specialization);

   // List<Doctor> getDoctorsByExperience(int minYears);

   // List<Doctor> getDoctorsByDesignation(String designation);

    List<Doctor> getDoctorsByGender(String gender); 

}
