package com.example.Iprwc_backend.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Iprwc_backend.DAO.ReservationRepository;
import com.example.Iprwc_backend.DAO.RoomRepository;
import com.example.Iprwc_backend.DAO.RoomTypeRepository;
import com.example.Iprwc_backend.DTO.ReservationDTO;
import com.example.Iprwc_backend.DTO.RoomDTO;
import com.example.Iprwc_backend.DTO.TimeSlotDTO;
import com.example.Iprwc_backend.Model.Reservation;
import com.example.Iprwc_backend.Model.Room;
import com.example.Iprwc_backend.Model.RoomType;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository, RoomTypeRepository roomTypeRepo, ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.roomTypeRepository = roomTypeRepo;
        this.reservationRepository = reservationRepository;
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id).orElse(null);
    }

    public Room addRoom(RoomDTO roomDTO) {
        // First, fetch the RoomType by its ID
        RoomType roomType = roomTypeRepository.findById(roomDTO.getRoomTypeId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid RoomType ID"));

        // Create a new Room entity and set its properties
        Room room = new Room();
        room.setName(roomDTO.getName());
        room.setCapacity(roomDTO.getCapacity());
        room.setDescription(roomDTO.getDescription());
        room.setRoomType(roomType);

        // Save the new room to the database
        return roomRepository.save(room);
    }

    public List<Room> getRoomsByType(Long roomTypeId) {
        RoomType roomType = roomTypeRepository.findById(roomTypeId).orElse(null);
        return roomRepository.findByRoomType(roomType);
    }

    public List<TimeSlotDTO> getAvailableTimeSlotsForRoomType(String roomTypeId) {
        return null;
    }

    
}

