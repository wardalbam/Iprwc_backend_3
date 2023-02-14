package com.example.Iprwc_backend.Controller;

import java.net.URI;
import java.net.URL;
import java.util.List;

import org.hibernate.annotations.Any;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.Iprwc_backend.Model.Product;
import com.example.Iprwc_backend.Model.ProductStatus;
import com.example.Iprwc_backend.Service.ProductService;

import lombok.Data;
@CrossOrigin(origins = "https://gifted-nobel-9ce0d0.netlify.app")
@RestController
@RequestMapping("/api")
public class ProductController {

    private ProductService productSercice;



    public ProductController(ProductService productSercice) {
        this.productSercice = productSercice;
    }

    @DeleteMapping("product/delete/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable String id){
        Long idLong = Long.parseLong(id);
        try{
            Product product = productSercice.get(idLong);
            productSercice.deleteProduct(product);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    

    @GetMapping("product/all")
    public ResponseEntity<List<Product>> getActiveProducts(){
        try{
            List<Product> ProductList = productSercice.getProductsByStatus(ProductStatus.ACTIVE);
            return new ResponseEntity<>(ProductList, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("admin/product/all")
    public ResponseEntity<List<Product>> getAllProducts(){
        try{
            List<Product> ProductList = productSercice.getAllProduct();
            return new ResponseEntity<>(ProductList, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/product/add")
    public ResponseEntity<Product> saveProduct(@RequestBody NewProductForm product){
        // from NewProductForm to product
        Product product1 =new Product();
        product1.setName(product.getName());
        product1.setPrice(product.getPrice());
        product1.setDescription(product.getDescription());
        try{
            product1.setProductStatus(ProductStatus.valueOf(product.getStatus().toUpperCase()));
            product1.setImagePath( new URL(product.getImagepath()) );

            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/product/add").toUriString());
            return ResponseEntity.created(uri).body(productSercice.saveProduct(product1));
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/product/edit")
    public ResponseEntity<Product> editProduct(@RequestBody AlterProductForm product){
        // from NewProductForm to product
        Product product1 = productSercice.get(product.getId());
        product1.setName(product.getNewProductForm().getName() );
        product1.setPrice(product.getNewProductForm().getPrice() );
        product1.setDescription(product.getNewProductForm().getDescription());
        try{
            product1.setProductStatus(ProductStatus.valueOf(product.getNewProductForm().getStatus().toUpperCase()));
            product1.setImagePath( new URL(product.getNewProductForm().getImagepath()) );
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/product/add").toUriString());
            return ResponseEntity.created(uri).body(productSercice.saveProduct(product1));
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@RequestParam String id){
        Long idLong = Long.parseLong(id);
        try{
            Product product = productSercice.get(idLong);
            return new ResponseEntity<>(product, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
@Data
class NewProductForm{
    private String name;
    private double price;
    private String imagepath;
    private String description;
    private String status;
}

@Data
class AlterProductForm{
    private NewProductForm newProductForm;
    private Long id;
}

