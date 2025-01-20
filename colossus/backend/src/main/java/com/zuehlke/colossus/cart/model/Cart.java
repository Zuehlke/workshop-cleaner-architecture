package com.zuehlke.colossus.cart.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @JsonProperty("sessionID")
    private String sessionid;

    @ElementCollection
    @JsonProperty("products")
    private List<Long> products;

    public Cart() {
    }

    public Cart(String sessionid) {
        this.sessionid = sessionid;
    }

    public void addProduct(Long productId) {
        if (this.products == null)
            this.products = new ArrayList<>();
        this.products.add(productId);
    }

    public Long id() {
        return id;
    }

    public String sessionid() {
        return sessionid;
    }

    public List<Long> products() {
        return products;
    }
}
