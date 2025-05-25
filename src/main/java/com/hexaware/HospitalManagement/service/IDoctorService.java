package com.hexaware.HospitalManagement.service;

import java.time.LocalDate;
import java.util.List;

import com.hexaware.HospitalManagement.DTO.DoctorDTO;
import com.hexaware.HospitalManagement.DTO.MedicalRecordDTO;
import com.hexaware.HospitalManagement.DTO.PrescriptionDTO;
import com.hexaware.HospitalManagement.entity.Appointment;
import com.hexaware.HospitalManagement.entity.Doctor;
import com.hexaware.HospitalManagement.entity.MedicalRecord;
import com.hexaware.HospitalManagement.entity.Message;
import com.hexaware.HospitalManagement.entity.Prescription;
import com.hexaware.HospitalManagement.exception.AppointmentNotFoundException;
import com.hexaware.HospitalManagement.exception.DuplicateDoctorException;
import com.hexaware.HospitalManagement.exception.InvalidRoleException;
import com.hexaware.HospitalManagement.exception.MedicalRecordNotFoundException;
import com.hexaware.HospitalManagement.exception.PrescriptionNotFoundException;
import com.hexaware.HospitalManagement.exception.UserNotFoundException;

public interface IDoctorService {

	Doctor getDoctorById(Long doctorId);

	Doctor updateDoctor(Long doctorId, DoctorDTO doctorDTO) throws UserNotFoundException;

	boolean deleteDoctor(Long doctorId);

	List<Doctor> getDoctorsBySpecialization(String specialization);

	List<Doctor> getDoctorsByDesignation(String designation);

	List<Doctor> getDoctorsByGender(String gender);

	Doctor registerDoctor(DoctorDTO doctorDTO)
			throws UserNotFoundException, InvalidRoleException, DuplicateDoctorException;

	List<Doctor> getAllDoctors();

	List<Doctor> searchDoctorsByName(String name);

	List<Appointment> getAppointments(Long doctorId);

	List<Appointment> getUpcomingAppointments(Long doctorId);

    MedicalRecord addMedicalRecord(MedicalRecordDTO medicalRecordDTO) throws AppointmentNotFoundException;

    MedicalRecord updateMedicalRecord(MedicalRecordDTO medicalRecordDTO) throws MedicalRecordNotFoundException, AppointmentNotFoundException;

	List<MedicalRecord> getPatientMedicalHistory(Long patientId);

	Prescription addPrescription(PrescriptionDTO prescriptionDTO) throws MedicalRecordNotFoundException;

	Prescription updatePrescription(PrescriptionDTO updatedPrescription) throws PrescriptionNotFoundException;


	// Send message from doctor to patient (MessageRepository)
	boolean sendMessage(Long doctorId, Long patientId, String messageContent);

	// Retrieve chat messages between doctor and patient (MessageRepository)
	List<Message> getMessagesBetweenDoctorAndPatient(Long doctorId, Long patientId);

}
