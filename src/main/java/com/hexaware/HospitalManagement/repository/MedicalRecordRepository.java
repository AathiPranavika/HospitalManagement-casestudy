package com.hexaware.HospitalManagement.repository;

import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.hexaware.HospitalManagement.entity.MedicalRecord;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

    Optional<MedicalRecord> findByAppointmentAppointmentId(Long appointmentId);

    List<MedicalRecord> findByAppointmentPatientPatientId(Long patientId);

    List<MedicalRecord> findByAppointmentDoctorDoctorId(Long doctorId);


}
