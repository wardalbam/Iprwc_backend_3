package com.example.Iprwc_backend.DTO;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ReservationDTO {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
