package com.hexaware.HospitalManagement.DTO;

import com.hexaware.HospitalManagement.entity.Message.SenderRole;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MessageDTO {

    private Long messageId;

    @NotNull(message = "Doctor ID must not be null")
    private Long doctorId;

    @NotNull(message = "Patient ID must not be null")
    private Long patientId;

    @NotNull(message = "Sender role must be specified")
    private SenderRole senderRole;

    @NotBlank(message = "Message content cannot be blank")
    private String message;

    private boolean isRead;


    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
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

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }

    @Override
    public String toString() {
        return "MessageDTO [messageId=" + messageId + ", doctorId=" + doctorId + ", patientId=" + patientId
                + ", senderRole=" + senderRole + ", message=" + message + ", isRead=" + isRead + "]";
    }
}
