package com.hardwarestore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @OneToOne
    private Product product;

    @JsonIgnore
    @OneToOne
    private Order order;

    private Long quantity;

    @JsonProperty("orderId")
    public Long getOrderId() {
        return order != null ? order.getId() : null;
    }

    @JsonProperty("productName")
    public String getProductName() {
        return product != null ? product.getName() : null;
    }

    @JsonProperty("productPrice")
    public Double getProductPrice() {
        return product != null ? product.getPrice() : null;
    }
}
