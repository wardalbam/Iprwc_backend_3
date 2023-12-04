package com.example.reserveeringpunt.DAO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.reserveeringpunt.Model.Room;
import com.example.reserveeringpunt.Model.RoomType;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("SELECT r FROM Room r WHERE r.id NOT IN (SELECT res.room.id FROM Reservation res WHERE res.startTime < :endTime AND res.endTime > :startTime)")
    List<Room> findAvailableRoomsBetweenTimes(LocalDateTime startTime, LocalDateTime endTime);

    // List<Room> findByRoomType(RoomType roomType);

    // @Query("SELECT res.startTime, res.endTime FROM Reservation res " +
    // "WHERE res.room.roomType.id = :roomTypeId " +
    // "AND res.startTime >= :startTime " +
    // "AND res.endTime <= :endTime")
    // List<Object[]> findReservedTimeSlotsByRoomTypeAndDate(@Param("roomTypeId")
    // Long roomTypeId,
    // @Param("startTime") LocalDateTime startTime,
    // @Param("endTime") LocalDateTime endTime);

    // @Query("SELECT COUNT(res) = 0 " +
    // "FROM Reservation res " +
    // "WHERE res.room.roomType.id = :roomTypeId " +
    // "AND NOT (res.startTime >= :endTime OR res.endTime <= :startTime)")
    // boolean findByRoomTypeAndNotReserved(@Param("roomTypeId") Long roomTypeId,
    // @Param("startTime") LocalDateTime startTime,
    // @Param("endTime") LocalDateTime endTime);

    // // findByRoomType_Id
    // List<Room> findByRoomType_Id(Long roomTypeId);

    // see if room is available by its id and list of timeslots
    @Query("SELECT COUNT(res) = 0 " +
            "FROM Reservation res " +
            "WHERE res.room.id = :roomId " +
            "AND NOT (res.startTime >= :endTime OR res.endTime <= :startTime)")
    boolean findByRoomIdAndNotReserved(@Param("roomId") Long roomId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

}
