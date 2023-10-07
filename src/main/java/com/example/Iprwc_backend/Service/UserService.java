package com.example.Iprwc_backend.Service;

import com.example.Iprwc_backend.Model.Role;
import com.example.Iprwc_backend.Model.User;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    User getUser(String userName);
    List<User> getUsers();
    List<User> findAllUsersWithoutAdmin();
    void removeUserById(Long id);
    // check if user has role admin
    boolean checkIfUserHasAdminRole(HttpServletRequest request);
    // findByEmail
    User findByEmail(String email);
    // updatePassword
    void updatePassword(User user, String newPassword);
}
