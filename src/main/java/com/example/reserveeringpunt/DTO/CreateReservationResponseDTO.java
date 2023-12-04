package com.example.reserveeringpunt.DTO;

import java.time.LocalDateTime;

import com.example.reserveeringpunt.Model.RoomType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
// Response DTO for the created reservation
public class CreateReservationResponseDTO {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String roomName;

}
