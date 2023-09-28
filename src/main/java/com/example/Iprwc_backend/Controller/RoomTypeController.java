package com.example.Iprwc_backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Iprwc_backend.DTO.RoomTypeDTO;
import com.example.Iprwc_backend.Model.RoomType;
import com.example.Iprwc_backend.Service.RoomTypeService;

@CrossOrigin(origins = {"http://localhost:4200", "https://loquacious-baklava-ac398e.netlify.app/"})


@RestController
@RequestMapping("/api/room-types")
public class RoomTypeController {

    @Autowired
    private RoomTypeService roomTypeService;

    // @PostMapping
    // public ResponseEntity<RoomType> addRoomType(@RequestBody RoomTypeDTO roomTypeDTO) {
    //     RoomType roomType = roomTypeService.addRoomType(roomTypeDTO);
    //     return ResponseEntity.status(HttpStatus.CREATED).body(roomType);
    // }

    // // get all room types
    // @GetMapping
    // public ResponseEntity<Iterable<RoomType>> getAllRoomTypes() {
    //     Iterable<RoomType> roomTypes = roomTypeService.getAllRoomTypes();
    //     return ResponseEntity.ok(roomTypes);
    // }
    
    // // get by id
    // @GetMapping("/{id}")
    // public ResponseEntity<RoomType> getRoomTypeById(@PathVariable Long id) {
    //     RoomType roomType = roomTypeService.getRoomTypeById(id);
    //     if (roomType != null) {
    //         return ResponseEntity.ok(roomType);
    //     } else {
    //         return ResponseEntity.notFound().build();
    //     }
    // }
}

