package com.example.Iprwc_backend.DAO;

// roomTypeRepo is the name of the repository

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Iprwc_backend.Model.RoomType;

public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {
}