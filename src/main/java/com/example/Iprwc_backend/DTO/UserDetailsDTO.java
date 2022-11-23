package com.example.Iprwc_backend.DTO;

import com.example.Iprwc_backend.Model.Address;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Data
public class UserDetailsDTO {
    
    public Long id;
    public String fullname;
    public String email;
    public String land;
    public String zipcode;
    public String addressLine;
    public String city;
    
    
    public UserDetailsDTO(Long id, String fullname, String email, String land, String zipcode, String addressLine,
            String city) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.land = land;
        this.zipcode = zipcode;
        this.addressLine = addressLine;
        this.city = city;
    }

}
