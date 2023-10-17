package com.example.Iprwc_backend.Controller;

import java.security.Principal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.Iprwc_backend.DAO.ReservationRepository;
import com.example.Iprwc_backend.DAO.RoleRepo;
import com.example.Iprwc_backend.DAO.RoomRepository;
import com.example.Iprwc_backend.DAO.RoomTypeRepository;
import com.example.Iprwc_backend.DAO.UserRepo;
import com.example.Iprwc_backend.DTO.CreateBulkReservationsRequestDTO;
import com.example.Iprwc_backend.DTO.CreateReservationRequestDTO;
import com.example.Iprwc_backend.DTO.CreateReservationResponseDTO;
import com.example.Iprwc_backend.DTO.TimeSlotDTO;
import com.example.Iprwc_backend.DTO.TimeSlotStringDTO;
import com.example.Iprwc_backend.Model.Reservation;
import com.example.Iprwc_backend.Model.Room;
import com.example.Iprwc_backend.Model.User;
import com.example.Iprwc_backend.Service.ReservationService;
import com.example.Iprwc_backend.Service.UserService;
import com.example.Iprwc_backend.helper.RoomConfig;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

// @CrossOrigin(origins = "https://loquacious-baklava-ac398e.netlify.app/")
// @CrossOrigin(origins = "http://localhost:4200") // Allow requests from your Angular app's origin
// allow delete request
@CrossOrigin(origins = { "http://localhost:4200", "https://loquacious-baklava-ac398e.netlify.app/" })

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

    @Autowired
    private RoomConfig roomConfiguration;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepo roleRepo;

    @GetMapping
    public List<Reservation> getAllReservations(
            HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        User user = userService.getUser(principal.getName());

        return reservationService.getAllReservations(user);
    }

    // get reservations admin only > check if user is admin. return ResepomseEntity
    @GetMapping("/admin")
    public ResponseEntity<List<Reservation>> getAllReservationsAdmin(
            HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        User user = userService.getUser(principal.getName());
        System.out.println(user.toString());
        if (user.getRoles().contains(roleRepo.findByName("ROLE_ADMIN"))) {
            // find all starting from today and after
            return ResponseEntity.ok(reservationService.getAllReservationsAdmin());
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    // Spring Boot Controller
    @GetMapping("/week/{year}/{weekNumber}")
    public ResponseEntity<List<Reservation>> getReservationsByWeek(@PathVariable int year, @PathVariable int weekNumber,
            HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        User user = userService.getUser(principal.getName());
        if (user.getRoles().contains(roleRepo.findByName("ROLE_ADMIN"))) {
            LocalDate weekStartDate = LocalDate.ofYearDay(year, 1) // January 1st of the selected year
                    .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)) // Get the first Monday of the year or the
                                                                              // same day
                    .plusWeeks(weekNumber); // Subtract 1 to match the week number
            LocalDate weekEndDate = weekStartDate.plusDays(6);
            System.out.println(weekStartDate);
            return ResponseEntity.ok(reservationService.getReservationsByWeek(weekStartDate, weekEndDate));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
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

    @PostMapping("/create")
    public ResponseEntity<?> createBulkReservations(@RequestBody CreateBulkReservationsRequestDTO requestDTO,
            HttpServletRequest request) {

        // check if reservation date is in the past
        LocalDate reservationDate = requestDTO.getReservationDate();
        LocalDate currentDate = LocalDate.now();
        if (reservationDate.isBefore(currentDate)) {
            return new ResponseEntity<>("Reservation date cannot be in the past.", HttpStatus.BAD_REQUEST);
        }

        try {
            Principal principal = request.getUserPrincipal();
            User user = userService.getUser(principal.getName());

            // Create a list to store individual reservations
            List<Reservation> reservations = new ArrayList<>();

            // Iterate through the time slots and create reservations for each
            for (TimeSlotStringDTO timeSlot : requestDTO.getTimeSlots()) {
                String startTimeStr = timeSlot.getStartTime();
                String endTimeStr = timeSlot.getEndTime();

                // Parse the start and end times from the strings if needed
                LocalTime startTime = LocalTime.parse(startTimeStr);
                LocalTime endTime = LocalTime.parse(endTimeStr);

                // create startTime as localdatetime
                LocalDateTime startTimeDateTime = LocalDateTime.of(requestDTO.getReservationDate(), startTime);
                LocalDateTime endTimeDateTime = LocalDateTime.of(requestDTO.getReservationDate(), endTime);

                LocalDateTime currentDateTime = LocalDateTime.now();
                LocalDateTime startTimeWithDate = LocalDateTime.of(requestDTO.getReservationDate(), startTime);
                LocalDateTime endTimeWithDate = LocalDateTime.of(requestDTO.getReservationDate(), endTime);

                // Validate the time slot
                if (!startTime.isBefore(endTime) || startTimeWithDate.isBefore(currentDateTime)) {
                    throw new IllegalArgumentException("Invalid time slot.");
                }

                boolean availableRoom = roomRepository.findByRoomIdAndNotReserved(
                        requestDTO.getRoomId(),
                        startTimeDateTime,
                        endTimeDateTime);

                if (!availableRoom) {
                    throw new IllegalArgumentException("No room available for the selected time slot.");
                }

                Reservation reservation = new Reservation();
                reservation.setStartTime(startTimeWithDate);
                reservation.setEndTime(endTimeWithDate);
                reservation.setRoom(
                        roomRepository.findById(requestDTO.getRoomId()).get());
                reservation.setNote(requestDTO.getNote());
                reservation.setUser(user);
                // Add the reservation to the list
                reservations.add(reservation);
            }

            // Save all reservations in bulk
            List<Reservation> savedReservations = reservationService.createBulkReservations(reservations);

            // Convert the saved Reservation objects to response DTOs
            List<CreateReservationResponseDTO> responseDTOs = new ArrayList<>();
            for (Reservation savedReservation : savedReservations) {
                CreateReservationResponseDTO responseDTO = new CreateReservationResponseDTO();
                responseDTO.setId(savedReservation.getId());
                responseDTO.setStartTime(savedReservation.getStartTime());
                responseDTO.setEndTime(savedReservation.getEndTime());
                // responseDTO.setRoomTypeId(savedReservation.getRoom().getRoomType().getId());
                responseDTO.setRoomName(savedReservation.getRoom().getName());
                responseDTOs.add(responseDTO);
            }

            return new ResponseEntity<>(responseDTOs, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // Handle validation errors
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Handle other errors
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // delete by id admin

    @GetMapping("/delete/admin/{id}")
    public ResponseEntity<?> deleteReservationByIdAdmin(@PathVariable Long id, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        User user = userService.getUser(principal.getName());
        if (user.getRoles().contains(roleRepo.findByName("ROLE_ADMIN"))) {
            try {
                reservationService.deleteReservationById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    // delete by id user
    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteReservationByIdUser(@PathVariable Long id, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        User user = userService.getUser(principal.getName());
        if (user.getRoles().contains(roleRepo.findByName("ROLE_USER"))
                || user.getRoles().contains(roleRepo.findByName("ROLE_ADMIN"))) {
            try {
                reservationService.deleteReservationById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

}
