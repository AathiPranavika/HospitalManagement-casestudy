package com.hexaware.HospitalManagement.restController;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hexaware.HospitalManagement.DTO.MedicalRecordDTO;
import com.hexaware.HospitalManagement.entity.MedicalRecord;
import com.hexaware.HospitalManagement.exception.AppointmentNotFoundException;
import com.hexaware.HospitalManagement.exception.MedicalRecordNotFoundException;
import com.hexaware.HospitalManagement.service.IMedicalRecordService;

@RestController
@RequestMapping("/api/medicalRecords")
public class MedicalRecordController {

    private static final Logger logger = LoggerFactory.getLogger(MedicalRecordController.class);

    @Autowired
    private IMedicalRecordService medicalRecordService;

    @PostMapping("/create")
    public MedicalRecord createMedicalRecord(@RequestBody MedicalRecordDTO dto) throws AppointmentNotFoundException {
        logger.info("Creating medical record for appointmentId={}", dto.getAppointmentId());
        return medicalRecordService.createMedicalRecord(dto);
    }

    @GetMapping("/{recordId}")
    public MedicalRecord getMedicalRecordById(@PathVariable Long recordId) throws MedicalRecordNotFoundException {
        logger.info("Fetching medical record by recordId={}", recordId);
        return medicalRecordService.getMedicalRecordById(recordId);
    }

    @GetMapping("/byAppointment/{appointmentId}")
    public MedicalRecord getMedicalRecordByAppointmentId(@PathVariable Long appointmentId) throws MedicalRecordNotFoundException {
        logger.info("Fetching medical record by appointmentId={}", appointmentId);
        return medicalRecordService.getMedicalRecordByAppointmentId(appointmentId);
    }

    @GetMapping("/byPatient/{patientId}")
    public List<MedicalRecord> getMedicalRecordsByPatientId(@PathVariable Long patientId) {
        logger.info("Fetching all medical records for patientId={}", patientId);
        return medicalRecordService.getMedicalRecordsByPatientId(patientId);
    }

    @GetMapping("/byDoctor/{doctorId}")
    public List<MedicalRecord> getMedicalRecordsByDoctorId(@PathVariable Long doctorId) {
        logger.info("Fetching all medical records for doctorId={}", doctorId);
        return medicalRecordService.getMedicalRecordsByDoctorId(doctorId);
    }

    @PutMapping("/update")
    public MedicalRecord updateMedicalRecord(@RequestBody MedicalRecordDTO dto) throws MedicalRecordNotFoundException, AppointmentNotFoundException {
        logger.info("Updating medical record for recordId={}", dto.getRecordId());
        return medicalRecordService.updateMedicalRecord(dto);
    }

    @DeleteMapping("/delete/{recordId}")
    public String deleteMedicalRecord(@PathVariable Long recordId) throws MedicalRecordNotFoundException {
        logger.info("Deleting medical record with recordId={}", recordId);
        boolean deleted = medicalRecordService.deleteMedicalRecord(recordId);
        return deleted ? "Medical record deleted successfully" : "Failed to delete medical record";
    }

    @GetMapping("/getall")
    public List<MedicalRecord> getAllMedicalRecords() {
        logger.info("Fetching all medical records");
        return medicalRecordService.getAllMedicalRecords();
    }
}
