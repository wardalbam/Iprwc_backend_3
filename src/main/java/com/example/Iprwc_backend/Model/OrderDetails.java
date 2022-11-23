package com.example.Iprwc_backend.Model;

// import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.data.annotation.CreatedDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Table(name = "OrderDetails")
@Entity
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "order_date")
    @CreationTimestamp
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Date orderDate;

    public String username;
    public double totalPrice;
    public OrderStatus orderStatus;
    @OneToMany(fetch = FetchType.EAGER)
    public Collection<OrderLine> orederLines = new ArrayList<>();
    @OneToOne()
    public Address shippingAddress;
    
    
    public OrderDetails(Collection<OrderLine> orederLines, String username, Address shippingAddress, double totalPrice,
             OrderStatus orderStatus) {
        this.orederLines = orederLines;
        this.username = username;
        this.shippingAddress = shippingAddress;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
    }



}
