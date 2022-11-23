package com.example.Iprwc_backend.Model;

import java.net.URL;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class OrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    private Long productId;
    
    private int amount;

    private String product_name;
    
    private double product_price;

    private URL product_image;

    public OrderLine(Long orderId, Long productId, int amount, String product_name, double product_price,
    URL product_image) {
        this.orderId = orderId;
        this.productId = productId;
        this.amount = amount;
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_image = product_image;
    }
    

}
