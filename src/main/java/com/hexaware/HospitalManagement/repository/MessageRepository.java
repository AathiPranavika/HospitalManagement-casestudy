package com.hexaware.HospitalManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.HospitalManagement.entity.Message;
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

}
