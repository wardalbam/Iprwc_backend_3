package com.example.Iprwc_backend.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Iprwc_backend.DAO.ReservationRepository;
import com.example.Iprwc_backend.DTO.TimeSlotDTO;
import com.example.Iprwc_backend.Model.Reservation;
import com.example.Iprwc_backend.Model.Room;
import com.example.Iprwc_backend.Model.RoomType;
import com.example.Iprwc_backend.helper.RoomConfig;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    
     @Autowired
    private RoomConfig roomConfiguration;



    // ... other methods ...

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    public List<Reservation> getReservationsByRoomType(RoomType roomType) {
        return reservationRepository.findByRoomRoomType(roomType);
    }

    public List<TimeSlotDTO> getAvailableTimeSlotsForRoomType(Long roomTypeId, LocalDate reservationDate) {
        LocalDateTime startOfReservationDate = reservationDate.atStartOfDay();
        LocalDateTime endOfReservationDate = startOfReservationDate.plusDays(1).minusMinutes(1);
    
        // Get the opening and closing times from the room configuration
        LocalTime openingTime = roomConfiguration.getOpeningTimeAsLocalTime();
        LocalTime closingTime = roomConfiguration.getClosingTimeAsLocalTime();
    
        List<Reservation> reservations = reservationRepository.findByRoomTypeAndEndTimeAfter(roomTypeId, LocalDateTime.now());
        List<TimeSlotDTO> availableTimeSlots = new ArrayList<>();
    
        // Initialize currentSlotStart
        LocalDateTime currentSlotStart = (LocalDateTime.now().isBefore(startOfReservationDate))
            ? startOfReservationDate.withHour(openingTime.getHour()).withMinute(openingTime.getMinute())
            : ((LocalDateTime.now().getMinute() < 30)
                ? LocalDateTime.now().withMinute(30).withSecond(0).withNano(0)
                : LocalDateTime.now().plusHours(1).withMinute(0).withSecond(0).withNano(0));
    
        // Use a while loop with the helper method to find the next valid time slot
        final LocalDateTime[] nextSlotStartWrapper = { currentSlotStart };
        while (nextSlotStartWrapper[0].isBefore(endOfReservationDate) && nextSlotStartWrapper[0].toLocalTime().isBefore(closingTime)) {
            LocalDateTime currentSlotEnd = nextSlotStartWrapper[0].plusMinutes(30);
    
            if (currentSlotEnd.toLocalTime().isAfter(openingTime) &&
                reservations.stream().noneMatch(r -> r.getStartTime().isBefore(currentSlotEnd) && r.getEndTime().isAfter(nextSlotStartWrapper[0]))) {
                TimeSlotDTO timeSlotDTO = new TimeSlotDTO(); 
                timeSlotDTO.setStartTime(nextSlotStartWrapper[0]);
                timeSlotDTO.setEndTime(currentSlotEnd);
                availableTimeSlots.add(timeSlotDTO);
            }
    
            // Move to the next time slot
            nextSlotStartWrapper[0] = getNextValidTimeSlot(nextSlotStartWrapper[0].plusMinutes(30), openingTime, closingTime);
        }
    
        return availableTimeSlots;
    }
    
    // Helper method to find the next valid time slot
    private LocalDateTime getNextValidTimeSlot(LocalDateTime currentSlot, LocalTime openingTime, LocalTime closingTime) {
        if (currentSlot.toLocalTime().isAfter(closingTime)) {
            // If the current slot's time is after the closing time, reset to the next day's opening time
            return currentSlot.plusDays(1).withHour(openingTime.getHour()).withMinute(openingTime.getMinute());
        } else if (currentSlot.toLocalTime().isBefore(openingTime)) {
            // If the current slot's time is before the opening time, set to the opening time
            return currentSlot.withHour(openingTime.getHour()).withMinute(openingTime.getMinute());
        } else {
            // Round to the next valid half-hour time slot
            int minute = currentSlot.getMinute();
            int minuteOffset = minute % 30 == 0 ? 30 : 60 - minute;
            return currentSlot.plusMinutes(minuteOffset);
        }
    }





}
