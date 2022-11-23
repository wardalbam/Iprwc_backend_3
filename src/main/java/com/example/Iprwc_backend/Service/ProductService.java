package com.example.Iprwc_backend.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Iprwc_backend.DAO.ProductRepo;
import com.example.Iprwc_backend.Model.Product;
import com.example.Iprwc_backend.Model.ProductStatus;

@Service
public class ProductService {
    
    private ProductRepo productRepo;

    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public List<Product> getAllProduct(){
        return productRepo.findAll();
    }
    // get all porducts with productStatus = active 
    public List<Product> getProductsByStatus(ProductStatus status){
        // if status = ALL return all products 
        if(status.equals("ALL")){
            return productRepo.findAll();
        }
        return productRepo.findByProductStatus(status);
    }

    
    public Product saveProduct(Product product){
        return productRepo.save(product);
    }

    public Product get(Long id){
        return productRepo.findById(id).get();
    }

    public void deleteProduct(Product product){
        productRepo.delete(product);
    }

}