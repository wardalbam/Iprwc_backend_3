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

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id).orElse(null);
    }

    // update Room details
    public Room updateRoomDetails(RoomDTO roomDto, Long id) {

        Room room = roomRepository.findById(id).orElse(null);
        if (room != null) {
            room.setName(roomDto.getName());
            room.setCapacity(roomDto.getCapacity());
            room.setDescription(roomDto.getDescription());
            room.setNotes(roomDto.getNotes());
            room.setImage(roomDto.getImage());

            roomRepository.save(room);
        }
        return room;
    }

    public Room addRoom(RoomDTO roomDTO) {
        // Create a new Room entity and set its properties
        Room room = new Room();
        room.setName(roomDTO.getName());
        room.setCapacity(roomDTO.getCapacity());
        room.setDescription(roomDTO.getDescription());
        room.setNotes(roomDTO.getNotes());
        room.setImage(roomDTO.getImage());

        // Save the new room to the database
        return roomRepository.save(room);
    }

    public List<TimeSlotDTO> getAvailableTimeSlotsForRoomType(String roomTypeId) {
        return null;
    }

    public void removeRoomById(Long roomId) {
        // First, delete all reservations associated with the room
        List<Reservation> reservationsToDelete = reservationRepository.findByRoomId(roomId);
        reservationRepository.deleteAll(reservationsToDelete);

        // Then, delete the room itself
        roomRepository.deleteById(roomId);
    }

}
