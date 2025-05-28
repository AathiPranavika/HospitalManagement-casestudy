package com.hexaware.HospitalManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.HospitalManagement.entity.Message;
import com.hexaware.HospitalManagement.entity.Message.SenderRole;
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

	    List<Message> findByDoctorDoctorIdAndPatientPatientIdOrderBySentAtAsc(Long doctorId, Long patientId);
	    List<Message> findByDoctorDoctorIdAndSenderRole(Long doctorId, Message.SenderRole senderRole);
	    List<Message> findByPatientPatientIdAndSenderRole(Long patientId, Message.SenderRole senderRole);
		List<Message> findByPatientPatientIdAndIsReadFalseAndSenderRole(long patientId, SenderRole doctor);
		List<Message> findByDoctorDoctorIdAndIsReadFalseAndSenderRole(long doctorId, SenderRole patient);
}
