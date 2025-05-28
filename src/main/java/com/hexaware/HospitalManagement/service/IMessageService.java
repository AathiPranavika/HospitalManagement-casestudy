package com.hexaware.HospitalManagement.service;

import java.util.List;

import com.hexaware.HospitalManagement.DTO.MessageDTO;
import com.hexaware.HospitalManagement.entity.Message;
import com.hexaware.HospitalManagement.exception.MessageNotFoundException;

public interface IMessageService {

    // Create and save a message to the database
    Message sendMessage(MessageDTO messageDTO);

    Message getMessageById(int messageId) throws MessageNotFoundException;

    List<Message> getMessagesBetweenDoctorAndPatient(int doctorId, int patientId);

    List<Message> getUnreadMessagesForPatient(int patientId);

    List<Message> getUnreadMessagesForDoctor(int doctorId);

    boolean markMessageAsRead(int messageId);

    List<Message> getMessagesSentByDoctor(int doctorId);

    List<Message> getMessagesSentByPatient(int patientId);

    boolean deleteMessage(int messageId);
}
