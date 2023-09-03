package com.example.Iprwc_backend.Controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Iprwc_backend.DTO.ReservationAvalabilityResponseDTO;
import com.example.Iprwc_backend.DTO.RoomDTO;
import com.example.Iprwc_backend.DTO.TimeSlotDTO;
import com.example.Iprwc_backend.Model.Room;
import com.example.Iprwc_backend.Service.ReservationService;
import com.example.Iprwc_backend.Service.RoomService;
import com.example.Iprwc_backend.Service.UserService;
import com.example.Iprwc_backend.helper.RoomConfig;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {


    @Autowired
    private RoomService roomService;

    @Autowired
    private ReservationService reservationService;

    // roomConfiguration
    @Autowired
    private RoomConfig roomConfiguration;

    @Autowired
    private UserService userService;

    // Parse the String date into a LocalDate object
    // LocalDate TestDate = LocalDate.parse("2023-08-05");

    @GetMapping
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }


    // delete room by id check if admin!
    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable("id") Long id,
                                         HttpServletRequest request
    ){
        // check if admin
        if( !userService.checkIfUserHasAdminRole(request) ){
            return new ResponseEntity<>("Only admin can delete rooms!",HttpStatus.FORBIDDEN);
        }
        try{
            roomService.removeRoomById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }






    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
        Room room = roomService.getRoomById(id);
        if (room != null) {
            return ResponseEntity.ok(room);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Add endpoints for adding, updating, and deleting rooms
    // add room
    @PostMapping("/create")
    public ResponseEntity<?> addRoom(@RequestBody RoomDTO roomDTO, HttpServletRequest request) {

        System.out.println("<<<<<<<<<<<<<<<<<<<<<");
        System.out.println("roomDTO: " + roomDTO);
        

        // check if admin
        if( !userService.checkIfUserHasAdminRole(request) ){
            return new ResponseEntity<>("Only admin can delete rooms!",HttpStatus.FORBIDDEN);
        }
        try{
            Room room = roomService.addRoom(roomDTO);
            return new ResponseEntity<>(room,HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // // get rooms by type
    // @GetMapping("/type/{id}")
    // public ResponseEntity<List<Room>> getRoomsByType(@PathVariable Long id) {
    //     List<Room> rooms = roomService.getRoomsByType(id);
    //     if (rooms != null) {
    //         return ResponseEntity.ok(rooms);
    //     } else {
    //         return ResponseEntity.notFound().build();
    //     }
    // }


    @GetMapping("/available-time-slots")
    public ResponseEntity<ReservationAvalabilityResponseDTO> getReservationAvailability(
            @RequestParam String roomId,
             @RequestParam String date) {
        
                
                System.out.println("date: " + date); // date: 2023-08-25
                System.out.println("roomId: " + roomId);
                // convert the String date to a LocalDate object
                LocalDate localDate = LocalDate.parse(date);
                System.out.println("localDate: " + localDate); // localDate: 2023-08-25

         List<TimeSlotDTO> unAvailableTimeSlotDTOs = reservationService.getUnavailableTimeSlotsByDate(
            localDate,
            Long.parseLong(roomId)
        );
        
        // Create ReservationAvalabilityResponseDTO
        // Get the opening and closing times from the room configuration
        LocalTime openingTime = roomConfiguration.getOpeningTimeForDay(localDate);
        LocalTime closingTime = roomConfiguration.getClosingTimeAsLocalTime();

        // Create ReservationAvalabilityResponseDTO
        ReservationAvalabilityResponseDTO responseDTO = new ReservationAvalabilityResponseDTO();
        responseDTO.setOpeningTime(openingTime);
        responseDTO.setClosingTime(closingTime);
        responseDTO.setUnavailableTimeSlots(unAvailableTimeSlotDTOs);
        
        return ResponseEntity.ok(responseDTO);
    }
    
}

