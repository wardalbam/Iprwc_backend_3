package com.example.reserveeringpunt.DTO;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Lob;

import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDetailsDTO {

    public Long id;
    public String fullname;
    public String email;
    private String image;

}
