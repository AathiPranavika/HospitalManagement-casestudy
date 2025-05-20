package com.hexaware.hospital_manage.service;

import java.time.LocalDateTime;
import java.util.List;

import com.hexaware.hospital_manage.entity.Appointment;

public interface IAppointmentService {

    Appointment createAppointment(Appointment appointment);

    Appointment updateAppointment(Appointment appointment);

    Appointment getAppointmentById(int appointmentId);

    List<Appointment> getAllAppointments();

    List<Appointment> getAppointmentsByPatientId(int patientId);

    List<Appointment> getAppointmentsByDoctorId(int doctorId);

    List<Appointment> getAppointmentsByDate(LocalDateTime date);

    // Get all appointments by status (PENDING, CONFIRMED, etc.)
    List<Appointment> getAppointmentsByStatus(Appointment.AppointmentStatus status);
    
    // Cancel appointment by ID (sets status to CANCELLED)
    boolean cancelAppointment(int appointmentId);


    // Confirm appointment by ID (sets status to CONFIRMED)
    boolean confirmAppointment(int appointmentId);

    // Mark appointment as completed by ID (sets status to COMPLETED)
    boolean completeAppointment(int appointmentId);

    // Reject appointment by ID (sets status to REJECTED)
    boolean rejectAppointment(int appointmentId);
}
