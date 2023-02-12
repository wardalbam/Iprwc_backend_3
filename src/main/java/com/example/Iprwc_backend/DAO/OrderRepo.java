package com.example.Iprwc_backend.DAO;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.Iprwc_backend.Model.OrderDetails;


public interface OrderRepo extends JpaRepository<OrderDetails, Long>{

    
    @Query("SELECT o FROM OrderDetails o WHERE o.username = ?1")
    List<OrderDetails> findByUsername(String username);

    @Query("SELECT o FROM OrderDetails o WHERE o.username = ?1 AND o.id = ?2")
    Collection<OrderDetails> getOrderBYOrderIdAndUserId(String username, Long orderId);


}
