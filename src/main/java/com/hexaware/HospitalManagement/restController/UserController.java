package com.hexaware.HospitalManagement.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hexaware.HospitalManagement.DTO.UserDTO;
import com.hexaware.HospitalManagement.entity.User;
import com.hexaware.HospitalManagement.exception.UserNotFoundException;
import com.hexaware.HospitalManagement.service.IUserService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    @Autowired
    IUserService userService;

    @GetMapping("/getAll")
    public List<User> getAllUser() {
        log.info("Fetching all users");
        List<User> users = userService.getAllUsers();
        log.info("Total users fetched: {}", users.size());
        return users;
    }

    @GetMapping("/getById/{id}")
    public User getUserById(@PathVariable Long id) throws UserNotFoundException {
        log.info("Fetching user with ID: {}", id);
        User user = userService.getUserById(id);
        log.info("User fetched successfully: {}", user.getEmail());
        return user;
    }

    @GetMapping("/getByRole/{role}")
    public List<User> getUserByRole(@PathVariable User.Role role) {
        log.info("Fetching users with role: {}", role);
        List<User> users = userService.getUsersByRole(role);
        log.info("Total users with role {}: {}", role, users.size());
        return users;
    }

    @GetMapping("/getByEmail/{email}")
    public User getUserByEmail(@PathVariable String email) throws UserNotFoundException {
        log.info("Fetching user by email: {}", email);
        User user = userService.getUserByEmail(email);
        log.info("User fetched successfully: {}", user.getUserId());
        return user;
    }

    @PostMapping("/add")
    public User addUser(@Valid @RequestBody UserDTO userDTO) {
        log.info("Registering new user with email: {}", userDTO.getEmail());
        User user = userService.registerUser(userDTO);
        log.info("User registered successfully with ID: {}", user.getUserId());
        return user;
    }

    @PutMapping("/update/{id}")
    public User updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) throws UserNotFoundException {
        log.info("Updating user with ID: {}", id);
        User updatedUser = userService.updateUser(id, userDTO);
        log.info("User updated successfully: {}", updatedUser.getEmail());
        return updatedUser;
    }

    @DeleteMapping("/deleteById/{id}")
    public String deleteUser(@PathVariable Long id) throws UserNotFoundException {
        log.info("Deleting user with ID: {}", id);
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            log.info("User deleted successfully with ID: {}", id);
            return "User deleted successfully";
        } else {
            log.warn("Failed to delete user with ID: {}", id);
            return "Invalid user";
        }
    }
}
