package com.hexaware.hospital_manage.service;

import java.util.List;

import com.hexaware.hospital_manage.entity.User;

public interface IUserService {

    User registerUser(User user);
    
    User login(String email, String password);
    
    User getUserByEmail(String email);
    // Get all users by role (e.g., all doctors or all patients)
    
    //User.Role->data type of parameter->classname.enumName->gives enum type
    List<User> getUsersByRole(User.Role role);
    
    User updateUserProfile(int userId, User updatedUser);
    
    boolean changePassword(int userId, String oldPassword, String newPassword);

    boolean deleteUser(int userId);

    

}
