package com.example.Iprwc_backend.Service;

import java.util.Collection;
import java.util.logging.Logger;

import javax.persistence.criteria.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Iprwc_backend.DAO.OrderRepo;
import com.example.Iprwc_backend.DAO.ProductRepo;
import com.example.Iprwc_backend.Model.Address;
import com.example.Iprwc_backend.Model.OrderDetails;
import com.example.Iprwc_backend.Model.OrderLine;

import lombok.extern.java.Log;

@Service
public class OrderService {
    
    @Autowired
    OrderRepo orderRepo;
    @Autowired
    ProductRepo productRepo;
    

    // public void addLineToOrder(Bestelling order, OrderLine orderLine){
    //     System.out.println( "adding " +orderLine.getProduct().getName() );
    //     order.getOrederLines().add(orderLine);
    // }

    public OrderService(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }
    public void addAddressToOrder(OrderDetails order, Address address){
        System.out.println( "adding " + address.zipcode );
        order.setShippingAddress(address);
    }
    // public boolean orderBelongsToUser(Long orderId, Long userId ){

    //     return !orderRepo.getOrderBYOrderIdAndUserId(userId, orderId).isEmpty();

    // }

    // get total price of Collection<OrderLine> orderLines
    public double getTotalPrice(Collection<OrderLine> orderLines){
        double totalPrice = 0;
        for (OrderLine orderLine : orderLines) {
            totalPrice += productRepo.getById(orderLine.getProductId()).getPrice() * orderLine.getAmount();
        }
        return totalPrice;
    }
    

}
