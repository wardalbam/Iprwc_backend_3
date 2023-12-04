package com.example.reserveeringpunt.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import com.example.reserveeringpunt.DAO.ReservationRepository;
import com.example.reserveeringpunt.DTO.TimeSlotDTO;
import com.example.reserveeringpunt.Model.Reservation;
import com.example.reserveeringpunt.Model.User;
import com.example.reserveeringpunt.helper.RoomConfig;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RoomConfig roomConfiguration;

    private static final int TIME_SLOT_DURATION = 60;

    // getAllReservationsAdmin
    public List<Reservation> getAllReservationsAdmin() {
        // start from today and get all reservations in the future
        LocalDate today = LocalDate.now();
        return reservationRepository.findByStartTimeAfter(today.atStartOfDay());

    }

    // removeReservations
    public void removeReservations(List<Reservation> reservations) {
        reservationRepository.deleteAll(reservations);
    }

    public List<Reservation> getAllReservations(User user) {
        return reservationRepository.findByUserId(user.getId());
    }

    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    // return all timeStols in a day from opening to closing by Date in
    // List<TimeslotDTO>
    public List<TimeSlotDTO> getAllTimeSlotsByDate(LocalDate date) {
        LocalTime openingTime = roomConfiguration.getOpeningTimeForDay(date);
        LocalTime closingTime = roomConfiguration.getClosingTimeAsLocalTime();

        List<TimeSlotDTO> timeSlots = new ArrayList<>();

        for (LocalTime startTime = openingTime; startTime
                .isBefore(closingTime); startTime = startTime.plusMinutes(TIME_SLOT_DURATION)) {
            TimeSlotDTO timeSlotDTO = new TimeSlotDTO();
            timeSlotDTO.setStartTime(date.atTime(startTime));
            timeSlotDTO.setEndTime(date.atTime(startTime.plusMinutes(TIME_SLOT_DURATION)));
            timeSlots.add(timeSlotDTO);
        }

        // Debugging logs
        System.out.println("Generating time slots for date: " + date);
        return timeSlots;
    }

    public List<TimeSlotDTO> getUnavailableTimeSlotsByDate(LocalDate date, Long roomId) {
        List<TimeSlotDTO> timeSlots = getAllTimeSlotsByDate(date);
        List<TimeSlotDTO> unavailableTimeSlots = new ArrayList<>();

        LocalDateTime startOfReservationDate = roomConfiguration.getOpeningTimeForDay(date).atDate(date);
        LocalDateTime endOfReservationDate = roomConfiguration.getClosingTimeAsLocalTime().atDate(date);

        List<Reservation> reservations = reservationRepository.findReservationsByRoomAndDate(
                roomId,
                startOfReservationDate,
                endOfReservationDate);

        for (TimeSlotDTO timeSlotDTO : timeSlots) {
            LocalDateTime startTime = timeSlotDTO.getStartTime();
            LocalDateTime endTime = timeSlotDTO.getEndTime();
            // Check if there are any reservations that overlap with the current time slot
            // and same room id
            boolean isUnavailable = reservations.stream()
                    .anyMatch(reservation -> (reservation.getStartTime().isBefore(endTime)) &&
                            (reservation.getEndTime().isAfter(startTime) ||
                                    reservation.getEndTime().isEqual(endTime) || // Consider reservations that end at
                                                                                 // closing time
                                    reservation.getEndTime().isEqual(startOfReservationDate) // Consider reservations
                                                                                             // that end at opening time
                            ));

            if (isUnavailable || startTime.isBefore(LocalDateTime.now())) {
                unavailableTimeSlots.add(timeSlotDTO);

            }

        }
        return unavailableTimeSlots;
    }

    public List<Reservation> createBulkReservations(List<Reservation> reservations) {
        // You can perform any additional business logic or validations here
        // Before saving the reservations in bulk

        // Save the reservations in bulk
        List<Reservation> savedReservations = reservationRepository.saveAll(reservations);

        // You can perform any post-save operations or return additional data if needed

        return savedReservations;
    }

    // delete by id
    public void deleteReservationById(Long id) {
        reservationRepository.deleteById(id);
    }

    public List<Reservation> getReservationsByWeek(LocalDate weekStartDate, LocalDate weekEndDate) {
        LocalDateTime weekStartDateTime = weekStartDate.atStartOfDay();
        LocalDateTime weekEndDateTime = weekEndDate.atTime(23, 59, 59); // Set the time to the end of the day

        return reservationRepository.findByStartTimeBetween(weekStartDateTime, weekEndDateTime);
    }

}
