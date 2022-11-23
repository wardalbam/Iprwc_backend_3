package com.example.Iprwc_backend.DAO;

import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.Iprwc_backend.Model.OrderDetails;
import com.example.Iprwc_backend.Model.OrderLine;

public interface OrderLineRepo  extends JpaRepository<OrderLine, Long>{
    
    // @Query("SELECT * from OrderLine where orderId = 1?")
    List<OrderLine> findLinesByOrderId(Long orderId);

}
