package com.zuehlke.colossus.catalog.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductAndSpecification {

    @JsonProperty("product")
    private Product product;

    @JsonProperty("specification")
    private ProductSpecification specification;

    public ProductAndSpecification(Product product, ProductSpecification specification) {
        this.product = product;
        this.specification = specification;
    }

    public Product product() {
        return product;
    }

    public ProductSpecification specification() {
        return specification;
    }
}
