package com.example.Iprwc_backend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Iprwc_backend.DAO.RoomTypeRepository;
import com.example.Iprwc_backend.DTO.RoomTypeDTO;
import com.example.Iprwc_backend.Model.RoomType;

@Service
public class RoomTypeService {

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    public RoomType addRoomType(RoomTypeDTO roomTypeDTO) {
        RoomType roomType = new RoomType();
        roomType.setType(roomTypeDTO.getType());
        return roomTypeRepository.save(roomType);
    }

    public Iterable<RoomType> getAllRoomTypes() {
        return roomTypeRepository.findAll();

    }

    public RoomType getRoomTypeById(Long id) {
        return roomTypeRepository.findById(id).orElse(null);
    }
}

