package com.example.Iprwc_backend.DTO;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import com.example.Iprwc_backend.Model.Address;
import com.example.Iprwc_backend.Model.OrderLine;
import com.example.Iprwc_backend.Model.OrderStatus;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Data
public class OrderDTO {
    Address address;
    List<OrderLineDTO> ProductLineList;

    public OrderDTO(Address address, List<OrderLineDTO> productLineList) {
        this.address = address;
        ProductLineList = productLineList;
    }

}
