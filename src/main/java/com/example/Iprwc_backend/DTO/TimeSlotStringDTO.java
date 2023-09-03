package com.example.Iprwc_backend.DTO;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class TimeSlotStringDTO {
    private String startTime;
    private String endTime;
}
