package com.jwt.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jwt.auth.entities.User;

public interface UserRepo extends JpaRepository<User, Long>{

	public User findByUsername(String username) throws Exception;
}
