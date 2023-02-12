package com.example.Iprwc_backend.Service;

import com.example.Iprwc_backend.DAO.RoleRepo;
import com.example.Iprwc_backend.DAO.UserRepo;
import com.example.Iprwc_backend.Model.Role;
import com.example.Iprwc_backend.Model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
@Slf4j

public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if( user == null ){
            log.error("user not found");
            throw new UsernameNotFoundException("user not found in database");
        } else{
            log.info("user found in database");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public UserRepo getUserRepo() {
        return userRepo;
    }

    public RoleRepo getRoleRepo() {
        return roleRepo;
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.info("saving user to database");
        return this.userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("adding role to database");
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info( "adding " +roleName+ " to " + username);
        User user = userRepo.findByUsername(username);
        Role role = roleRepo.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public User getUser(String userName) {
        log.info("fetching user"+userName);
        return userRepo.findByUsername(userName);
    }

    @Override
    public List<User> getUsers() {
        return userRepo.findAll();
    }
    // Get all users with that have ROLE_USER as role name 
    @Override
    public List<User> findAllUsersWithoutAdmin() {
        return userRepo.findAllUsersWithoutAdmin();
    }
    // get all users with that have ROLE_ADMIN or ROLE_MANAGER as role name
    public List<User> findAllUsersWithAdminOrManager() {
        return userRepo.findAllUsersWithAdminOrManager();
    }

    // remove user by id
    @Override
    public void removeUserById(Long id) {
        userRepo.deleteById(id);
    }

}
