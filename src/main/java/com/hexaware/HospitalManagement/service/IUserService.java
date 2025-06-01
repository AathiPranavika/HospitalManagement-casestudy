package com.hexaware.HospitalManagement.service;

import com.hexaware.HospitalManagement.DTO.UserDTO;
import com.hexaware.HospitalManagement.entity.User;
import com.hexaware.HospitalManagement.exception.UserNotFoundException;

import java.util.List;

public interface IUserService {

    String registerUser(UserDTO userDTO);

    User getUserById(Long userId) throws UserNotFoundException;

    User getUserByEmail(String email) throws UserNotFoundException;

    User updateUser(Long userId, UserDTO userDTO) throws UserNotFoundException;

    boolean deleteUser(Long userId) throws UserNotFoundException;

    List<User> getAllUsers();

    List<User> getUsersByRole(User.Role role);
    

}
