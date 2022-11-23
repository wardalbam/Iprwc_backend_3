package com.example.Iprwc_backend.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Iprwc_backend.Model.Address;

public interface AddressRepo extends JpaRepository<Address, Long>{
    
}
