package com.example.Iprwc_backend.DAO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Iprwc_backend.Model.Reservation;
import com.example.Iprwc_backend.Model.Room;
import com.example.Iprwc_backend.Model.RoomType;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // List<Reservation> findByRoomRoomType(RoomType roomType);

    List<Reservation> findByUserId(Long userId);

    // get available room by roomId
    @Query("SELECT r FROM Room r WHERE r.id = :roomId AND NOT EXISTS (" +
            "SELECT 1 FROM Reservation res WHERE res.room = r AND NOT (res.startTime >= :endTime OR res.endTime <= :startTime))")
    Room findRoomByRoomIdAndNotReserved(@Param("roomId") Long roomId, @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    @Query("SELECT res FROM Reservation res WHERE res.room.id = :roomId AND res.startTime >= :startTime AND res.endTime <= :endTime")
    List<Reservation> findReservationsByRoomAndDate(
            @Param("roomId") Long roomId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    // findif reservation exists ByReservationDateAndRoomId
    @Query("SELECT COUNT(res) = 0 " +
            "FROM Reservation res " +
            "WHERE res.room.id = :roomId " +
            "AND NOT (res.startTime = :endTime OR res.endTime = :startTime)")
    boolean findByRoomIdAndNotReserved(@Param("roomId") Long roomId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    // findByStartTimeAfter
    List<Reservation> findByStartTimeAfter(LocalDateTime startTime);

    // findByRoomId
    List<Reservation> findByRoomId(Long roomId);

    List<Reservation> findByStartTimeBetween(LocalDateTime weekStartDate, LocalDateTime weekEndDate);

}
