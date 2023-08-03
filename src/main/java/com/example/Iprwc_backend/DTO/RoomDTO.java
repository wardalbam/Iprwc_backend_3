package com.example.Iprwc_backend.DTO;

import lombok.Data;


@Data
public class RoomDTO {
    private String name;
    private int capacity;
    private String description;
    private Long roomTypeId; // Use the ID of the roomType instead of the object itself
}
