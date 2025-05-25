package com.hexaware.HospitalManagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.HospitalManagement.DTO.PrescriptionDTO;
import com.hexaware.HospitalManagement.entity.MedicalRecord;
import com.hexaware.HospitalManagement.entity.Prescription;
import com.hexaware.HospitalManagement.exception.MedicalRecordNotFoundException;
import com.hexaware.HospitalManagement.exception.PrescriptionNotFoundException;
import com.hexaware.HospitalManagement.repository.MedicalRecordRepository;
import com.hexaware.HospitalManagement.repository.PrescriptionRepository;

@Service
public class PrescriptionServiceImpl implements IPrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepo;

    @Autowired
    private MedicalRecordRepository medicalRecordRepo;

    @Override
    public Prescription createPrescription(PrescriptionDTO dto) throws MedicalRecordNotFoundException {
        Optional<MedicalRecord> recordOpt = medicalRecordRepo.findById(dto.getMedicalRecordId());

        if (!recordOpt.isPresent()) {
            throw new MedicalRecordNotFoundException("Medical record not found with ID: " + dto.getMedicalRecordId());
        }

        Prescription prescription = new Prescription();
        prescription.setMedicalRecord(recordOpt.get());
        prescription.setMedicineName(dto.getMedicineName());
        prescription.setDosage(dto.getDosage());
        prescription.setRemarks(dto.getRemarks());

        return prescriptionRepo.save(prescription);
    }

    @Override
    public Prescription getPrescriptionById(Long prescriptionId) throws PrescriptionNotFoundException {
        Optional<Prescription> opt = prescriptionRepo.findById(prescriptionId);
        if (!opt.isPresent()) {
            throw new PrescriptionNotFoundException("Prescription not found with ID: " + prescriptionId);
        }
        return opt.get();
    }

    @Override
    public Prescription updatePrescription(PrescriptionDTO dto) throws PrescriptionNotFoundException {
        Optional<Prescription> prescriptions = prescriptionRepo.findById(dto.getPrescriptionId());

        if (prescriptions.isEmpty()) {
            throw new PrescriptionNotFoundException("Prescription not found for medicine: " + dto.getMedicineName());
        }

        Prescription prescription = prescriptions.get();
        prescription.setMedicineName(dto.getMedicineName());
        prescription.setDosage(dto.getDosage());
        prescription.setRemarks(dto.getRemarks());

        return prescriptionRepo.save(prescription);
    }

    @Override
    public boolean deletePrescription(Long prescriptionId) throws PrescriptionNotFoundException {
        if (!prescriptionRepo.existsById(prescriptionId)) {
            throw new PrescriptionNotFoundException("Prescription not found with ID: " + prescriptionId);
        }
        prescriptionRepo.deleteById(prescriptionId);
        return true;
    }

    @Override
    public List<Prescription> getPrescriptionsByPatientId(Long patientId) {
        return prescriptionRepo.findByMedicalRecordAppointmentPatientPatientId(patientId);
    }

    @Override
    public List<Prescription> getPrescriptionsByAppointmentId(Long appointmentId) {
        return prescriptionRepo.findByMedicalRecordAppointmentAppointmentId(appointmentId);
    }

    
}
