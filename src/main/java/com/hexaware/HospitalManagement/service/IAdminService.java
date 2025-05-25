package com.hexaware.HospitalManagement.service;

import java.util.List;

import com.hexaware.HospitalManagement.DTO.AdminDTO;
import com.hexaware.HospitalManagement.DTO.AppointmentDTO;
import com.hexaware.HospitalManagement.DTO.DoctorDTO;
import com.hexaware.HospitalManagement.DTO.PatientDTO;
import com.hexaware.HospitalManagement.DTO.UserDTO;
import com.hexaware.HospitalManagement.entity.Admin;
import com.hexaware.HospitalManagement.entity.Appointment;
import com.hexaware.HospitalManagement.entity.Doctor;
import com.hexaware.HospitalManagement.entity.Patient;
import com.hexaware.HospitalManagement.entity.User;
import com.hexaware.HospitalManagement.exception.AppointmentNotFoundException;
import com.hexaware.HospitalManagement.exception.DuplicateDoctorException;
import com.hexaware.HospitalManagement.exception.DuplicatePatientException;
import com.hexaware.HospitalManagement.exception.InvalidRoleException;
import com.hexaware.HospitalManagement.exception.UserNotFoundException;

public interface IAdminService {

    Doctor addDoctor(DoctorDTO doctorDTO) throws UserNotFoundException, InvalidRoleException, DuplicateDoctorException;
    Doctor updateDoctor(Long doctorId, DoctorDTO doctorDTO) throws UserNotFoundException;
    void deleteDoctor(Long doctorId);
    List<Doctor> getAllDoctors();
    Doctor getDoctorById(Long doctorId);
    List<Doctor> getDoctorsBySpecialization(String specialization);
    List<Doctor> getDoctorsByGender(String gender);

    Patient addPatient(PatientDTO patientDTO) throws InvalidRoleException, UserNotFoundException, DuplicatePatientException;
    Patient updatePatient(Long patientId, PatientDTO patientDTO);
    void deletePatient(Long patientId);
    List<Patient> getAllPatients();
    Patient getPatientById(Long patientId);
    List<Patient> searchPatientsByBloodGroup(String bloodGroup);

    List<Appointment> getAllAppointments();
    Appointment getAppointmentById(Long appointmentId) throws AppointmentNotFoundException;
    Appointment updateAppointment(Long appointmentId, AppointmentDTO appointmentDTO) throws AppointmentNotFoundException;
    void cancelAppointment(Long appointmentId) throws AppointmentNotFoundException;

    User addUser(UserDTO userDTO);
    User updateUser(Long userId, UserDTO userDTO) throws UserNotFoundException;
    void deleteUser(Long userId) throws UserNotFoundException;
    User getUserById(Long userId) throws UserNotFoundException;
    List<User> getAllUsers();

    Admin registerAdmin(AdminDTO adminDTO);
    Admin updateAdmin(Long adminId, AdminDTO adminDTO);
    void deleteAdmin(Long adminId);
    List<Admin> getAllAdmins();
    Admin getAdminById(Long adminId);
}
