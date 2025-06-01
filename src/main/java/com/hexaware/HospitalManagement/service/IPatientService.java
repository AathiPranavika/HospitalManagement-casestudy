package com.hexaware.HospitalManagement.service;

import java.util.List;

import com.hexaware.HospitalManagement.DTO.AppointmentDTO;
import com.hexaware.HospitalManagement.DTO.DoctorDTO;
import com.hexaware.HospitalManagement.DTO.MessageDTO;
import com.hexaware.HospitalManagement.DTO.PatientDTO;
import com.hexaware.HospitalManagement.entity.Appointment;
import com.hexaware.HospitalManagement.entity.Doctor;
import com.hexaware.HospitalManagement.entity.MedicalRecord;
import com.hexaware.HospitalManagement.entity.Message;
import com.hexaware.HospitalManagement.entity.Patient;
import com.hexaware.HospitalManagement.entity.Prescription;
import com.hexaware.HospitalManagement.exception.AppointmentNotFoundException;
import com.hexaware.HospitalManagement.exception.DuplicateDoctorException;
import com.hexaware.HospitalManagement.exception.DuplicatePatientException;
import com.hexaware.HospitalManagement.exception.InvalidRoleException;
import com.hexaware.HospitalManagement.exception.UserNotFoundException;

public interface IPatientService {

    Patient registerPatient(PatientDTO patientInput) throws InvalidRoleException, UserNotFoundException, DuplicatePatientException;

    Patient getPatientById(Long patientId);

    Patient updatePatient(Long patientId, PatientDTO patientInput);

    boolean deletePatient(Long patientId);

    List<Patient> getAllPatients();

    List<Patient> searchPatientsByName(String name);

    List<Patient> searchPatientsByBloodGroup(String bloodGroup);

    Appointment bookAppointment(AppointmentDTO appointmentDto) throws AppointmentNotFoundException;

    boolean cancelAppointment(Long appointmentId) throws AppointmentNotFoundException;

    List<Appointment> getUpcomingAppointments(Long patientId);

    List<MedicalRecord> getMedicalRecordsByPatientId(Long patientId);

    List<Prescription> getPrescriptionsByPatientId(Long patientId);
        
    Message sendMessage(MessageDTO messageDTO);

    List<Message> getMessagesBetweenDoctorAndPatient(int doctorId, int patientId);

    List<Message> getUnreadMessagesForPatient(int patientId);

    boolean markMessageAsRead(int messageId);

    List<Message> getMessagesSentByPatient(int patientId);

	boolean checkPatientExistsById(Long patientId);

    List<Doctor> getDoctorsBySpecialization(String specialization);

    List<Doctor> getDoctorsByDesignation(String designation);

    List<Doctor> getDoctorsByGender(String gender);

    List<Doctor> getAllDoctors();

    List<Doctor> searchDoctorsByName(String name);

}
