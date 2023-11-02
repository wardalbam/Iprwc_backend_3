package com.example.Iprwc_backend.Controller;

import com.example.Iprwc_backend.DAO.RoleRepo;
import com.example.Iprwc_backend.DAO.UserRepo;
import com.example.Iprwc_backend.DTO.UserDetailsDTO;
import com.example.Iprwc_backend.Model.Role;
import com.example.Iprwc_backend.Model.User;
import com.example.Iprwc_backend.Service.ReservationService;
import com.example.Iprwc_backend.Service.UserServiceImpl;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.security.Principal;
import java.sql.Blob;
import java.util.*;

@CrossOrigin(origins = { "http://localhost:4200", "https://loquacious-baklava-ac398e.netlify.app/" })
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserServiceImpl userService;
    private final UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;
    @Autowired
    ReservationService reservationService;

    public UserController(UserServiceImpl userService, UserRepo userRepo) {
        this.userService = userService;
        this.userRepo = userRepo;
    }

    // delete user by id
    @GetMapping("user/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        try {
            // if user dont have role admin
            if (!userRepo.getById(id).getRoles().contains(roleRepo.findByName("ROLE_ADMIN"))) {
                userService.removeUserById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Admin cannot be removed!", HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("user/details")
    public ResponseEntity<UserDetailsDTO> getEmail(HttpServletRequest request) {
        try {
            Principal principal = request.getUserPrincipal();
            User user = userService.getUser(principal.getName());
            UserDetailsDTO userDetails = new UserDetailsDTO();
            userDetails = new UserDetailsDTO(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getImage());

            return new ResponseEntity<UserDetailsDTO>(userDetails, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("user/all")
    public ResponseEntity<List<User>> getAllUsersAsAdmin() {
        try {
            List<User> userList = userService.findAllUsersWithoutAdmin();
            // return users without password
            for (User user : userList) {
                // String decodedPasswordString = ??;
                // user.setPassword(decodedPasswordString);
            }
            return new ResponseEntity<>(userList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("user/all/roleuser")
    public ResponseEntity<List<User>> getAllUsersAsManager() {
        try {
            List<User> userList = userService.findAllUsersWithAdminOrManager();
            return new ResponseEntity<>(userList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/user/save")
    public ResponseEntity<User> saveUser(@RequestBody RegisterForm NewUser) {

        // validate NewUser data
        if (NewUser.getUsername().isEmpty() || NewUser.getPassword().isEmpty() || NewUser.getEmail().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // if user not exist
        if (userService.getUser(NewUser.getUsername()) == null) {
            try {
                // add role user
                User user = new User();
                user.setUsername(NewUser.getUsername());
                user.setPassword(NewUser.getPassword());
                // email
                user.setEmail(
                        NewUser.getEmail());
                userService.saveUser(user);
                userService.addRoleToUser(user.getUsername(), "ROLE_USER");
                URI uri = URI.create(
                        ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
                // send email with credentials
                userService.sendCredentials(user.getEmail(), user.getUsername(),
                        NewUser.getPassword());

                return ResponseEntity.created(uri).body(user);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    @PostMapping("/role/addtouser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) {
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    // add new user as admin if the request user has role name ROLE_ADMIN
    @PostMapping("/user/save/manager")
    public ResponseEntity<User> saveUserAsAdmin(@RequestBody RegisterForm ManagerRegisterForm,
            HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        User user = userService.getUser(principal.getName());
        // if user has role admin
        if (user.getRoles().contains(roleRepo.findByName("ROLE_ADMIN"))) {
            // if user not exist
            if (userService.getUser(ManagerRegisterForm.getUsername()) == null) {
                try {
                    // add role user
                    User newUser = new User();
                    newUser.setUsername(ManagerRegisterForm.getUsername());
                    newUser.setPassword(ManagerRegisterForm.getPassword());
                    // newUser.setName(ManagerRegisterForm.getFullname());
                    newUser.setEmail(ManagerRegisterForm.getEmail());
                    userService.saveUser(newUser);
                    userService.addRoleToUser(newUser.getUsername(), "ROLE_MANAGER");
                    URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path("/api/user/save/manager").toUriString());
                    return ResponseEntity.created(uri).body(newUser);
                } catch (Exception e) {
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    // get current user
    @GetMapping("/users/current")
    public ResponseEntity<UserDetailsDTO> getCurrentUser(HttpServletRequest request) {

        Principal principal = request.getUserPrincipal();
        String username = principal.getName();
        User user = userService.getUser(username);
        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
        userDetailsDTO.setEmail(user.getEmail());
        userDetailsDTO.setId(user.getId());
        userDetailsDTO.setFullname(username);
        userDetailsDTO.setImage(user.getImage());
        return ResponseEntity.ok(userDetailsDTO);

    }

    // users/upload-image
    @PostMapping("/{userId}/upload-image")
    public ResponseEntity<?> uploadImage(
            @PathVariable("userId") Long userId,
            @RequestBody UserImageForm imageUser,
            HttpServletRequest request) {

        // validate the image
        if (imageUser.getImage().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = userService.getUserById(userId);
        userService.setUserImage(user, imageUser.getImage());
        return ResponseEntity.ok().build();
    }

}

@Data
class RoleToUserForm {
    private String username;
    private String roleName;
}

@Data
class RegisterForm {
    private String username;
    private String password;
    private String email;
}

@Data
class UserImageForm {
    private Long userId;
    private String image;
}