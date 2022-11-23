package com.example.Iprwc_backend.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.aspectj.weaver.ast.Var;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Address {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public String land;
    public String zipcode;
    public String addressLine;
    public String city;
    public Address(Long id, String land, String zipcode, String addressLine, String city) {
        this.id = id;
        this.land = land;
        this.zipcode = zipcode;
        this.addressLine = addressLine;
        this.city = city;
    }

}
