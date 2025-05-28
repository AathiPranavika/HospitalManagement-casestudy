package com.hexaware.HospitalManagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.HospitalManagement.DTO.AppointmentDTO;
import com.hexaware.HospitalManagement.DTO.MessageDTO;
import com.hexaware.HospitalManagement.DTO.PatientDTO;
import com.hexaware.HospitalManagement.entity.Appointment;
import com.hexaware.HospitalManagement.entity.MedicalRecord;
import com.hexaware.HospitalManagement.entity.Message;
import com.hexaware.HospitalManagement.entity.Patient;
import com.hexaware.HospitalManagement.entity.Prescription;
import com.hexaware.HospitalManagement.entity.User;
import com.hexaware.HospitalManagement.exception.AppointmentNotFoundException;
import com.hexaware.HospitalManagement.exception.DuplicatePatientException;
import com.hexaware.HospitalManagement.exception.UserNotFoundException;
import com.hexaware.HospitalManagement.repository.PatientRepository;
import com.hexaware.HospitalManagement.repository.UserRepository;

@Service
public class PatientServiceImpl implements IPatientService {

    @Autowired
    private PatientRepository patientRepository;

   @Autowired
   private IAppointmentService AppointmentService;
   
   @Autowired
   private UserRepository userRepo;
   
   @Autowired
   IMedicalRecordService MedicalRecordService;
   
   @Autowired
   IPrescriptionService PrescriptionService;
    
   @Autowired
   IMessageService messageService;
   
    @Override
    public Patient registerPatient(PatientDTO dto) throws DuplicatePatientException, UserNotFoundException {
        Optional<Patient> existing = patientRepository.findById(dto.getUserId());
        if (existing.isPresent()) {
            throw new DuplicatePatientException("Patient with this userId already exists");
        }
        Optional<User> userOpt = userRepo.findById(dto.getUserId());
        if (!userOpt.isPresent()) {
            throw new UserNotFoundException("User with id " + dto.getUserId() + " not found");
        }
        Patient patient = new Patient();
        patient.setUser(userOpt.get());
        patient.setAddress(dto.getAddress());
        patient.setEmergencyContact(dto.getEmergencyContact());
        patient.setBloodGroup(dto.getBloodGroup());
        patient.setMedicalHistory(dto.getMedicalHistory());
        return patientRepository.save(patient);
    }

    @Override
    public Patient updatePatient(Long patientId, PatientDTO dto) {
        Optional<Patient> optionalPatient = patientRepository.findById(patientId);
        if (optionalPatient.isPresent()) {
            Patient patient = optionalPatient.get();
            patient.setAddress(dto.getAddress());
            patient.setEmergencyContact(dto.getEmergencyContact());
            patient.setBloodGroup(dto.getBloodGroup());
            patient.setMedicalHistory(dto.getMedicalHistory());
            return patientRepository.save(patient);
        }
        return null; 
    }

    @Override
    public Patient getPatientById(Long patientId) {
        return patientRepository.findById(patientId).orElse(null);
    }

    @Override
    public boolean deletePatient(Long patientId) {
        if (patientRepository.existsById(patientId)) {
            patientRepository.deleteById(patientId);
            return true;
        }
        return false;
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public List<Patient> searchPatientsByName(String name) {
    	return patientRepository.searchPatientsByName(name);
    }

    @Override
    public List<Patient> searchPatientsByBloodGroup(String bloodGroup) {
    	return patientRepository.findByBloodGroup(bloodGroup);
    }

    @Override
    public Appointment bookAppointment(AppointmentDTO dto) throws AppointmentNotFoundException {
       return AppointmentService.bookAppointment(dto);
    }
    
    @Override
    public List<Appointment> getUpcomingAppointments(Long patientId) {
        return AppointmentService.getUpcomingAppointmentsForPatient(patientId);
    }

	@Override
	public boolean cancelAppointment(Long appointmentId) throws AppointmentNotFoundException {
		if(AppointmentService.cancelAppointmentById(appointmentId)!=null)
		{
			return true;
		}
		return false;
	}

	@Override
	public List<MedicalRecord> getMedicalRecordsByPatientId(Long patientId) {
		return MedicalRecordService.getMedicalRecordsByPatientId(patientId);
	}

	@Override
	public List<Prescription> getPrescriptionsByPatientId(Long patientId) {
		return PrescriptionService.getPrescriptionsByPatientId(patientId);
	}
	@Override
	public List<Prescription> getPrescriptionsByAppointmentId(Long appointmentId)
	{
		return PrescriptionService.getPrescriptionsByAppointmentId(appointmentId);
	}

	@Override
	public Message sendMessage(MessageDTO messageDTO) {
		
		return messageService.sendMessage(messageDTO);
	}

	@Override
	public List<Message> getMessagesBetweenDoctorAndPatient(int doctorId, int patientId) {
		return messageService.getMessagesBetweenDoctorAndPatient(doctorId, patientId);
	}

	@Override
	public List<Message> getUnreadMessagesForPatient(int patientId) {
		return messageService.getUnreadMessagesForPatient(patientId);
	}

	@Override
	public boolean markMessageAsRead(int messageId) {
		return messageService.markMessageAsRead(messageId);
	}

	@Override
	public List<Message> getMessagesSentByPatient(int patientId) {
		return messageService.getMessagesSentByPatient(patientId);
	}
   
    
}
