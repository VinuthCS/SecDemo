package com.fullstack.tekandco.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fullstack.tekandco.dao.UserToken;
import com.fullstack.tekandco.dao.ValidateUser;
import com.fullstack.tekandco.services.JWTUtil;

@RestController
public class LoginController {
	
	@Autowired
	JWTUtil jwtUtil;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@GetMapping("/")
	public String home() {
		return "Home";
	}
	
	@GetMapping("/user")
	public String user() {
		return "Welcome User";
	}
	
	@PostMapping("/login")
	public UserToken login(@RequestBody ValidateUser user) {
		try {
			authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(user.getUserName(), 
						user.getPassword()));
		}catch(Exception e) {
			return new UserToken(null);
		}
		UserToken userToken = new UserToken(jwtUtil.generteToken(user.getUserName()));
		return userToken;
	}

}
