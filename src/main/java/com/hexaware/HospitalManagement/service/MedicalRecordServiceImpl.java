package com.hexaware.HospitalManagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.HospitalManagement.DTO.MedicalRecordDTO;
import com.hexaware.HospitalManagement.entity.Appointment;
import com.hexaware.HospitalManagement.entity.MedicalRecord;
import com.hexaware.HospitalManagement.exception.AppointmentNotFoundException;
import com.hexaware.HospitalManagement.exception.MedicalRecordNotFoundException;
import com.hexaware.HospitalManagement.repository.AppointmentRepository;
import com.hexaware.HospitalManagement.repository.MedicalRecordRepository;

@Service
public class MedicalRecordServiceImpl implements IMedicalRecordService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public MedicalRecord createMedicalRecord(MedicalRecordDTO dto) throws AppointmentNotFoundException {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(dto.getAppointmentId());

        if (optionalAppointment.isEmpty()) {
            throw new AppointmentNotFoundException("Appointment not found with id: " + dto.getAppointmentId());
        }

        Appointment appointment = optionalAppointment.get();

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setAppointment(appointment);
        medicalRecord.setSymptoms(dto.getSymptoms());
        medicalRecord.setPhysicalExam(dto.getPhysicalExam());
        medicalRecord.setDiagnosis(dto.getDiagnosis());
        medicalRecord.setTreatmentPlan(dto.getTreatmentPlan());
        System.out.println("Appointment ID: " + appointment.getAppointmentId());
        System.out.println("Appointment object: " + appointment);
        System.out.println("MedicalRecord object: " + medicalRecord);

        return medicalRecordRepository.save(medicalRecord);
    }

    @Override
    public MedicalRecord getMedicalRecordByAppointmentId(Long appointmentId) throws MedicalRecordNotFoundException {
        Optional<MedicalRecord> optionalRecord = medicalRecordRepository.findByAppointmentAppointmentId(appointmentId);

        if (optionalRecord.isEmpty()) {
            throw new MedicalRecordNotFoundException("Medical record not found for appointment id: " + appointmentId);
        }

        return optionalRecord.get();
    }

    @Override
    public MedicalRecord getMedicalRecordById(Long recordId) throws MedicalRecordNotFoundException {
        Optional<MedicalRecord> optionalRecord = medicalRecordRepository.findById(recordId);

        if (optionalRecord.isEmpty()) {
            throw new MedicalRecordNotFoundException("Medical record not found with id: " + recordId);
        }

        return optionalRecord.get();
    }

    @Override
    public List<MedicalRecord> getMedicalRecordsByPatientId(Long patientId) {
        return medicalRecordRepository.findByAppointmentPatientPatientId(patientId);
    }

    @Override
    public List<MedicalRecord> getMedicalRecordsByDoctorId(Long doctorId) {
        return medicalRecordRepository.findByAppointmentDoctorDoctorId(doctorId);
    }

    @Override
    public MedicalRecord updateMedicalRecord(MedicalRecordDTO dto) throws MedicalRecordNotFoundException, AppointmentNotFoundException {
        Optional<MedicalRecord> optionalRecord = medicalRecordRepository.findById(dto.getRecordId());

        if (optionalRecord.isEmpty()) {
            throw new MedicalRecordNotFoundException("Medical record not found with id: " + dto.getRecordId());
        }

        MedicalRecord record = optionalRecord.get();
        record.setSymptoms(dto.getSymptoms());
        record.setPhysicalExam(dto.getPhysicalExam());
        record.setDiagnosis(dto.getDiagnosis());
        record.setTreatmentPlan(dto.getTreatmentPlan());

        if (dto.getAppointmentId() != null && !dto.getAppointmentId().equals(record.getAppointment().getAppointmentId())) {
            Optional<Appointment> optionalAppointment = appointmentRepository.findById(dto.getAppointmentId());
            if (optionalAppointment.isEmpty()) {
                throw new AppointmentNotFoundException("Appointment not found with id: " + dto.getAppointmentId());
            }
            record.setAppointment(optionalAppointment.get());
        }

        return medicalRecordRepository.save(record);
    }

    @Override
    public boolean deleteMedicalRecord(Long recordId) throws MedicalRecordNotFoundException {
        if (!medicalRecordRepository.existsById(recordId)) {
            throw new MedicalRecordNotFoundException("Medical record not found with id: " + recordId);
        }
        medicalRecordRepository.deleteById(recordId);
        return true;
    }

    @Override
    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordRepository.findAll();
    }
}
