package com.hexaware.microService.hospitalMangement.Message.service;

import java.util.List;
import java.util.Optional;

import com.hexaware.microService.hospitalMangement.Message.dto.MessageDTO;
import com.hexaware.microService.hospitalMangement.Message.entity.Message;
import com.hexaware.microService.hospitalMangement.Message.exception.MessageNotFoundException;

public interface IMessageService {

    Message sendMessage(MessageDTO messageDTO);

    Message getMessageById(Long messageId) throws MessageNotFoundException;

    List<Message> getMessagesBetweenDoctorAndPatient(Long doctorId, Long patientId);

    List<Message> getUnreadMessagesForPatient(Long patientId);

    List<Message> getUnreadMessagesForDoctor(Long doctorId);

    boolean markMessageAsRead(Long messageId);

    List<Message> getMessagesSentByDoctor(Long doctorId);

    List<Message> getMessagesSentByPatient(Long patientId);

    boolean deleteMessage(Long messageId);


}
