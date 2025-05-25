package com.hexaware.HospitalManagement.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.HospitalManagement.DTO.AppointmentDTO;
import com.hexaware.HospitalManagement.entity.Appointment;
import com.hexaware.HospitalManagement.entity.Doctor;
import com.hexaware.HospitalManagement.entity.Patient;
import com.hexaware.HospitalManagement.exception.AppointmentNotFoundException;
import com.hexaware.HospitalManagement.repository.AppointmentRepository;
import com.hexaware.HospitalManagement.repository.DoctorRepository;
import com.hexaware.HospitalManagement.repository.PatientRepository;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class AppointmentServiceImpl implements IAppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepo;

    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private DoctorRepository doctorRepo;

    @Override
    public Appointment getAppointmentById(Long appointmentId) throws AppointmentNotFoundException {
       
        Optional<Appointment> appObj=appointmentRepo.findById(appointmentId);
        
        if(appObj.isEmpty())
        {
        	throw new AppointmentNotFoundException("Appointment not found");
        }
        return appObj.get();
    }

    @Override
    public Appointment bookAppointment(AppointmentDTO dto) throws AppointmentNotFoundException {
        Appointment appointment = new Appointment();

        appointment.setAppointmentId(dto.getAppointmentId());
        appointment.setSymptoms(dto.getSymptoms());
        appointment.setStatus(Appointment.AppointmentStatus.PENDING);
        appointment.setAppointmentDate(null);

        Optional<Patient> patientOpt = patientRepo.findById(dto.getPatientId());
        if (patientOpt.isEmpty()) {
            throw new AppointmentNotFoundException("Patient not found");
        }
        Patient patient = patientOpt.get();

        Optional<Doctor> doctorOpt = doctorRepo.findById(dto.getDoctorId());
        if (doctorOpt.isEmpty()) {
            throw new AppointmentNotFoundException("Doctor not found");
        }
        Doctor doctor = doctorOpt.get();

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        return appointmentRepo.save(appointment);
    }

    @Override
    public List<Appointment> getAppointmentsByPatientId(Long patientId) {
        return appointmentRepo.findByPatientPatientId(patientId);
    }

    @Override
    public List<Appointment> getAppointmentsByDoctorId(Long doctorId) {
        return appointmentRepo.findByDoctorDoctorId(doctorId);
    }

    @Override
    public Appointment updateAppointment(Long appointmentId, AppointmentDTO dto) throws AppointmentNotFoundException {
        Appointment existing = getAppointmentById(appointmentId);

        existing.setAppointmentDate(dto.getAppointmentDate());
        existing.setSymptoms(dto.getSymptoms());
        existing.setStatus(dto.getStatus());

        return appointmentRepo.save(existing);
    }

    @Override
    public Appointment cancelAppointmentById(Long appointmentId) throws AppointmentNotFoundException {
        int updatedRows = appointmentRepo.updateAppointmentStatus(appointmentId, Appointment.AppointmentStatus.CANCELLED);
        if (updatedRows == 0) {
            throw new AppointmentNotFoundException("Appointment not found with id: " + appointmentId);
        }
        return getAppointmentById(appointmentId);
    }

    @Override
    public Appointment rejectAppointmentById(Long appointmentId) throws AppointmentNotFoundException {
        int updatedRows = appointmentRepo.updateAppointmentStatus(appointmentId, Appointment.AppointmentStatus.REJECTED);
        if (updatedRows == 0) {
            throw new AppointmentNotFoundException("Appointment not found with id: " + appointmentId);
        }
        return getAppointmentById(appointmentId);
    }

    @Override
    public Appointment completeAppointmentById(Long appointmentId) throws AppointmentNotFoundException {
        int updatedRows = appointmentRepo.updateAppointmentStatus(appointmentId, Appointment.AppointmentStatus.COMPLETED);
        if (updatedRows == 0) {
            throw new AppointmentNotFoundException("Appointment not found with id: " + appointmentId);
        }
        return getAppointmentById(appointmentId);
    }

    @Override
    public Appointment confirmAppointment(Long appointmentId,AppointmentDTO dto, LocalDateTime dateTime) throws AppointmentNotFoundException {
        Appointment appointment = getAppointmentById(dto.getAppointmentId());
        appointment.setStatus(Appointment.AppointmentStatus.CONFIRMED);
        appointment.setAppointmentDate(dateTime);
        return appointmentRepo.save(appointment);
    }

    @Override
    public List<Appointment> getUpcomingAppointmentsForDoctor(Long doctorId) {
        return appointmentRepo.findByDoctorDoctorIdAndAppointmentDateAfter(doctorId, LocalDate.now());
    }

    @Override
    public List<Appointment> getUpcomingAppointmentsForPatient(Long patientId) {
        return appointmentRepo.findByPatientPatientIdAndAppointmentDateAfter(patientId, LocalDate.now());
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepo.findAll();
    }

    @Override
    public List<Appointment> getAppointmentsByStatus(Appointment.AppointmentStatus status) {
        return appointmentRepo.findByStatus(status);
    }

    @Override
    public List<Appointment> searchAppointmentsByDate(LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();
        return appointmentRepo.findByAppointmentDateBetween(start, end);
    }
    @Override
    public void deleteAppointmentById(Long id) throws AppointmentNotFoundException {
        Optional<Appointment> appObj = appointmentRepo.findById(id);

        if (appObj.isEmpty()) {
            throw new AppointmentNotFoundException("Appointment with ID " + id + " not found.");
        }

        appointmentRepo.delete(appObj.get());
    }


}
