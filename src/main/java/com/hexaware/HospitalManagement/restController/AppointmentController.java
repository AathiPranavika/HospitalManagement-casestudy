package com.hexaware.HospitalManagement.restController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.HospitalManagement.DTO.AppointmentDTO;
import com.hexaware.HospitalManagement.entity.Appointment;
import com.hexaware.HospitalManagement.exception.AppointmentNotFoundException;
import com.hexaware.HospitalManagement.service.IAppointmentService;
@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private IAppointmentService appointmentService;

    @PostMapping("/book")
    public ResponseEntity<Appointment> bookAppointment(@RequestBody AppointmentDTO appointmentDTO) throws AppointmentNotFoundException {
        Appointment savedAppointment = appointmentService.bookAppointment(appointmentDTO);
        return new ResponseEntity<>(savedAppointment, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> allAppointments = appointmentService.getAllAppointments();
        return new ResponseEntity<>(allAppointments,HttpStatus.OK);    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) throws AppointmentNotFoundException {
        Appointment appointment = appointmentService.getAppointmentById(id);
        return new ResponseEntity<>(appointment,HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public Appointment updateAppointment(@PathVariable Long id,
                                         @RequestBody AppointmentDTO appointmentDTO) throws AppointmentNotFoundException {
        return appointmentService.updateAppointment(id, appointmentDTO);
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<Appointment> cancelAppointment(@PathVariable Long id) throws AppointmentNotFoundException {
        Appointment appointment = appointmentService.cancelAppointmentById(id);
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<Appointment> rejectAppointment(@PathVariable Long id) throws AppointmentNotFoundException {
        Appointment appointment = appointmentService.rejectAppointmentById(id);
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }

    @PutMapping("/complete/{id}")
    public ResponseEntity<Appointment> completeAppointment(@PathVariable Long id) throws AppointmentNotFoundException {
        Appointment appointment = appointmentService.completeAppointmentById(id);
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }


    @PutMapping("/confirm/{id}/{dateTime}")
    public Appointment confirmAppointment(@PathVariable Long id,
                                          @RequestBody AppointmentDTO appointmentDTO,
                                          @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime) throws AppointmentNotFoundException {
        return appointmentService.confirmAppointment(id,appointmentDTO, dateTime);
    }

    @GetMapping("/patient/{patientId}")
    public List<Appointment> getAppointmentsByPatientId(@PathVariable Long patientId) {
        return appointmentService.getAppointmentsByPatientId(patientId);
    }

    @GetMapping("/doctor/{doctorId}")
    public List<Appointment> getAppointmentsByDoctorId(@PathVariable Long doctorId) {
        return appointmentService.getAppointmentsByDoctorId(doctorId);
    }

    @GetMapping("/doctor/{doctorId}/upcoming")
    public List<Appointment> getUpcomingAppointmentsForDoctor(@PathVariable Long doctorId) {
        return appointmentService.getUpcomingAppointmentsForDoctor(doctorId);
    }

    @GetMapping("/patient/{patientId}/upcoming")
    public List<Appointment> getUpcomingAppointmentsForPatient(@PathVariable Long patientId) {
        return appointmentService.getUpcomingAppointmentsForPatient(patientId);
    }

    @GetMapping("/status/{status}")
    public List<Appointment> getAppointmentsByStatus(@PathVariable Appointment.AppointmentStatus status) {
        return appointmentService.getAppointmentsByStatus(status);
    }

    @GetMapping("/search/{date}")
    public List<Appointment> searchAppointmentsByDate(@PathVariable
                                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate date) {
        return appointmentService.searchAppointmentsByDate(date);
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable Long id) throws AppointmentNotFoundException {
        appointmentService.deleteAppointmentById(id);
        return new ResponseEntity<>("Appointment with ID " + id + " deleted successfully.", HttpStatus.OK);
    }

}
