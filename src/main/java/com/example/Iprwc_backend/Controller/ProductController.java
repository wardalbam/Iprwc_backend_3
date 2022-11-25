package com.example.Iprwc_backend.Controller;

import java.util.List;

import org.hibernate.annotations.Any;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Iprwc_backend.Model.Product;
import com.example.Iprwc_backend.Service.ProductSercice;

@RestController
@RequestMapping("/api")
public class ProductController {

    private ProductSercice productSercice;



    public ProductController(ProductSercice productSercice) {
        this.productSercice = productSercice;
    }

    @GetMapping("prodcut/all")
    public ResponseEntity getAllProducts(){
        try{
            List<Product> list = productSercice.getAllProduct();
            return new ResponseEntity<>(list, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>("somthing went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/prodcut/add")
    public ResponseEntity saveProduct(@RequestParam Product product){
        try{
            productSercice.saveProduct(product);
            return new ResponseEntity<>("product added", HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>("somthing went wrong!", HttpStatus.BAD_REQUEST);
        }
        
    }


}
