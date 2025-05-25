package com.hexaware.HospitalManagement.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hexaware.HospitalManagement.DTO.UserDTO;
import com.hexaware.HospitalManagement.entity.User;
import com.hexaware.HospitalManagement.exception.UserNotFoundException;
import com.hexaware.HospitalManagement.service.IUserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    IUserService userService;

    @GetMapping("/getAll")
    public List<User> getAllUser() {
        return userService.getAllUsers();
    }

    @GetMapping("/getById/{id}")
    public User getUserById(@PathVariable Long id) throws UserNotFoundException {
        return userService.getUserById(id);
    }

    // CHANGE ROLE TO UPPERCASE
    @GetMapping("/getByRole/{role}")
    public List<User> getUserByRole(@PathVariable User.Role role) {
        return userService.getUsersByRole(role);
    }

    @GetMapping("/getByEmail/{email}")
    public User getUserByEmail(@PathVariable String email) throws UserNotFoundException {
        return userService.getUserByEmail(email);
    }

    @PostMapping("/add")
    public User addUser(@Valid @RequestBody UserDTO userDTO) {
        return userService.registerUser(userDTO);
    }

    @PutMapping("/update/{id}")
    public User updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) throws UserNotFoundException {
        return userService.updateUser(id, userDTO);
    }

    @DeleteMapping("/deleteById/{id}")
    public String deleteUser(@PathVariable Long id) throws UserNotFoundException {
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            return "User deleted successfully";
        } else {
            return "Invalid user";
        }
    }
}
