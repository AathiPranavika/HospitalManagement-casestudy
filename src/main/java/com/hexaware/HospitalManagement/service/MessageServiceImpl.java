package com.hexaware.HospitalManagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.HospitalManagement.DTO.MessageDTO;
import com.hexaware.HospitalManagement.entity.Doctor;
import com.hexaware.HospitalManagement.entity.Message;
import com.hexaware.HospitalManagement.entity.Message.SenderRole;
import com.hexaware.HospitalManagement.entity.Patient;
import com.hexaware.HospitalManagement.exception.MessageNotFoundException;
import com.hexaware.HospitalManagement.repository.DoctorRepository;
import com.hexaware.HospitalManagement.repository.MessageRepository;
import com.hexaware.HospitalManagement.repository.PatientRepository;

@Service
public class MessageServiceImpl implements IMessageService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Message sendMessage(MessageDTO messageDTO) {
        Optional<Doctor> doctorOpt = doctorRepository.findById(messageDTO.getDoctorId());
        Optional<Patient> patientOpt = patientRepository.findById(messageDTO.getPatientId());

        if (!doctorOpt.isPresent() || !patientOpt.isPresent()) {
            throw new RuntimeException("Doctor or Patient not found with given ID");
        }

        Message message = new Message();
        message.setDoctor(doctorOpt.get());
        message.setPatient(patientOpt.get());
        message.setSenderRole(messageDTO.getSenderRole());
        message.setMessage(messageDTO.getMessage());
        message.setRead(false); // default

        return messageRepository.save(message);
    }

    @Override
    public Message getMessageById(int messageId) throws MessageNotFoundException {
        Optional<Message> optionalMessage = messageRepository.findById((long) messageId);
       if(optionalMessage.isEmpty())
       {
    	   throw new MessageNotFoundException("Message not found");
       }
       return optionalMessage.get();
    }


    @Override
    public List<Message> getMessagesBetweenDoctorAndPatient(int doctorId, int patientId) {
        return messageRepository.findByDoctorDoctorIdAndPatientPatientIdOrderBySentAtAsc(
                (long) doctorId, (long) patientId);
    }

    @Override
    public List<Message> getUnreadMessagesForPatient(int patientId) {
        return messageRepository.findByPatientPatientIdAndIsReadFalseAndSenderRole(
            (long) patientId, SenderRole.DOCTOR);
    }


    @Override
    public List<Message> getUnreadMessagesForDoctor(int doctorId) {
        return messageRepository.findByDoctorDoctorIdAndIsReadFalseAndSenderRole(
            (long) doctorId, SenderRole.PATIENT);
    }


    @Override
    public boolean markMessageAsRead(int messageId) {
        Optional<Message> optionalMessage = messageRepository.findById((long) messageId);
        if (optionalMessage.isPresent()) {
            Message message = optionalMessage.get();
            message.setRead(true);
            messageRepository.save(message);
            return true;
        }
        return false;
    }

    @Override
    public List<Message> getMessagesSentByDoctor(int doctorId) {
        return messageRepository.findByDoctorDoctorIdAndSenderRole(
            (long) doctorId, SenderRole.DOCTOR);
    }

    @Override
    public List<Message> getMessagesSentByPatient(int patientId) {
        return messageRepository.findByPatientPatientIdAndSenderRole(
            (long) patientId, SenderRole.PATIENT);
    }

    @Override
    public boolean deleteMessage(int messageId) {
        Optional<Message> optionalMessage = messageRepository.findById((long) messageId);
        if (optionalMessage.isPresent()) {
            messageRepository.deleteById((long) messageId);
            return true;
        }
        return false;
    }
}
