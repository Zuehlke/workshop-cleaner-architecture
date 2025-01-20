package com.zuehlke.colossus.catalog.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Catalog {

    @JsonProperty("products")
    private List<Product> products;

    @JsonProperty("categories")
    private List<ProductCategory> categories;

    public Catalog(List<Product> products, List<ProductCategory> categories) {
        this.products = products;
        this.categories = categories;
    }

    public List<Product> products() {
        return products;
    }

    public List<ProductCategory> categories() {
        return categories;
    }
}
