package com.hexaware.HospitalManagement.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.hexaware.HospitalManagement.DTO.MessageDTO;
import com.hexaware.HospitalManagement.entity.Message;
import com.hexaware.HospitalManagement.entity.Message.SenderRole;
import com.hexaware.HospitalManagement.exception.MessageNotFoundException;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@Rollback
@TestMethodOrder(OrderAnnotation.class)
class MessageServiceImplTest {

    @Autowired
    private IMessageService messageService;


    @Test
    @Order(1)
    void testSendMessage() {
        MessageDTO dto = new MessageDTO();
        dto.setDoctorId(1L);
        dto.setPatientId(1L);
        dto.setSenderRole(SenderRole.DOCTOR);
        dto.setMessage("Hello, this is a test message");

        Message saved = messageService.sendMessage(dto);
        assertNotNull(saved);
        assertNotNull(saved.getMessageId());
    }

    @Test
    @Order(2)
    void testGetMessageById() throws MessageNotFoundException {
        Message message = messageService.getMessageById(1);
        assertNotNull(message);

    }

    @Test
    @Order(3)
    void testGetMessagesBetweenDoctorAndPatient() {
        List<Message> messages = messageService.getMessagesBetweenDoctorAndPatient(1, 1);
        assertFalse(messages.isEmpty());
    }

    @Test
    @Order(4)
    void testGetUnreadMessagesForPatient() {
        List<Message> messages = messageService.getUnreadMessagesForPatient(1);
        assertFalse(messages.isEmpty());
        assertEquals(SenderRole.DOCTOR, messages.get(0).getSenderRole());
    }

    @Test
    @Order(5)
    void testGetUnreadMessagesForDoctor() {
        List<Message> messages = messageService.getUnreadMessagesForDoctor(1);
        assertFalse(messages.isEmpty());
        assertEquals(SenderRole.PATIENT, messages.get(0).getSenderRole());
    }

    @Test
    @Order(6)
    void testMarkMessageAsRead() {
        boolean result = messageService.markMessageAsRead(1);
        assertTrue(result);
    }

    @Test
    @Order(7)
    void testGetMessagesSentByDoctor() {
        List<Message> messages = messageService.getMessagesSentByDoctor(1);
        assertFalse(messages.isEmpty());
        assertEquals(SenderRole.DOCTOR, messages.get(0).getSenderRole());
    }

    @Test
    @Order(8)
    void testGetMessagesSentByPatient() {
        List<Message> messages = messageService.getMessagesSentByPatient(1);
        assertFalse(messages.isEmpty());
        assertEquals(SenderRole.PATIENT, messages.get(0).getSenderRole());
    }

    @Test
    @Order(9)
    void testDeleteMessage() {
        boolean deleted = messageService.deleteMessage(1);
        assertTrue(deleted);
    }
}
