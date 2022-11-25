package com.example.Iprwc_backend.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Iprwc_backend.DAO.ProductRepo;
import com.example.Iprwc_backend.Model.Product;

@Service
public class ProductSercice {
    
    private ProductRepo productRepo;

    public ProductSercice(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public List<Product> getAllProduct(){
        return productRepo.findAll();
    }
    
    public Product saveProduct(Product product){
        return productRepo.save(product);
    }

}

