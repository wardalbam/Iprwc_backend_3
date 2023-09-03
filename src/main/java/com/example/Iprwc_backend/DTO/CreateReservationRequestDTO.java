package com.example.Iprwc_backend.DTO;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

// Request DTO for creating a reservation
@Data
@Getter
@Setter
public class CreateReservationRequestDTO {
    private LocalDateTime startTime;
    private Long roomId;

    // Getters and setters (you can use Lombok annotations if desired)
}