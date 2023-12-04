package com.example.reserveeringpunt.Model;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Collection;

@Setter
@Getter
@Data
@ConstructorBinding
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    // private String name;
    private String username;
    private String password;
    @NonNull
    private String email;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public Collection<Role> roles = new ArrayList<>();

    @Lob
    @Column(nullable = true)
    private String image;

}
