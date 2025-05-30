package com.hexaware.microService.hospitalMangement.Message.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hexaware.microService.hospitalMangement.Message.dto.MessageDTO;
import com.hexaware.microService.hospitalMangement.Message.entity.Message;
import com.hexaware.microService.hospitalMangement.Message.exception.MessageNotFoundException;
import com.hexaware.microService.hospitalMangement.Message.repository.MessageRepository;

@Service
public class MessageServiceImpl implements IMessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final String DOCTOR_SERVICE_URL = "http://localhost:8080/api/doctors";
    private static final String PATIENT_SERVICE_URL = "http://localhost:8080/api/patients";

    @Override
    public Message sendMessage(MessageDTO messageDTO) {
        Long doctorId = messageDTO.getDoctorId();
        Long patientId = messageDTO.getPatientId();

        validateDoctorAndPatient(doctorId, patientId);

        Message message = new Message();
        message.setDoctorId(doctorId);
        message.setPatientId(patientId);
        message.setSenderRole(messageDTO.getSenderRole());
        message.setMessage(messageDTO.getMessage());
        message.setRead(false);

        return messageRepository.save(message);
    }

    @Override
    public Message getMessageById(Long messageId) throws MessageNotFoundException {
        return messageRepository.findById(messageId)
                .orElseThrow(() -> new MessageNotFoundException("Message not found"));
    }

    @Override
    public List<Message> getMessagesBetweenDoctorAndPatient(Long doctorId, Long patientId) {
        validateDoctorAndPatient(doctorId, patientId);
        return messageRepository.findByDoctorIdAndPatientIdOrderBySentAtAsc(doctorId, patientId);
    }

    @Override
    public List<Message> getUnreadMessagesForPatient(Long patientId) {
        checkPatientExists(patientId);
        return messageRepository.findByPatientIdAndIsReadFalseAndSenderRole(patientId, Message.SenderRole.DOCTOR);
    }

    @Override
    public List<Message> getUnreadMessagesForDoctor(Long doctorId) {
        checkDoctorExists(doctorId);
        return messageRepository.findByDoctorIdAndIsReadFalseAndSenderRole(doctorId, Message.SenderRole.PATIENT);
    }

    @Override
    public boolean markMessageAsRead(Long messageId) {
        return messageRepository.findById(messageId).map(msg -> {
            msg.setRead(true);
            messageRepository.save(msg);
            return true;
        }).orElse(false);
    }

    @Override
    public List<Message> getMessagesSentByDoctor(Long doctorId) {
        checkDoctorExists(doctorId);
        return messageRepository.findByDoctorIdAndSenderRole(doctorId, Message.SenderRole.DOCTOR);
    }

    @Override
    public List<Message> getMessagesSentByPatient(Long patientId) {
        checkPatientExists(patientId);
        return messageRepository.findByPatientIdAndSenderRole(patientId, Message.SenderRole.PATIENT);
    }

    @Override
    public boolean deleteMessage(Long messageId) {
        if (messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
            return true;
        }
        return false;
    }

    private void validateDoctorAndPatient(Long doctorId, Long patientId) {
        checkDoctorExists(doctorId);
        checkPatientExists(patientId);
    }

    private void checkDoctorExists(Long doctorId) {
        try {
            Boolean exists = restTemplate.getForObject(DOCTOR_SERVICE_URL + "/exists/" + doctorId, Boolean.class);
            if (exists == null || !exists) {
                throw new RuntimeException("Doctor not found with ID: " + doctorId);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error validating doctor ID: " + e.getMessage());
        }
    }

    private void checkPatientExists(Long patientId) {
        try {
            Boolean exists = restTemplate.getForObject(PATIENT_SERVICE_URL + "/exists/" + patientId, Boolean.class);
            if (exists == null || !exists) {
                throw new RuntimeException("Patient not found with ID: " + patientId);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error validating patient ID: " + e.getMessage());
        }
    }
}
