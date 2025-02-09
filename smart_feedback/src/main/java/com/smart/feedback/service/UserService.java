package com.smart.feedback.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.smart.feedback.model.User;
import com.smart.feedback.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public void saveNewUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}

	public Optional<User> authenticate(String username, String password) {
		Optional<User> user = userRepository.findByUsername(username);
		if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
			return user;
		}
		return Optional.empty();
	}

	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
}
