package com.hexaware.HospitalManagement.service;
/**
 * Service interface for managing appointment-related operations in the Hospital Management System.
 * * 
 * @author Aathi Pranavika
 * @version 1.0
 */
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    
    Appointment confirmAppointment(Long appointmentId,AppointmentDTO appointmentDTO, LocalDateTime dateTime) throws AppointmentNotFoundException;

    List<Appointment> getUpcomingAppointmentsForDoctor(Long doctorId);

    List<Appointment> getUpcomingAppointmentsForPatient(Long patientId);

    List<Appointment> getAllAppointments();

    List<Appointment> getAppointmentsByStatus(Appointment.AppointmentStatus status);

    List<Appointment> searchAppointmentsByDate(LocalDate date);
    
    void deleteAppointmentById(Long id) throws AppointmentNotFoundException;


}
