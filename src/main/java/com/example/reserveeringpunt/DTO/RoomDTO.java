package com.example.reserveeringpunt.DTO;

import lombok.Data;

@Data
public class RoomDTO {

    private String name;
    private int capacity;
    private String description;
    private String notes;
    private String image; // Updated to a String to store the base64-encoded image

}
