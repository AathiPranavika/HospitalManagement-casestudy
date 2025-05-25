package com.hexaware.HospitalManagement.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.HospitalManagement.entity.User;
import com.hexaware.HospitalManagement.service.IUserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController 
{
	  @Autowired
      IUserService userService;
	  
	  @GetMapping("/getAll")
	  public List<User> getAllUser()
	  {
		 return userService.getAllUsers();
	  }
	  
	  @GetMapping("/getById/{id}")
	  public User getUserById(@PathVariable Long id)
	  {
		  return userService.getUserById(id);
	  }
	  
	  //CHANGE ROLE TO UPPERCASE
	  @GetMapping("/getByRole/{role}")
	  public List<User> getUserByRole(@PathVariable User.Role role)
	  {
		  return userService.getUsersByRole(role);
	  }
	  
	  @GetMapping("/getByEmai/{email}")
	  public User getUserById(@PathVariable String email )
	  {
		  return userService.getUserByEmail(email);
	  }
	  
	  @PostMapping("/add")
	  public User addUser(@Valid @RequestBody User user)
	  {
		  return userService.registerUser(user);
	  }
	  
	  @PutMapping("/update")
	  public User updateUser(@Valid @RequestBody User user)
	  {
		  return userService.updateUser(user);
	  }
	  
	  @DeleteMapping("/deleteById/{id}")
	  public String deleteUser(@PathVariable Long id)
	  {
		  if(userService.deleteUser(id))
		  {
			  return "user deleted successfully";
		  }
		  return "invalid user";
	  }
}
