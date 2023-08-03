package com.example.Iprwc_backend.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Iprwc_backend.DTO.ReservationDTO;
import com.example.Iprwc_backend.DTO.RoomDTO;
import com.example.Iprwc_backend.DTO.TimeSlotDTO;
import com.example.Iprwc_backend.Model.Room;
import com.example.Iprwc_backend.Service.ReservationService;
import com.example.Iprwc_backend.Service.RoomService;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    
    @Autowired
    private RoomService roomService;

    @Autowired
    private ReservationService reservationService;

    // Parse the String date into a LocalDate object
    LocalDate TestDate = LocalDate.parse("2023-08-04");

    @GetMapping
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
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
    @PostMapping("/add")
    public ResponseEntity<Room> addRoom(@RequestBody RoomDTO roomDTO) {
        Room room = roomService.addRoom(roomDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(room);
    }

    // get rooms by type
    @GetMapping("/type/{id}")
    public ResponseEntity<List<Room>> getRoomsByType(@PathVariable Long id) {
        List<Room> rooms = roomService.getRoomsByType(id);
        if (rooms != null) {
            return ResponseEntity.ok(rooms);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/available-time-slots")
    public ResponseEntity<List<TimeSlotDTO>> getAvailableTimeSlots(@RequestParam Long roomTypeId) {
        List<TimeSlotDTO> availableTimeSlots = reservationService.getAvailableTimeSlotsForRoomType(
            roomTypeId,
            TestDate
        );
        return ResponseEntity.ok(availableTimeSlots);
    }
    
    // @PostMapping("/reserve/{roomTypeId}")
    // public ResponseEntity<ReservationDTO> reserveRoom(@PathVariable Long roomTypeId, @RequestBody ReservationDTO reservationDTO) {
    //     ReservationDTO reservation = roomService.reserveRoom(roomTypeId, reservationDTO);
    //     return ResponseEntity.ok(reservation);
    // }
}

