package com.hexaware.HospitalManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hexaware.HospitalManagement.entity.Patient;
import com.hexaware.HospitalManagement.entity.User;
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    boolean existsByUser(User user);

	@Query("select p from Patient p where p.user.name=:name")
	List<Patient> searchPatientsByName(String name);
	
	List<Patient> findByBloodGroup(String bloodGroup);

	List<Patient> findByUserNameContainingIgnoreCase(String name);

	boolean existsByPatientIdAndUserUserId(Long doctorId, Long userId);
}
