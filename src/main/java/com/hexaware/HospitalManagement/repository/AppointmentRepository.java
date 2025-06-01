package com.hexaware.HospitalManagement.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hexaware.HospitalManagement.entity.Appointment;
import com.hexaware.HospitalManagement.entity.Appointment.AppointmentStatus;

import jakarta.transaction.Transactional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

	//writing query with fk->first entity name->entity attribute name
	List<Appointment> findByPatientPatientId(Long patientId);
	List<Appointment> findByDoctorDoctorId(Long doctorId);
	List<Appointment> findByDoctorDoctorIdAndAppointmentDateAfter(Long doctorId, LocalDateTime localDate);
	List<Appointment> findByPatientPatientIdAndAppointmentDateAfter(Long patientId, LocalDateTime localDate);
	List<Appointment> findByStatus(AppointmentStatus status);
	List<Appointment> findByAppointmentDateBetween(LocalDateTime start, LocalDateTime end);
	
	 @Modifying
	    
	    @Query("UPDATE Appointment a SET a.status = :status WHERE a.appointmentId = :appointmentId")
	    int updateAppointmentStatus(Long appointmentId, Appointment.AppointmentStatus status);


}
