package com.example.Iprwc_backend.DTO;


import java.time.LocalDate;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class CreateBulkReservationsRequestDTO {
    

    public  Long roomId;
    public TimeSlotStringDTO[] timeSlots;
    public LocalDate reservationDate;
    public String note;

}
