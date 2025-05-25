package com.hexaware.HospitalManagement.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hexaware.HospitalManagement.DTO.AppointmentDTO;
import com.hexaware.HospitalManagement.DTO.PatientDTO;
import com.hexaware.HospitalManagement.entity.Appointment;
import com.hexaware.HospitalManagement.entity.MedicalRecord;
import com.hexaware.HospitalManagement.entity.Message;
import com.hexaware.HospitalManagement.entity.Patient;
import com.hexaware.HospitalManagement.entity.Prescription;
import com.hexaware.HospitalManagement.exception.AppointmentNotFoundException;
import com.hexaware.HospitalManagement.exception.DuplicatePatientException;
import com.hexaware.HospitalManagement.exception.InvalidRoleException;
import com.hexaware.HospitalManagement.exception.UserNotFoundException;
import com.hexaware.HospitalManagement.service.IPatientService;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    IPatientService patientService;

    @PostMapping("/register")
    public ResponseEntity<Patient> registerPatient(@RequestBody PatientDTO patientDto)
            throws UserNotFoundException, DuplicatePatientException, InvalidRoleException {
        Patient saved = patientService.registerPatient(patientDto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) throws UserNotFoundException {
        Patient patient = patientService.getPatientById(id);
        if (patient == null) {
            throw new UserNotFoundException("Patient not found with ID: " + id);
        }
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody PatientDTO dto) {
        Patient updated = patientService.updatePatient(id, dto);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable Long id) throws UserNotFoundException {
        boolean deleted = patientService.deletePatient(id);
        if (deleted) {
            return new ResponseEntity<>("Patient deleted successfully", HttpStatus.OK);
        } else {
            throw new UserNotFoundException("Patient not found with ID: " + id);
        }
    }

    @GetMapping("/getall")
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/search/name/{name}")
    public List<Patient> searchPatientsByName(@PathVariable String name) {
        return patientService.searchPatientsByName(name);
    }

    @GetMapping("/search/bloodgroup/{bloodGroup}")
    public List<Patient> searchPatientsByBloodGroup(@PathVariable String bloodGroup) {
        return patientService.searchPatientsByBloodGroup(bloodGroup);
    }

    @PostMapping("/bookAppointment")
    public ResponseEntity<Appointment> bookAppointment(@RequestBody AppointmentDTO dto)
            throws AppointmentNotFoundException {
        Appointment appointment = patientService.bookAppointment(dto);
        return new ResponseEntity<>(appointment, HttpStatus.CREATED);
    }

    @DeleteMapping("/cancelAppointment/{id}")
    public ResponseEntity<String> cancelAppointment(@PathVariable Long id) throws AppointmentNotFoundException {
        boolean cancelled = patientService.cancelAppointment(id);
        if (cancelled) {
            return new ResponseEntity<>("Appointment cancelled successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Appointment cancellation failed", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/appointments/upcoming/{id}")
    public List<Appointment> getUpcomingAppointments(@PathVariable Long id) {
        return patientService.getUpcomingAppointments(id);
    }

    @GetMapping("/messages/{id}")
    public List<Message> getMessages(@PathVariable Long id) {
        return patientService.getMessagesFromDoctor(id);
    }

    @GetMapping("/medicalRecords/{patientid}")
    public List<MedicalRecord> getMedicalRecords(@PathVariable Long patientid) {
        return patientService.getMedicalRecordsByPatientId(patientid);
    }

    @GetMapping("/Prescription/patientId/{patientId}")
    public List<Prescription> getPrescriptions(@PathVariable Long patientId) {
        return patientService.getPrescriptionsByPatientId(patientId);
    }
    
    @GetMapping("/Prescription/AppointmentId/{appointmentId}")
    public List<Prescription> getPrescriptionsByAppointmentId(@PathVariable Long appointmentId) {
        return patientService.getPrescriptionsByAppointmentId(appointmentId);
    }

}
