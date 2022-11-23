package com.example.Iprwc_backend.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Iprwc_backend.Model.Product;
import com.example.Iprwc_backend.Model.ProductStatus;


public interface ProductRepo extends JpaRepository<Product, Long>{
    // findByProductStatus
    public List<Product> findByProductStatus(ProductStatus productStatus);

}
