package com.hexaware.hospital_manage.entity;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int doctorId;

    //optional = false->The Java reference must not be null.
    @OneToOne(optional = false,cascade = CascadeType.ALL )
    @JoinColumn(name = "userId", nullable = false,//that column in db should not be null 
    unique = true,
    foreignKey = @ForeignKey(name = "fk_doctor_user"))
    private User user;

    @Column(nullable = false, length = 30)
    private String specialization;

    
    @Column( nullable = false,columnDefinition = "INT CHECK (experienceYears >= 0)")
    private int experienceYears;

    @Column(nullable = false, length = 50)
    private String qualification;

    @Column(nullable = false, length = 50)
    private String designation;

    // Getters and Setters

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
