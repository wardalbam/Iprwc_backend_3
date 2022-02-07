package com.example.Iprwc_backend.DAO;

import com.example.Iprwc_backend.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
