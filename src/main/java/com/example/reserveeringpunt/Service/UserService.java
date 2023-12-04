package com.example.reserveeringpunt.Service;

import com.example.reserveeringpunt.Model.Role;
import com.example.reserveeringpunt.Model.User;

import java.sql.Blob;
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

    // send email with credentials
    void sendCredentials(String email, String username, String password);

    // getUserById
    User getUserById(long id);

    // setUserImage
    void setUserImage(User user, String image);
}
