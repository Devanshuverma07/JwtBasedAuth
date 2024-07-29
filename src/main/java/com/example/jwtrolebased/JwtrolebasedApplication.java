package com.example.jwtrolebased;

import com.example.jwtrolebased.entities.Role;
import com.example.jwtrolebased.entities.User;
import com.example.jwtrolebased.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@SpringBootApplication
public class JwtrolebasedApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(JwtrolebasedApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// List<User> admins = getUsersByRole(Role.ADMIN);
		// admins.forEach(admin -> System.out.println(admin.getEmail()));
	}

	public User signin(String email) {
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
	}

	public List<User> getUsersByRole(Role role) {
		List<User> users = userRepository.findByRole(role);
		if (users.isEmpty()) {
			throw new RuntimeException("No users found with role " + role);
		}
		return users;
	}

}
