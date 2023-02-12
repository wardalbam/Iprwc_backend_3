package com.example.Iprwc_backend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Iprwc_backend.DAO.AddressRepo;
import com.example.Iprwc_backend.Model.Address;

@Service
public class AddressService {

    @Autowired
    AddressRepo addressRepo;

    public Address SaveAddress(Address address){
        address.setLand("Nederland");
        return addressRepo.save(address);
    }
    
}
