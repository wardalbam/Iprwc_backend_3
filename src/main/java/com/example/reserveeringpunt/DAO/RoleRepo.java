package com.example.reserveeringpunt.DAO;

import com.example.reserveeringpunt.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
