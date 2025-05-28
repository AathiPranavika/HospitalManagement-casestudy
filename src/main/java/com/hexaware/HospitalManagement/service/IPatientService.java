package com.hexaware.HospitalManagement.service;

import java.util.List;

import com.hexaware.HospitalManagement.DTO.AppointmentDTO;
import com.hexaware.HospitalManagement.DTO.MessageDTO;
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
    
    List<Prescription> getPrescriptionsByAppointmentId(Long appointmentId);
    
 // 1. Patient sends a message to a doctor
    Message sendMessage(MessageDTO messageDTO);

    // 2. Patient views conversation with a doctor
    List<Message> getMessagesBetweenDoctorAndPatient(int doctorId, int patientId);

    // 3. Patient checks unread messages from doctor
    List<Message> getUnreadMessagesForPatient(int patientId);

    // 4. Patient marks a message as read
    boolean markMessageAsRead(int messageId);

    // 5. Patient views all messages they have sent
    List<Message> getMessagesSentByPatient(int patientId);

}
