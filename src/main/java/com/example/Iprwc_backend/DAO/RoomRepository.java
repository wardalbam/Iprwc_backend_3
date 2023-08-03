package com.example.Iprwc_backend.DAO;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.Iprwc_backend.Model.Room;
import com.example.Iprwc_backend.Model.RoomType;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("SELECT r FROM Room r WHERE r.id NOT IN (SELECT res.room.id FROM Reservation res WHERE res.startTime < :endTime AND res.endTime > :startTime)")
    List<Room> findAvailableRoomsBetweenTimes(LocalDateTime startTime, LocalDateTime endTime);

    List<Room> findByRoomType(RoomType roomType);
}


