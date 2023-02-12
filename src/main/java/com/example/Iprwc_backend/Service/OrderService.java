package com.example.Iprwc_backend.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.Iprwc_backend.DAO.AddressRepo;
import com.example.Iprwc_backend.DAO.OrderLineRepo;
import com.example.Iprwc_backend.DAO.OrderRepo;
import com.example.Iprwc_backend.DAO.ProductRepo;
import com.example.Iprwc_backend.DTO.OrderDTO;
import com.example.Iprwc_backend.DTO.OrderLineDTO;
import com.example.Iprwc_backend.Model.Address;
import com.example.Iprwc_backend.Model.OrderDetails;
import com.example.Iprwc_backend.Model.OrderLine;
import com.example.Iprwc_backend.Model.OrderStatus;
import com.example.Iprwc_backend.Model.Product;
import com.example.Iprwc_backend.Model.User;

import lombok.extern.java.Log;

@Service
public class OrderService {
    
    @Autowired
    OrderRepo orderRepo;
    @Autowired
    ProductRepo productRepo;

    @Autowired
    AddressRepo addressRepo;

    @Autowired
    UserService userService;

    @Autowired
    OrderLineRepo orderLineRepo;

    @Autowired
    AddressService addressService;

    

    public OrderService(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }
    public void addAddressToOrder(OrderDetails order, Address address){
        System.out.println( "adding " + address.zipcode );
        order.setShippingAddress(address);
    }

    public double getTotalPrice(Collection<OrderLine> orderLines){
        double totalPrice = 0;
        for (OrderLine orderLine : orderLines) {
            totalPrice += productRepo.getById(orderLine.getProductId()).getPrice() * orderLine.getAmount();
        }
        return totalPrice;
    }

    public OrderDetails createNewOrder(OrderDTO orderDTO, HttpServletRequest request ){
        
         // create new address
         Address address = addressService.SaveAddress(orderDTO.getAddress());
         
         // get the user from the request
         Principal principal = request.getUserPrincipal();
         User user = userService.getUser(principal.getName());

         // create new order
         OrderDetails order = new OrderDetails(new ArrayList<>(), user.getUsername(), address, 0 , OrderStatus.RECIEVED);

         // Convert orderlinesDTO to list of orderlines
         List<OrderLine> productLines = new ArrayList<>();
         for (OrderLineDTO orderLineDTO : orderDTO.getProductLineList()) {
             Product product = orderLineDTO.getProduct();
             Integer amount = orderLineDTO.getAmount();
             OrderLine orderLine = new OrderLine( order.getId(), product.getId(), amount, product.getName(), product.getPrice(),product.getImagePath());
             orderLineRepo.save(orderLine);
             productLines.add(orderLine);
         }

        // Add orderlines List to order
        order.setOrederLines(productLines);
        return order;
    }

    
}
