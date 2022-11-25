package com.example.Iprwc_backend.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Iprwc_backend.Model.Product;

public interface ProductRepo extends JpaRepository<Product, Long>{
    
}
