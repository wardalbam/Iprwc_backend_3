package com.example.Iprwc_backend.DAO;

import com.example.Iprwc_backend.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
