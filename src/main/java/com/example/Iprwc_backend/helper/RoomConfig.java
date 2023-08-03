package com.example.Iprwc_backend.helper;

import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter


@Component
public class RoomConfig {

    @Value("${room.openingTime}")
    private String openingTime;

    @Value("${room.closingTime}")
    private String closingTime;

    // Convert the opening and closing time strings to LocalTime objects
    public LocalTime getOpeningTimeAsLocalTime() {
        return LocalTime.parse(openingTime);
    }

    public LocalTime getClosingTimeAsLocalTime() {
        return LocalTime.parse(closingTime);
    }
}

