package com.example.Iprwc_backend.DTO;

import com.example.Iprwc_backend.Model.Product;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Data
public class OrderLineDTO {
    Product product;
    int amount;

    public OrderLineDTO( Product product, int amount) {
        this.product = product;
        this.amount = amount;
    }
}
