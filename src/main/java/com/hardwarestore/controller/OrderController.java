package com.hardwarestore.controller;

import com.hardwarestore.exception.OrderException;
import com.hardwarestore.exception.ProductNotFoundException;
import com.hardwarestore.model.Order;
import com.hardwarestore.model.Product;
import com.hardwarestore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping()
    public Order createOrder(@RequestBody List<Product> products) throws OrderException, ProductNotFoundException {
        return orderService.createOrder(products);
    }
}
