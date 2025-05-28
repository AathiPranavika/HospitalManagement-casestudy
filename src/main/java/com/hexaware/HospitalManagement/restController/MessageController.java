package com.hexaware.HospitalManagement.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hexaware.HospitalManagement.DTO.MessageDTO;
import com.hexaware.HospitalManagement.entity.Message;
import com.hexaware.HospitalManagement.exception.MessageNotFoundException;
import com.hexaware.HospitalManagement.service.IMessageService;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

	@Autowired
	private IMessageService messageService;

	@PostMapping("/send")
	public Message sendMessage(@RequestBody MessageDTO messageDTO) {
		return messageService.sendMessage(messageDTO);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Message> getMessageById(@PathVariable("id") int messageId) throws MessageNotFoundException {
		Message message = messageService.getMessageById(messageId);
		if (message != null) {
			return ResponseEntity.ok(message);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/between")
	public List<Message> getMessagesBetweenDoctorAndPatient(@RequestParam int doctorId, @RequestParam int patientId) {
		return messageService.getMessagesBetweenDoctorAndPatient(doctorId, patientId);
	}

	@GetMapping("/unread/patient/{patientId}")
	public List<Message> getUnreadMessagesForPatient(@PathVariable int patientId) {
		return messageService.getUnreadMessagesForPatient(patientId);
	}

	@GetMapping("/unread/doctor/{doctorId}")
	public List<Message> getUnreadMessagesForDoctor(@PathVariable int doctorId) {
		return messageService.getUnreadMessagesForDoctor(doctorId);
	}

	@PutMapping("/mark-read/{id}")
	public ResponseEntity<String> markMessageAsRead(@PathVariable("id") int messageId) {
		boolean success = messageService.markMessageAsRead(messageId);
		if (success) {
			return new ResponseEntity<>("Message marked as read.", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Message not found.", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/sent/doctor/{doctorId}")
	public List<Message> getMessagesSentByDoctor(@PathVariable int doctorId) {
		return messageService.getMessagesSentByDoctor(doctorId);
	}

	@GetMapping("/sent/patient/{patientId}")
	public List<Message> getMessagesSentByPatient(@PathVariable int patientId) {
		return messageService.getMessagesSentByPatient(patientId);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteMessage(@PathVariable("id") int messageId) {
		boolean deleted = messageService.deleteMessage(messageId);
		if (deleted) {
			return new ResponseEntity<>("Message deleted successfully.", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Message not found.", HttpStatus.BAD_REQUEST);
		}
	}
}
