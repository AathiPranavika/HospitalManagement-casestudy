package com.hexaware.microService.hospitalMangement.Message.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.microService.hospitalMangement.Message.entity.Message;
import com.hexaware.microService.hospitalMangement.Message.entity.Message.SenderRole;

public interface MessageRepository extends JpaRepository<Message, Long> {
	List<Message> findByDoctorIdAndPatientIdOrderBySentAtAsc(Long doctorId, Long patientId);

    List<Message> findByPatientIdAndIsReadFalseAndSenderRole(Long patientId, SenderRole senderRole);

    List<Message> findByDoctorIdAndIsReadFalseAndSenderRole(Long doctorId, SenderRole senderRole);

    List<Message> findByDoctorIdAndSenderRole(Long doctorId, SenderRole senderRole);

    List<Message> findByPatientIdAndSenderRole(Long patientId, SenderRole senderRole);
}
