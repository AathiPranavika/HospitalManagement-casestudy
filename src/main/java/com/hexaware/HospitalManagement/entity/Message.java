package com.hexaware.HospitalManagement.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Table(name="messages")
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @ManyToOne
    @JoinColumn(name = "doctorId", nullable = false,
                foreignKey = @ForeignKey(name = "fk_msg_doctor"))
    @NotNull(message = "Doctor must be specified")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patientId", nullable = false,
                foreignKey = @ForeignKey(name = "fk_msg_patient"))
    @NotNull(message = "Patient must be specified")
    private Patient patient;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,
            columnDefinition = "ENUM('DOCTOR', 'PATIENT')")
    @NotNull(message = "Sender role must be specified")
    private SenderRole senderRole;

    @Column(nullable = false, columnDefinition = "TEXT")
    @NotBlank(message = "Message content cannot be blank")
    @Size(max = 200, message = "Message must be at most 200 characters")
    private String message;

    @CreationTimestamp
    @Column(insertable = false, updatable = false)
    private LocalDateTime sentAt;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isRead = false;

    public enum SenderRole {
        DOCTOR, PATIENT
    }

    // Getters and Setters

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public SenderRole getSenderRole() {
        return senderRole;
    }

    public void setSenderRole(SenderRole senderRole) {
        this.senderRole = senderRole;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

	@Override
	public String toString() {
		return "Message [messageId=" + messageId + ", doctor=" + doctor + ", patient=" + patient + ", senderRole="
				+ senderRole + ", message=" + message + ", sentAt=" + sentAt + ", isRead=" + isRead + "]";
	}
    
    
    
}
