package com.hexaware.HospitalManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hexaware.HospitalManagement.entity.Doctor;
import com.hexaware.HospitalManagement.entity.User;
@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

	List<Doctor> findBySpecialization(String Specialization);
	
	List<Doctor> findByDesignation(String designation);
	
    boolean existsByUser(User user);

	//getting data in user table
	@Query("select d from Doctor d where d.user.gender=?1")
	List<Doctor> getDoctorsByGender(String gender);
	
	@Query("select d from Doctor d where d.user.name=:name")
	List<Doctor> searchDoctorsByName(@Param("name") String name);

	
}
