package com.hexaware.HospitalManagement.restController;
/**
 * REST controller for admin-related operations in the Hospital Management System.
 * * 
 * @author Aathi Pranavika
 * @version 1.0
 * */
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

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
import com.hexaware.HospitalManagement.service.IAdminService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/admins")
@Slf4j
public class AdminController {

    @Autowired
    private IAdminService adminService;

    // Doctor APIs

    @PostMapping("/doctor/add")
    public Doctor addDoctor(@Valid @RequestBody DoctorDTO doctorDTO) 
            throws UserNotFoundException, InvalidRoleException, DuplicateDoctorException {
        log.info("Adding a new doctor");
        Doctor doctor = adminService.addDoctor(doctorDTO);
        log.info("Added doctor with ID {}", doctor.getDoctorId());
        return doctor;
    }

    @PutMapping("/doctor/update/{id}")
    public Doctor updateDoctor(@PathVariable("id") Long doctorId, @Valid @RequestBody DoctorDTO doctorDTO) 
            throws UserNotFoundException {
        log.info("Updating doctor with ID {}", doctorId);
        Doctor updatedDoctor = adminService.updateDoctor(doctorId, doctorDTO);
        log.info("Updated doctor with ID {}", doctorId);
        return updatedDoctor;
    }

    @DeleteMapping("/doctor/delete/{id}")
    public String deleteDoctor(@PathVariable("id") Long doctorId) {
        log.info("Deleting doctor with ID {}", doctorId);
        adminService.deleteDoctor(doctorId);
        log.info("Deleted doctor with ID {}", doctorId);
        return "doctor deleted successfully";

    }

    @GetMapping("/doctors/getall")
    public List<Doctor> getAllDoctors() {
        log.info("Fetching all doctors");
        return adminService.getAllDoctors();
    }

    @GetMapping("/doctor/getById/{id}")
    public Doctor getDoctorById(@PathVariable("id") Long doctorId) {
        log.info("Fetching doctor by ID {}", doctorId);
        return adminService.getDoctorById(doctorId);
    }

    @GetMapping("/doctors/specialization/{spec}")
    public List<Doctor> getDoctorsBySpecialization(@PathVariable("spec") String specialization) {
        log.info("Fetching doctors by specialization: {}", specialization);
        return adminService.getDoctorsBySpecialization(specialization);
    }

    @GetMapping("/doctors/gender/{gender}")
    public List<Doctor> getDoctorsByGender(@PathVariable("gender") String gender) {
        log.info("Fetching doctors by gender: {}", gender);
        return adminService.getDoctorsByGender(gender);
    }


    // Patient APIs

    @PostMapping("/patient/add")
    public Patient addPatient(@Valid @RequestBody PatientDTO patientDTO) 
            throws InvalidRoleException, UserNotFoundException, DuplicatePatientException {
        log.info("Adding a new patient");
        Patient patient = adminService.addPatient(patientDTO);
        log.info("Added patient with ID {}", patient.getPatientId());
        return patient;
    }

    @PutMapping("/patient/update/{id}")
    public Patient updatePatient(@PathVariable("id") Long patientId, @Valid @RequestBody PatientDTO patientDTO) {
        log.info("Updating patient with ID {}", patientId);
        Patient updatedPatient = adminService.updatePatient(patientId, patientDTO);
        log.info("Updated patient with ID {}", patientId);
        return updatedPatient;
    }

    @DeleteMapping("/patient/delete/{id}")
    public String deletePatient(@PathVariable("id") Long patientId) {
        log.info("Deleting patient with ID {}", patientId);
        adminService.deletePatient(patientId);
        log.info("Deleted patient with ID {}", patientId);
        return "patient deleted successfully";

    }

    @GetMapping("/patients/getAll")
    public List<Patient> getAllPatients() {
        log.info("Fetching all patients");
        return adminService.getAllPatients();
    }

    @GetMapping("/patient/getById/{id}")
    public Patient getPatientById(@PathVariable("id") Long patientId) {
        log.info("Fetching patient by ID {}", patientId);
        return adminService.getPatientById(patientId);
    }

    @GetMapping("/patients/getByBloodGroup/{bloodGroup}")
    public List<Patient> searchPatientsByBloodGroup(@PathVariable("bloodGroup") String bloodGroup) {
        log.info("Searching patients by blood group: {}", bloodGroup);
        return adminService.searchPatientsByBloodGroup(bloodGroup);
    }


    // Appointment APIs

    @GetMapping("/appointments/getAll")
    public List<Appointment> getAllAppointments() {
        log.info("Fetching all appointments");
        return adminService.getAllAppointments();
    }

    @GetMapping("/appointment/getById/{id}")
    public Appointment getAppointmentById(@PathVariable("id") Long appointmentId) throws AppointmentNotFoundException {
        log.info("Fetching appointment by ID {}", appointmentId);
        return adminService.getAppointmentById(appointmentId);
    }

