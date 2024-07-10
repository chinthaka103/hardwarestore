package com.hardwarestore.controller;

import com.hardwarestore.exception.ProductNotFoundException;
import com.hardwarestore.model.Product;
import com.hardwarestore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping()
    public List<Product> getProducts(){
        return productService.getProducts();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public Product createProduct(@RequestBody Product product){
        return productService.createProduct(product);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping()
    public Product updateProduct(@RequestBody Product product) throws ProductNotFoundException {
        return productService.updateProduct(product);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) throws ProductNotFoundException {
        productService.deleteProduct(id);
    }
}