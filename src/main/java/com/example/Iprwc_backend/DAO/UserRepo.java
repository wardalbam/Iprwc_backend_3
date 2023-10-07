package com.example.Iprwc_backend.DAO;

import com.example.Iprwc_backend.Model.Role;
import com.example.Iprwc_backend.Model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
    //  get all users that have no name ROLE_ADMIN in roles names
    @Query("SELECT u FROM User u WHERE u.id NOT IN (SELECT u.id FROM User u JOIN u.roles r WHERE r.name = 'ROLE_ADMIN')")
    List<User> findAllUsersWithoutAdmin();

    //  get all users that have no name ROLE_ADMIN or ROLE_MANAGER in roles names
    @Query("SELECT u FROM User u WHERE u.id NOT IN (SELECT u.id FROM User u JOIN u.roles r WHERE r.name = 'ROLE_ADMIN' OR r.name = 'ROLE_MANAGER')")
    List<User> findAllUsersWithAdminOrManager();

    // findByEmail
    User findByEmail(String email);

    // updatePassword
    @Query("UPDATE User u SET u.password = ?1 WHERE u.id = ?2")
    void updatePassword(String password, Long userId);
}
