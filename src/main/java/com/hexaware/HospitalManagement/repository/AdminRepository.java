package com.hexaware.HospitalManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.HospitalManagement.entity.Admin;
@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

}
