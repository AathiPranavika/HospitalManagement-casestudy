package com.hexaware.HospitalManagement.restController;
/**
* REST controller for authentication-related operations in the Hospital Management System.
* * 
* @author Aathi Pranavika
* @version 1.0
* */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.HospitalManagement.DTO.AuthRequestDTO;
import com.hexaware.HospitalManagement.DTO.UserDTO;
import com.hexaware.HospitalManagement.entity.User;
import com.hexaware.HospitalManagement.service.IUserService;
import com.hexaware.HospitalManagement.service.JwtService;


@RestController
@RequestMapping("api/users")
public class AuthController {

	@Autowired
	IUserService service;

	@Autowired
	JwtService jwtService;

	@Autowired
	AuthenticationManager authenticationManager;

	Logger logger = LoggerFactory.getLogger(AuthController.class);

	@PostMapping("/registration/new")
	public String addNewUser(@RequestBody UserDTO userInfo) {
		return service.registerUser(userInfo);
	}

	@PostMapping("/login/authenticate")
	public String authenticateAndGetToken(@RequestBody AuthRequestDTO authRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
		
		

		String token = null;

		if (authentication.isAuthenticated()) {

			token = jwtService.generateToken(authRequest.getUserName());

			logger.info("Token : " + token);

		} else {

			logger.info("invalid");

			throw new UsernameNotFoundException("UserName or Password in Invalid / Invalid Request");

		}

		return token;

	}

}
