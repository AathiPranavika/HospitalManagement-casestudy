package com.hexaware.hospital_manage.service;

import java.util.List;

import com.hexaware.hospital_manage.entity.Message;

public interface IMessageService {

	//creating and saving a message to the database
    Message sendMessage(Message message);

    Message getMessageById(int messageId);

    List<Message> getMessagesBetweenDoctorAndPatient(int doctorId, int patientId);

    List<Message> getUnreadMessagesForPatient(int patientId);

    List<Message> getUnreadMessagesForDoctor(int doctorId);

    boolean markMessageAsRead(int messageId);

    List<Message> getMessagesSentByDoctor(int doctorId);

    List<Message> getMessagesSentByPatient(int patientId);

    boolean deleteMessage(int messageId);
}
