package com.example.Iprwc_backend;

import com.example.Iprwc_backend.DAO.ReservationRepository;
import com.example.Iprwc_backend.DAO.RoomRepository;
import com.example.Iprwc_backend.DAO.RoomTypeRepository;
import com.example.Iprwc_backend.DAO.UserRepo;
import com.example.Iprwc_backend.Model.Role;
import com.example.Iprwc_backend.Model.User;
import com.example.Iprwc_backend.Service.UserService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
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
			UserRepo userRepository) {
		return args -> {

			// userService.saveRole(new Role("ROLE_ADMIN"));

			// // check if user already exists
			// if (userRepository.findByUsername("Ward") != null) {
			userService.saveRole(new Role("ROLE_ADMIN"));
			userService.saveRole(new Role("ROLE_USER"));
			userService.saveRole(new Role("ROLE_MANAGER"));
			userService.saveUser(new User(null,
					"Ward",
					"1234",
					"wardalbam32@gmail.com",
					new ArrayList<>(), null)

			);
			userService.addRoleToUser("Ward", "ROLE_ADMIN");

			// }
			// userService.saveRole(new Role("ROLE_USER"));
			// userService.saveRole(new Role("ROLE_MANAGER"));

			// userService.saveUser(new User(null, "khaledjad", "1234", "", new
			// ArrayList<>()));
			// userService.saveUser(new User(null, "ibra", "1234", "", new ArrayList<>()));

			// userService.addRoleToUser("khaledjad", "ROLE_MANAGER");
			// userService.addRoleToUser("ibra", "ROLE_USER");

			// // create room type
			// RoomType roomType1 = new RoomType();
			// roomType1.setType(RoomTypeOptions.vergadering_ruimte_open);
			// roomType1.setCapacity(10);
			// roomType1.setDescription("Vergadering ruimte - buiten");
			// roomTypeRepository.save(roomType1);

			// // create room 1
			// Room room1 = new Room();
			// room1.setName("Room 1");
			// // room1.setRoomType(roomType1);
			// roomRepository.save(room1);

			// // create room 2
			// Room room2 = new Room();
			// room2.setName("Vergadering ruimte 1");
			// // room2.setRoomType(roomType1);
			// room2.setCapacity(5);
			// room2.setDescription("ruimte ligt op de 2e verdieping");
			// room2.setNotes("Deze ruimte is voorzien van een beamer");
			// roomRepository.save(room2);

			// // create reaservation for room 2
			// Reservation reservation1 = new Reservation();
			// reservation1.setRoom(room1);

			// // create new LocalDateTime

			// String startTimeString = "2023-08-07T10:00:00";
			// LocalDateTime startTime = LocalDateTime.parse(startTimeString);
			// reservation1.setStartTime(startTime);

			// String endTimeString = "2023-08-07T11:00:00";
			// LocalDateTime endTime = LocalDateTime.parse(endTimeString);
			// reservation1.setEndTime(endTime);

			// reservation1.setUser(
			// userRepository.findByUsername("ward")
			// );
			// // save reservation
			// reservationRepository.save(reservation1);

			// // create reaservation for room 2
			// Reservation reservation2 = new Reservation();
			// reservation2.setRoom(room2);

			// String startTimeString_2 = "2023-08-06T16:00:00";
			// LocalDateTime startTime2 = LocalDateTime.parse(startTimeString_2);
			// reservation2.setStartTime(startTime2);

			// String endTimeString_2 = "2023-08-06T18:00:00";
			// LocalDateTime endTime2 = LocalDateTime.parse(endTimeString_2);
			// reservation2.setEndTime(endTime2);

			// reservation2.setUser(
			// userRepository.findByUsername("ward")
			// );
			// // save reservation
			// reservationRepository.save(reservation2);

			// Reservation reservation3 = new Reservation();
			// reservation3.setRoom(room2);
			// String startTimeString3 = "2023-08-07T10:00:00";
			// LocalDateTime startTime3 = LocalDateTime.parse(startTimeString3);
			// reservation3.setStartTime(startTime3);

			// String endTimeString3 = "2023-08-07T11:00:00";
			// LocalDateTime endTime3 = LocalDateTime.parse(endTimeString3);
			// reservation3.setEndTime(endTime3);

			// reservation3.setUser(
			// userRepository.findByUsername("ward")
			// );
			// // save reservation
			// reservationRepository.save(reservation3);

			// // add reservation4 start 2023-08-04T15:00:00 till 2023-08-04T16:00:00
			// Reservation reservation4 = new Reservation();
			// reservation4.setRoom(room1);
			// String startTimeString4 = "2023-08-06T14:00:00";
			// LocalDateTime startTime4 = LocalDateTime.parse(startTimeString4);
			// reservation4.setStartTime(startTime4);

			// String endTimeString4 = "2023-08-06T15:00:00";
			// LocalDateTime endTime4 = LocalDateTime.parse(endTimeString4);
			// reservation4.setEndTime(endTime4);

			// reservation4.setUser(
			// userRepository.findByUsername("ward")
			// );

			// // save reservation
			// reservationRepository.save(reservation4);

			// // add roomtype, room and reservation for user khaledjad
			// // create room type
			// RoomType roomType2 = new RoomType();
			// roomType2.setType(RoomTypeOptions.vergadering_ruimte_dicht);
			// roomType2.setCapacity(5);
			// roomType2.setDescription("Vergadering ruimte - binnen");
			// roomTypeRepository.save(roomType2);

			// // create room 1
			// Room room3 = new Room();
			// room3.setName("Room 3");
			// // room3.setRoomType(roomType2);
			// roomRepository.save(room3);

			// // create reservation for room 3
			// Reservation reservation5 = new Reservation();
			// reservation5.setRoom(room3);
			// String startTimeString5 = "2023-08-07T16:00:00";
			// LocalDateTime startTime5 = LocalDateTime.parse(startTimeString5);
			// reservation5.setStartTime(startTime5);

			// String endTimeString5 = "2023-08-07T18:00:00";
			// LocalDateTime endTime5 = LocalDateTime.parse(endTimeString5);
			// reservation5.setEndTime(endTime5);

			// reservation5.setUser(
			// userRepository.findByUsername("khaledjad")
			// );
			// reservationRepository.save(reservation5);

		};

	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
