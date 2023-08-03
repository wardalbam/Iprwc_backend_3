package com.example.Iprwc_backend.DTO;


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
    
    
    public UserDetailsDTO(Long id, String fullname, String email) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
    }

}
