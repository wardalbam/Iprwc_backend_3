package com.example.Iprwc_backend;

import com.example.Iprwc_backend.DAO.ReservationRepository;
import com.example.Iprwc_backend.DAO.RoomRepository;
import com.example.Iprwc_backend.DAO.RoomTypeRepository;
import com.example.Iprwc_backend.DAO.UserRepo;
import com.example.Iprwc_backend.Model.Reservation;
import com.example.Iprwc_backend.Model.Role;
import com.example.Iprwc_backend.Model.Room;
import com.example.Iprwc_backend.Model.RoomType;
import com.example.Iprwc_backend.Model.RoomTypeOptions;
import com.example.Iprwc_backend.Model.User;
import com.example.Iprwc_backend.Service.UserService;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;

@SpringBootApplication
public class IprwcBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(IprwcBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner run(
		UserService userService,
		PasswordEncoder passwordEncoder,
		ReservationRepository reservationRepository,
		RoomRepository roomRepository,
		RoomTypeRepository roomTypeRepository,
		UserRepo userRepository){
		return args -> {
			userService.saveRole(new Role("ROLE_USER"));
			userService.saveRole(new Role("ROLE_MANAGER"));
			userService.saveRole(new Role("ROLE_ADMIN"));

		
			userService.saveUser(new User(null, 
			"Ward", 
			"ward", 
			"1234", 
			"wardalbam32@gmail.com",
			new ArrayList<>()));

			userService.saveUser(new User(null, "Khaled", "khaledjad", "1234", "", new ArrayList<>()));
			userService.saveUser(new User(null, "Ibra", "ibra", "1234", "", new ArrayList<>()));


			userService.addRoleToUser("ward", "ROLE_ADMIN");
			userService.addRoleToUser("khaledjad", "ROLE_MANAGER");
			userService.addRoleToUser("ibra", "ROLE_USER");


			// create room type 
			RoomType roomType1 = new RoomType();
			roomType1.setType(RoomTypeOptions.GROOT);
			roomTypeRepository.save(roomType1);

			// create room 1
			Room room1 = new Room();
			room1.setName("Room 1");
			room1.setRoomType(roomType1);
			roomRepository.save(room1);
			// create reaservation for room 2
			Reservation reservation1 = new Reservation();
			reservation1.setRoom(room1);

			// create new LocalDateTime
			
			String startTimeString = "2021-08-04T10:00:00";
			LocalDateTime startTime = LocalDateTime.parse(startTimeString);
			reservation1.setStartTime(startTime);

			String endTimeString = "2021-08-04T11:00:00";
			LocalDateTime endTime = LocalDateTime.parse(endTimeString);
			reservation1.setEndTime(endTime);

			reservation1.setUser(
				userRepository.findByUsername("ward")
			);
			// save reservation
			reservationRepository.save(reservation1);



			
			
		};
	}
	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
}

