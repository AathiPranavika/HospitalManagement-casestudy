package com.hexaware.HospitalManagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hexaware.HospitalManagement.entity.User;
import com.hexaware.HospitalManagement.entity.User.Role;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	//You can ask the Optional:if have a value? return it,if not found give orElse() part
	public Optional<User> findByEmail(String email);
	
	public List<User> findByRole(Role role);
	


}
