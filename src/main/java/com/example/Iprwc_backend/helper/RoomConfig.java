package com.example.Iprwc_backend.helper;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class RoomConfig {

    // Define a map to hold the opening and closing times for each day of the week
    private Map<DayOfWeek, LocalTime> openingTimes;
    private Map<DayOfWeek, LocalTime> closingTimes;

    // Constructor to set the opening times
    public RoomConfig(
        // Add the opening times as arguments to the constructor
        @Value("${room.openingTimeSunday}") String openingTimeSunday,
        @Value("${room.openingTimeMonday}") String openingTimeMonday,
        @Value("${room.openingTimeTuesday}") String openingTimeTuesday,
        @Value("${room.openingTimeWednesday}") String openingTimeWednesday,
        @Value("${room.openingTimeThursday}") String openingTimeThursday,
        @Value("${room.openingTimeFriday}") String openingTimeFriday,
        @Value("${room.openingTimeSaturday}") String openingTimeSaturday,
        @Value("${room.closingTime}") String closingTime
    ) {
        this.openingTimes = new HashMap<>();
        this.closingTimes = new HashMap<>();
        // Set the opening times for each day of the week
        openingTimes.put(DayOfWeek.SUNDAY, LocalTime.parse(openingTimeSunday));
        openingTimes.put(DayOfWeek.MONDAY, LocalTime.parse(openingTimeMonday));
        openingTimes.put(DayOfWeek.TUESDAY, LocalTime.parse(openingTimeTuesday));
        openingTimes.put(DayOfWeek.WEDNESDAY, LocalTime.parse(openingTimeWednesday));
        openingTimes.put(DayOfWeek.THURSDAY, LocalTime.parse(openingTimeThursday));
        openingTimes.put(DayOfWeek.FRIDAY, LocalTime.parse(openingTimeFriday));
        openingTimes.put(DayOfWeek.SATURDAY, LocalTime.parse(openingTimeSaturday));

        // Set the closing time for all days (assuming it's the same for all days)
        LocalTime parsedClosingTime = LocalTime.parse(closingTime);
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            closingTimes.put(dayOfWeek, parsedClosingTime);
        }
    }

    // Get the closing time as a LocalTime object
    public LocalTime getClosingTimeAsLocalTime() {
        return closingTimes.get(DayOfWeek.SUNDAY);
    }

    // get the opening time by Local date as input
    public LocalTime getOpeningTimeForDay(LocalDate localDate) {
        return openingTimes.getOrDefault(localDate.getDayOfWeek(), LocalTime.MIDNIGHT);
    }

    // Get the opening time for a specific day of the week
    public LocalTime getOpeningTimeForDay(DayOfWeek dayOfWeek) {
        return openingTimes.getOrDefault(dayOfWeek, LocalTime.MIDNIGHT);
    }

    // Get the closing time for a specific day of the week
    public LocalTime getClosingTimeForDay(DayOfWeek dayOfWeek) {
        return closingTimes.getOrDefault(dayOfWeek, LocalTime.MAX);
    }
}
