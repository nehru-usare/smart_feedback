package com.smart.feedback.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.smart.feedback.model.User;
import com.smart.feedback.service.UserService;

import jakarta.annotation.PostConstruct;

@Component
public class AdminUserInitializerComponent {

	@Autowired
	private UserService userService;

	@PostConstruct
	public void initializeAdminUser() {
		if (userService.findByUsername("SMART_ADMIN").isEmpty()) {
			User admin = new User();
			admin.setUsername("SMART_ADMIN");
			admin.setPassword("welcome123");
			admin.setRole("APP_ADMIN");
			userService.saveNewUser(admin);
		} else {
			System.out.println("SMART_ADMIN already exists in the database.");
		}
		System.out.println("SMART_ADMIN user's password is 'welcome123'");

	}
}
