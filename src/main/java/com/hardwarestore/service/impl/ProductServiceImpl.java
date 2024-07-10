package com.hardwarestore.service.impl;

import com.hardwarestore.exception.ProductNotFoundException;
import com.hardwarestore.model.Product;
import com.hardwarestore.repository.ProductRepository;
import com.hardwarestore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) throws ProductNotFoundException {
        Product dbProduct = getProductById(product.getId())
                .orElseThrow(() ->
                        new ProductNotFoundException("Product with ID " + product.getId() + " not found."));
        if(product.getName() != null) {
           dbProduct.setName(product.getName());
        }
        if(product.getQuantity() != null) {
            dbProduct.setQuantity(product.getQuantity());
        }
        if(product.getPrice() != null) {
            dbProduct.setPrice(product.getPrice());
        }
        return productRepository.save(dbProduct);
    }

    @Override
    public void deleteProduct(Long id) throws ProductNotFoundException {
        Product dbProduct = getProductById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product with ID " + id + " not found."));
        productRepository.deleteById(id);
    }
}
