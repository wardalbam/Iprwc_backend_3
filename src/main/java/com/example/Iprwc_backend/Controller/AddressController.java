package com.example.Iprwc_backend.Controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.Iprwc_backend.DAO.AddressRepo;
import com.example.Iprwc_backend.Model.Address;
import com.example.Iprwc_backend.Model.Role;
import com.example.Iprwc_backend.Model.User;
import com.example.Iprwc_backend.Service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import org.hibernate.annotations.Any;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/address")
public class AddressController {

    AddressRepo addressRepo;



    public AddressController(AddressRepo addressRepo) {
        this.addressRepo = addressRepo;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Address> getProductById(@RequestParam String id){
        Long idLong = Long.parseLong(id);
        try{
            Address address = addressRepo.getById(idLong);
            return new ResponseEntity<>(address, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}