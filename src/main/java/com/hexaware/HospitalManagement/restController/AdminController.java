package com.hexaware.HospitalManagement.restController;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private IAdminService adminService;

    // Doctor APIs

    @PostMapping("/doctor/add")
    public Doctor addDoctor(@Valid @RequestBody DoctorDTO doctorDTO) 
            throws UserNotFoundException, InvalidRoleException, DuplicateDoctorException {
        return adminService.addDoctor(doctorDTO);
    }

    @PutMapping("/doctor/update/{id}")
    public Doctor updateDoctor(@PathVariable("id") Long doctorId, @Valid @RequestBody DoctorDTO doctorDTO) 
            throws UserNotFoundException {
        return adminService.updateDoctor(doctorId, doctorDTO);
    }

    @DeleteMapping("/doctor/delete/{id}")
    public void deleteDoctor(@PathVariable("id") Long doctorId) {
        adminService.deleteDoctor(doctorId);
    }

    @GetMapping("/doctors/getall")
    public List<Doctor> getAllDoctors() {
        return adminService.getAllDoctors();
    }

    @GetMapping("/doctor/getById/{id}")
    public Doctor getDoctorById(@PathVariable("id") Long doctorId) {
        return adminService.getDoctorById(doctorId);
    }

    @GetMapping("/doctors/specialization/{spec}")
    public List<Doctor> getDoctorsBySpecialization(@PathVariable("spec") String specialization) {
        return adminService.getDoctorsBySpecialization(specialization);
    }

    @GetMapping("/doctors/gender/{gender}")
    public List<Doctor> getDoctorsByGender(@PathVariable("gender") String gender) {
        return adminService.getDoctorsByGender(gender);
    }


    // Patient APIs

    @PostMapping("/patient/add")
    public Patient addPatient(@Valid @RequestBody PatientDTO patientDTO) 
            throws InvalidRoleException, UserNotFoundException, DuplicatePatientException {
        return adminService.addPatient(patientDTO);
    }

    @PutMapping("/patient/update/{id}")
    public Patient updatePatient(@PathVariable("id") Long patientId, @Valid @RequestBody PatientDTO patientDTO) {
        return adminService.updatePatient(patientId, patientDTO);
    }

    @DeleteMapping("/patient/delete/{id}")
    public void deletePatient(@PathVariable("id") Long patientId) {
        adminService.deletePatient(patientId);
    }

    @GetMapping("/patients/getAll")
    public List<Patient> getAllPatients() {
        return adminService.getAllPatients();
    }

    @GetMapping("/patient/getById/{id}")
    public Patient getPatientById(@PathVariable("id") Long patientId) {
        return adminService.getPatientById(patientId);
    }

    @GetMapping("/patients/getByBloodGroup/{bloodGroup}")
    public List<Patient> searchPatientsByBloodGroup(@PathVariable("bloodGroup") String bloodGroup) {
        return adminService.searchPatientsByBloodGroup(bloodGroup);
    }


    // Appointment APIs

    @GetMapping("/appointments/getAll")
    public List<Appointment> getAllAppointments() {
        return adminService.getAllAppointments();
    }

    @GetMapping("/appointment/getById/{id}")
    public Appointment getAppointmentById(@PathVariable("id") Long appointmentId) throws AppointmentNotFoundException {
        return adminService.getAppointmentById(appointmentId);
    }

    @PutMapping("/appointment/update/{id}")
    public Appointment updateAppointment(@PathVariable("id") Long appointmentId, 
                                         @Valid @RequestBody AppointmentDTO appointmentDTO) throws AppointmentNotFoundException {
        return adminService.updateAppointment(appointmentId, appointmentDTO);
    }

    @DeleteMapping("/appointment/cancel/{id}")
    public void cancelAppointment(@PathVariable("id") Long appointmentId) throws AppointmentNotFoundException {
        adminService.cancelAppointment(appointmentId);
    }

    @PutMapping("/reject/{id}")
    public Appointment rejectAppointment(@PathVariable Long id) throws AppointmentNotFoundException {
        return adminService.rejectAppointmentById(id);
    }

    @PutMapping("/complete/{id}")
    public Appointment completeAppointment(@PathVariable Long id) throws AppointmentNotFoundException {
    	return adminService.completeAppointmentById(id);
         
    }

    @PutMapping("/confirm/{id}/{dateTime}")
    public Appointment confirmAppointment(
            @PathVariable Long id,
            @RequestBody AppointmentDTO appointmentDTO,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime) throws AppointmentNotFoundException {

    	return adminService.confirmAppointment(id,appointmentDTO, dateTime);
    }
    
    @DeleteMapping("/appointment/delete/{id}")
    public String deleteAppointmentById(@PathVariable("id") Long appointmentId) throws AppointmentNotFoundException {
       if(adminService.deleteAppointmentById(appointmentId))
       {
    	   return "admin deleted successfully";
       }
       return "deletion failed";
    }


    // User APIs

    @PostMapping("/user/add")
    public User addUser(@Valid @RequestBody UserDTO userDTO) {
        return adminService.addUser(userDTO);
    }

    @PutMapping("/user/update/{id}")
    public User updateUser(@PathVariable("id") Long userId, @Valid @RequestBody UserDTO userDTO) throws UserNotFoundException {
        return adminService.updateUser(userId, userDTO);
    }

    @DeleteMapping("/user/delete/{id}")
    public void deleteUser(@PathVariable("id") Long userId) throws UserNotFoundException {
        adminService.deleteUser(userId);
    }

    @GetMapping("/user/getById/{id}")
    public User getUserById(@PathVariable("id") Long userId) throws UserNotFoundException {
        return adminService.getUserById(userId);
    }

    @GetMapping("/users/getAll")
    public List<User> getAllUsers() {
        return adminService.getAllUsers();
    }


    // Admin APIs

    @PostMapping("/register/add")
    public Admin registerAdmin(@Valid @RequestBody AdminDTO adminDTO) throws UserNotFoundException {
        return adminService.registerAdmin(adminDTO);
    }

    @PutMapping("/admin/update/{id}")
    public Admin updateAdmin(@PathVariable("id") Long adminId, @Valid @RequestBody AdminDTO adminDTO) throws UserNotFoundException {
        return adminService.updateAdmin(adminId, adminDTO);
    }

    @DeleteMapping("/admin/delete/{id}")
    public void deleteAdmin(@PathVariable("id") Long adminId) {
        adminService.deleteAdmin(adminId);
    }

    @GetMapping("/admins/getAll")
    public List<Admin> getAllAdmins() {
        return adminService.getAllAdmins();
    }

    @GetMapping("/admin/getById/{id}")
    public Admin getAdminById(@PathVariable("id") Long adminId) {
        return adminService.getAdminById(adminId);
    }
}
