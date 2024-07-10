package com.hardwarestore.service;

import com.hardwarestore.exception.OrderException;
import com.hardwarestore.exception.ProductNotFoundException;
import com.hardwarestore.model.Order;
import com.hardwarestore.model.Product;

import java.util.List;

public interface OrderService {
    Order createOrder(List<Product> products) throws OrderException, ProductNotFoundException;
}