    @PutMapping("/appointment/update/{id}")
    public Appointment updateAppointment(@PathVariable("id") Long appointmentId, 
                                         @Valid @RequestBody AppointmentDTO appointmentDTO) throws AppointmentNotFoundException {
        log.info("Updating appointment with ID {}", appointmentId);
        Appointment updatedAppointment = adminService.updateAppointment(appointmentId, appointmentDTO);
        log.info("Updated appointment with ID {}", appointmentId);
        return updatedAppointment;
    }

    @DeleteMapping("/appointment/cancel/{id}")
    public String cancelAppointment(@PathVariable("id") Long appointmentId) throws AppointmentNotFoundException {
        log.info("Cancelling appointment with ID {}", appointmentId);
        adminService.cancelAppointment(appointmentId);
        log.info("Cancelled appointment with ID {}", appointmentId);
        return "appointment cancelled successfully";
    }

    @PutMapping("/appointment/reject/{id}")
    public String rejectAppointment(@PathVariable Long id) throws AppointmentNotFoundException {
        log.info("Rejecting appointment with ID {}", id);
        Appointment rejected = adminService.rejectAppointmentById(id);
        log.info("Rejected appointment with ID {}", id);
        return "appointment rejected successfully";
    }

    @PutMapping("/appointment/complete/{id}")
    public String completeAppointment(@PathVariable Long id) throws AppointmentNotFoundException {
        log.info("Completing appointment with ID {}", id);
        Appointment completed = adminService.completeAppointmentById(id);
        log.info("Completed appointment with ID {}", id);
        return "appointment completed successfully";
    }

    @PutMapping("/appointment/confirm/{id}/{dateTime}")
    public Appointment confirmAppointment(
            @PathVariable Long id,
            @RequestBody AppointmentDTO appointmentDTO,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime) throws AppointmentNotFoundException {
        log.info("Confirming appointment with ID {} at {}", id, dateTime);
        Appointment confirmed = adminService.confirmAppointment(id, appointmentDTO, dateTime);
        log.info("Confirmed appointment with ID {}", id);
        return confirmed;
    }
    
    @DeleteMapping("/appointment/delete/{id}")
    public String deleteAppointmentById(@PathVariable("id") Long appointmentId) throws AppointmentNotFoundException {
        log.info("Deleting appointment with ID {}", appointmentId);
        boolean deleted = adminService.deleteAppointmentById(appointmentId);
        if (deleted) {
            log.info("Deleted appointment with ID {}", appointmentId);
            return "appointment deleted successfully";
        }
        log.warn("Failed to delete appointment with ID {}", appointmentId);
        return "deletion failed";
    }


    // User APIs

    @PostMapping("/user/add")
    public String addUser(@Valid @RequestBody UserDTO userDTO) {
        log.info("Adding a new user");
        String registered = adminService.addUser(userDTO);
        return registered;
    }

    @PutMapping("/user/update/{id}")
    public User updateUser(@PathVariable("id") Long userId, @Valid @RequestBody UserDTO userDTO) throws UserNotFoundException {
        log.info("Updating user with ID {}", userId);
        User updatedUser = adminService.updateUser(userId, userDTO);
        log.info("Updated user with ID {}", userId);
        return updatedUser;
    }

    @DeleteMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Long userId) throws UserNotFoundException {
        log.info("Deleting user with ID {}", userId);
        adminService.deleteUser(userId);
        log.info("Deleted user with ID {}", userId);
        return "user deleted successfully";
    }

    @GetMapping("/user/getById/{id}")
    public User getUserById(@PathVariable("id") Long userId) throws UserNotFoundException {
        log.info("Fetching user by ID {}", userId);
        return adminService.getUserById(userId);
    }

    @GetMapping("/users/getAll")
    public List<User> getAllUsers() {
        log.info("Fetching all users");
        return adminService.getAllUsers();
    }


    // Admin APIs

    @PostMapping("/register/add")
    public Admin registerAdmin(@Valid @RequestBody AdminDTO adminDTO) throws UserNotFoundException {
        log.info("Registering new admin");
        Admin admin = adminService.registerAdmin(adminDTO);
        log.info("Registered admin with ID {}", admin.getAdminId());
        return admin;
    }

    @PutMapping("/update/{id}")
    public Admin updateAdmin(@PathVariable("id") Long adminId, @Valid @RequestBody AdminDTO adminDTO) throws UserNotFoundException {
        log.info("Updating admin with ID {}", adminId);
        Admin updatedAdmin = adminService.updateAdmin(adminId, adminDTO);
        log.info("Updated admin with ID {}", adminId);
        return updatedAdmin;
    }

    @DeleteMapping("/delete/{id}")
    public String deleteAdmin(@PathVariable("id") Long adminId) {
        log.info("Deleting admin with ID {}", adminId);
        adminService.deleteAdmin(adminId);
        log.info("Deleted admin with ID {}", adminId);
        return "admin deleted successfully";

    }

    @GetMapping("/getAll")
    public List<Admin> getAllAdmins() {
        log.info("Fetching all admins");
        return adminService.getAllAdmins();
    }

    @GetMapping("/getById/{id}")
    public Admin getAdminById(@PathVariable("id") Long adminId) {
        log.info("Fetching admin by ID {}", adminId);
        return adminService.getAdminById(adminId);
    }
}
