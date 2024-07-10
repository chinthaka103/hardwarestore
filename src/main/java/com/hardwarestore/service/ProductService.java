package com.hardwarestore.service;

import com.hardwarestore.exception.ProductNotFoundException;
import com.hardwarestore.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> getProducts();
    Optional<Product> getProductById(Long id);
    Product createProduct(Product product);
    Product updateProduct(Product product) throws ProductNotFoundException;
    void deleteProduct(Long id) throws ProductNotFoundException;
}
