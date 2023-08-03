package com.example.Iprwc_backend.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.Iprwc_backend.DAO.ReservationRepository;
import com.example.Iprwc_backend.DAO.RoomRepository;
import com.example.Iprwc_backend.DAO.UserRepo;
import com.example.Iprwc_backend.DTO.TimeSlotDTO;
import com.example.Iprwc_backend.Model.Reservation;
import com.example.Iprwc_backend.Model.Room;
import com.example.Iprwc_backend.Model.User;
import com.example.Iprwc_backend.Service.ReservationService;
import com.example.Iprwc_backend.helper.RoomConfig;


// @CrossOrigin(origins = "https://gifted-nobel-9ce0d0.netlify.app")
// @CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private ReservationRepository reservationRepository;

  
    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        Reservation reservation = reservationService.getReservationById(id);
        if (reservation != null) {
            return ResponseEntity.ok(reservation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // @GetMapping("/avalabletimeslots/{roomTypeId}")
    


    @PostMapping("/add")
    public Reservation createReservation(Reservation reservation, 
    @RequestHeader(name = "Authorization") String token) {
        // Get the user ID from the JWT token in the header
        // get the user username from the request
        String token_1 = token.substring("Bearer ".length());
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token_1);
        Long userId = decodedJWT.getClaim("id").asLong();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        reservation.setUser(user);

        // Check for an available room between the start and end times
        List<Room> availableRooms = roomRepository.findAvailableRoomsBetweenTimes(reservation.getStartTime(), reservation.getEndTime());

        if (availableRooms.isEmpty()) {
            // No available rooms found, handle the case as needed (e.g., return an error message)
            throw new RuntimeException("No available rooms found for the selected time slot.");
        }

        // Get the first available room and add it to the reservation
        Room availableRoom = availableRooms.get(0);
        reservation.setRoom(availableRoom);

        // Save the reservation to the database
        Reservation savedReservation = reservationRepository.save(reservation);

        // Return the room ID that is reserved
        return savedReservation;
    }




}
