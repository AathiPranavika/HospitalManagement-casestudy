package com.hexaware.HospitalManagement.service;
/**
 * Service interface for managing doctor-related operations in the Hospital Management System.
 * * 
 * @author Aathi Pranavika
 * @version 1.0
 */
import java.util.List;

import com.hexaware.HospitalManagement.DTO.DoctorDTO;
import com.hexaware.HospitalManagement.DTO.MedicalRecordDTO;
import com.hexaware.HospitalManagement.DTO.MessageDTO;
import com.hexaware.HospitalManagement.DTO.PrescriptionDTO;
import com.hexaware.HospitalManagement.entity.Appointment;
import com.hexaware.HospitalManagement.entity.Doctor;
import com.hexaware.HospitalManagement.entity.MedicalRecord;
import com.hexaware.HospitalManagement.entity.Message;
import com.hexaware.HospitalManagement.entity.Patient;
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

	boolean checkDoctorExistsById(Long doctorId);

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


	Message sendMessage(MessageDTO messageDTO);

	List<Message> getMessagesBetweenDoctorAndPatient(int doctorId, int patientId);

	List<Message> getUnreadMessagesForDoctor(int doctorId);

	boolean markMessageAsRead(int messageId);

	List<Message> getMessagesSentByDoctor(int doctorId);
	
	
	List<Patient> searchPatientsByName(String name);

    List<Patient> searchPatientsByBloodGroup(String bloodGroup);

	

   


}
