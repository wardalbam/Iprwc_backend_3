package com.example.Iprwc_backend.DAO;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Iprwc_backend.Model.Reservation;
import com.example.Iprwc_backend.Model.RoomType;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByRoomRoomType(RoomType roomType);

    // List<Reservation> findByRoomTypeAndEndTimeAfter(Long roomTypeId, LocalDateTime now);
    @Query("SELECT r FROM Reservation r WHERE r.room.roomType = :roomType AND r.endTime > :endTime")
    List<Reservation> findByRoomTypeAndEndTimeAfter(@Param("roomType") Long roomType, @Param("endTime") LocalDateTime endTime);
}

