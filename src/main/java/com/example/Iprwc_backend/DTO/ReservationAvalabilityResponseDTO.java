package com.example.Iprwc_backend.DTO;

import java.time.LocalTime;
import java.util.List;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ReservationAvalabilityResponseDTO {
    
    // openingTime and closingTime are strings because they are sent to the frontend as strings
    private LocalTime openingTime;
    private LocalTime closingTime;
    // availableTimeSlots is a list of strings because they are sent to the frontend as strings
    // list of reserved time slots
    private List<TimeSlotDTO> unavailableTimeSlots;


}
