package com.example.Iprwc_backend;

import com.example.Iprwc_backend.Model.Role;
import com.example.Iprwc_backend.Model.User;
import com.example.Iprwc_backend.Service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.UUID;

@SpringBootApplication
public class IprwcBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(IprwcBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserService userService){
		return args -> {
			userService.saveRole(new Role("ROLE_USER"));
			userService.saveRole(new Role("ROLE_MANAGER"));
			userService.saveRole(new Role("ROLE_ADMIN"));

			userService.saveUser(new User(null, "Ward albam", "ward", "1234", new ArrayList<>()) );
			userService.saveUser(new User(null, "khaled jad", "khaledjad", "54321", new ArrayList<>()) );
			userService.saveUser(new User(null, "ibrahim", "ibra", "090909", new ArrayList<>()) );

			userService.addRoleToUser("ward", "ROLE_ADMIN");
			userService.addRoleToUser("ward", "ROLE_MANAGER");
			userService.addRoleToUser("khaledjad", "ROLE_USER");
			userService.addRoleToUser("ibra", "ROLE_USER");
		};
	}
	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
}

