package com.hexaware.HospitalManagement.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.HospitalManagement.DTO.DoctorDTO;
import com.hexaware.HospitalManagement.DTO.MedicalRecordDTO;
import com.hexaware.HospitalManagement.DTO.PrescriptionDTO;
import com.hexaware.HospitalManagement.entity.Appointment;
import com.hexaware.HospitalManagement.entity.Doctor;
import com.hexaware.HospitalManagement.entity.MedicalRecord;
import com.hexaware.HospitalManagement.entity.Message;
import com.hexaware.HospitalManagement.entity.Prescription;
import com.hexaware.HospitalManagement.entity.User;
import com.hexaware.HospitalManagement.exception.AppointmentNotFoundException;
import com.hexaware.HospitalManagement.exception.DoctorNotFoundException;
import com.hexaware.HospitalManagement.exception.MedicalRecordNotFoundException;
import com.hexaware.HospitalManagement.exception.PrescriptionNotFoundException;
import com.hexaware.HospitalManagement.exception.UserNotFoundException;
import com.hexaware.HospitalManagement.repository.AppointmentRepository;
import com.hexaware.HospitalManagement.repository.DoctorRepository;
import com.hexaware.HospitalManagement.repository.UserRepository;

@Service
public class DoctorServiceImpl implements IDoctorService {

    @Autowired
    DoctorRepository doctorRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    AppointmentRepository AppointmentRepo;
    
    @Autowired
    IMedicalRecordService MedicalRecordService;
    
    @Autowired
    IPrescriptionService PrescriptionService;
    
    @Override
    public Doctor registerDoctor(DoctorDTO dto) throws UserNotFoundException {
        Optional<User> userOpt = userRepo.findById(dto.getUserId());
        if (userOpt.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        Doctor doctor = new Doctor();
        doctor.setUser(userOpt.get());
        doctor.setSpecialization(dto.getSpecialization());
        doctor.setExperienceYears(dto.getExperienceYears());
        doctor.setQualification(dto.getQualification());
        doctor.setDesignation(dto.getDesignation());

        return doctorRepo.save(doctor);
    }

    @Override
    public Doctor updateDoctor(Long id, DoctorDTO dto) throws UserNotFoundException {
        Optional<Doctor> existing = doctorRepo.findById(id);
        if (existing.isEmpty()) {
            throw new DoctorNotFoundException("Doctor not found");
        }

        Optional<User> userOpt = userRepo.findById(dto.getUserId());
        if (userOpt.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        Doctor updated = new Doctor();
        updated.setDoctorId(id);
        updated.setUser(userOpt.get());
        updated.setSpecialization(dto.getSpecialization());
        updated.setExperienceYears(dto.getExperienceYears());
        updated.setQualification(dto.getQualification());
        updated.setDesignation(dto.getDesignation());

        return doctorRepo.save(updated);
    }

    @Override
    public Doctor getDoctorById(Long doctorId) {
        return doctorRepo.findById(doctorId).orElse(null);
    }

    @Override
    public boolean deleteDoctor(Long doctorId) {
        if (doctorRepo.existsById(doctorId)) {
            doctorRepo.deleteById(doctorId);
            return true;
        }
        return false;
    }

    @Override
    public List<Doctor> getDoctorsBySpecialization(String specialization) {
        return doctorRepo.findBySpecialization(specialization);
    }

    @Override
    public List<Doctor> getDoctorsByDesignation(String designation) {
        return doctorRepo.findByDesignation(designation);
    }

    @Override
    public List<Doctor> getDoctorsByGender(String gender) {
        return doctorRepo.getDoctorsByGender(gender);
    }

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepo.findAll();
    }

    @Override
    public List<Doctor> searchDoctorsByName(String name) {
        return doctorRepo.searchDoctorsByName(name);
    }

    @Override
    public List<Appointment> getAppointments(Long doctorId) {
        return AppointmentRepo.findAll();
    }

    @Override
    public List<Appointment> getUpcomingAppointments(Long doctorId) {
        return AppointmentRepo.findByDoctorDoctorIdAndAppointmentDateAfter(doctorId, LocalDate.now());
    }

    @Override
    public MedicalRecord addMedicalRecord(MedicalRecordDTO medicalRecordDTO) throws AppointmentNotFoundException {
    	
    	return MedicalRecordService.createMedicalRecord(medicalRecordDTO);
       
    }
    

    @Override
    public MedicalRecord updateMedicalRecord(MedicalRecordDTO updatedRecord) throws MedicalRecordNotFoundException, AppointmentNotFoundException {
    	return MedicalRecordService.updateMedicalRecord(updatedRecord);
    	
    }
    
    @Override
    public List<MedicalRecord> getPatientMedicalHistory(Long patientId) {
    	return MedicalRecordService.getMedicalRecordsByPatientId(patientId);
    }
    @Override
    public Prescription addPrescription(PrescriptionDTO prescriptionDTO) throws MedicalRecordNotFoundException {
    	return PrescriptionService.createPrescription(prescriptionDTO);
    	
    }

    @Override
    public Prescription updatePrescription(PrescriptionDTO prescriptionDTO) throws PrescriptionNotFoundException {
    	return PrescriptionService.updatePrescription(prescriptionDTO);
    }

   

    @Override
    public boolean sendMessage(Long doctorId, Long patientId, String messageContent) {
        return false;
    }

    @Override
    public List<Message> getMessagesBetweenDoctorAndPatient(Long doctorId, Long patientId) {
        return null;
    }
}
