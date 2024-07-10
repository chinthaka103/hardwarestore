package com.hardwarestore.service.impl;

import com.hardwarestore.exception.OrderException;
import com.hardwarestore.exception.ProductNotFoundException;
import com.hardwarestore.model.Order;
import com.hardwarestore.model.OrderItem;
import com.hardwarestore.model.Product;
import com.hardwarestore.repository.OrderRepository;
import com.hardwarestore.service.OrderService;
import com.hardwarestore.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public Order createOrder(List<Product> products) throws OrderException, ProductNotFoundException {

        Order order = new Order();
        List<OrderItem> orderItems = new ArrayList<>();
        double totalAmount = 0;

        // Check if all products exist and have sufficient quantities
        for (Product product : products) {
            Optional<Product> productOpt = productService.getProductById(product.getId());
            if (productOpt.isEmpty()) {
                throw new OrderException("Product with ID " + product.getId() + " not found.");
            }

            Product dbProduct = productOpt.get();
            if(productOpt.get().getQuantity() < product.getQuantity()) {
                throw new OrderException("Product with ID " + product.getId() + " low in stock.");
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(dbProduct);
            orderItem.setOrder(order);
            orderItem.setQuantity(product.getQuantity());
            orderItems.add(orderItem);

            // update product quantity
            dbProduct.setQuantity(dbProduct.getQuantity() - product.getQuantity());
            totalAmount += dbProduct.getPrice() * product.getQuantity();

            productService.updateProduct(dbProduct);
        }

        // save the order
        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount);
        order.setOrderDate(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));

        return orderRepository.save(order);
    }
}
