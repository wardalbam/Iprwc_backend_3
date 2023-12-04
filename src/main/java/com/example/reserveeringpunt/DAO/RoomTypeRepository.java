package com.example.reserveeringpunt.DAO;

// roomTypeRepo is the name of the repository

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reserveeringpunt.Model.RoomType;

public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {
}