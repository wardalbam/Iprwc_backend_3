package com.example.Iprwc_backend.Model;


import java.net.URL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.websocket.Decoder.Text;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String Name;
    private Double Price;
    private URL ImagePath;
    // description string can be max 2000 char
    @Column(length = 4550)
    private String description;
    private ProductStatus productStatus;


    public Product(Long id, String name, Double price, URL imagePath, String description, ProductStatus productStatus) {
        this.id = id;
        Name = name;
        Price = price;
        ImagePath = imagePath;
        this.description = description;
        this.productStatus = productStatus;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public URL getImagePath() {
        return ImagePath;
    }

    public void setImagePath(URL imagePath) {
        ImagePath = imagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
