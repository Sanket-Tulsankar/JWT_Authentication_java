package com.jwt.auth.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jwt.auth.repository.UserRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	com.jwt.auth.entities.User findByUsername = null;
	try {
		findByUsername = this.userRepo.findByUsername(username);
	} catch (Exception e) {
		e.printStackTrace();
	}
			if(username.equals(findByUsername.getUsername())) {
				return new User(username, findByUsername.getPassword(), new ArrayList<>());
			}
			else 
			{
				throw new UsernameNotFoundException("Username not found !!");
		}
	}

}
