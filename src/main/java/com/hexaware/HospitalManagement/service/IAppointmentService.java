package com.hexaware.HospitalManagement.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.hexaware.HospitalManagement.DTO.AppointmentDTO;
import com.hexaware.HospitalManagement.entity.Appointment;
import com.hexaware.HospitalManagement.exception.AppointmentNotFoundException;

public interface IAppointmentService {

    Appointment bookAppointment(AppointmentDTO appointmentDTO) throws AppointmentNotFoundException;

    List<Appointment> getAppointmentsByPatientId(Long patientId);

    List<Appointment> getAppointmentsByDoctorId(Long doctorId);

    Appointment getAppointmentById(Long appointmentId) throws AppointmentNotFoundException;

    Appointment updateAppointment(Long appointmentId, AppointmentDTO updatedAppointmentDTO) throws AppointmentNotFoundException;

    Appointment cancelAppointmentById(Long appointmentId) throws AppointmentNotFoundException;
    
    Appointment rejectAppointmentById(Long appointmentId) throws AppointmentNotFoundException;
    
    Appointment completeAppointmentById(Long appointmentId) throws AppointmentNotFoundException;
    
    Appointment confirmAppointment(AppointmentDTO appointmentDTO, LocalDateTime dateTime) throws AppointmentNotFoundException;

    List<Appointment> getUpcomingAppointmentsForDoctor(Long doctorId);

    List<Appointment> getUpcomingAppointmentsForPatient(Long patientId);

    List<Appointment> getAllAppointments();

    List<Appointment> getAppointmentsByStatus(Appointment.AppointmentStatus status);

    List<Appointment> searchAppointmentsByDate(LocalDate date);

}
