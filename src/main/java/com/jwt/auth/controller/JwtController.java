package com.jwt.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.auth.entities.JwtAuthEntity;
import com.jwt.auth.entities.JwtToken;
import com.jwt.auth.services.CustomUserDetailsService;
import com.jwt.auth.util.JwtUtil;

@RestController
public class JwtController {
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/token")
	public ResponseEntity<?> generateToken(@RequestBody JwtAuthEntity user) throws Exception{
		System.out.println(user);
		try {
			this.authenticationManager
			.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		UserDetails loadUserByUsername = this.customUserDetailsService.loadUserByUsername(user.getUsername());
		String token = this.jwtUtil.generateToken(loadUserByUsername);
		System.out.println(token);
		
		return new ResponseEntity<JwtToken>(new JwtToken(token),HttpStatus.OK);
	}
}
