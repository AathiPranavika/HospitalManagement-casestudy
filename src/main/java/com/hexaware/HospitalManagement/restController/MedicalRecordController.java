package com.hexaware.HospitalManagement.restController;

import java.util.List;

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

    @Autowired
    private IMedicalRecordService medicalRecordService;

    @PostMapping("/create")
    public MedicalRecord createMedicalRecord(@RequestBody MedicalRecordDTO dto) throws AppointmentNotFoundException {
        return medicalRecordService.createMedicalRecord(dto);
    }

    @GetMapping("/{recordId}")
    public MedicalRecord getMedicalRecordById(@PathVariable Long recordId) throws MedicalRecordNotFoundException {
        return medicalRecordService.getMedicalRecordById(recordId);
    }

    @GetMapping("/byAppointment/{appointmentId}")
    public MedicalRecord getMedicalRecordByAppointmentId(@PathVariable Long appointmentId) throws MedicalRecordNotFoundException {
        return medicalRecordService.getMedicalRecordByAppointmentId(appointmentId);
    }

    @GetMapping("/byPatient/{patientId}")
    public List<MedicalRecord> getMedicalRecordsByPatientId(@PathVariable Long patientId) {
        return medicalRecordService.getMedicalRecordsByPatientId(patientId);
    }

    @GetMapping("/byDoctor/{doctorId}")
    public List<MedicalRecord> getMedicalRecordsByDoctorId(@PathVariable Long doctorId) {
        return medicalRecordService.getMedicalRecordsByDoctorId(doctorId);
    }

    @PutMapping("/update")
    public MedicalRecord updateMedicalRecord(@RequestBody MedicalRecordDTO dto) throws MedicalRecordNotFoundException, AppointmentNotFoundException {
        return medicalRecordService.updateMedicalRecord(dto);
    }

    @DeleteMapping("/delete/{recordId}")
    public String deleteMedicalRecord(@PathVariable Long recordId) throws MedicalRecordNotFoundException {
        boolean deleted = medicalRecordService.deleteMedicalRecord(recordId);
        return deleted ? "Medical record deleted successfully" : "Failed to delete medical record";
    }

    @GetMapping("/getall")
    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordService.getAllMedicalRecords();
    }
}
