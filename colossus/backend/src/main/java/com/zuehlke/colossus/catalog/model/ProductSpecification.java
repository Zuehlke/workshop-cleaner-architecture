package com.zuehlke.colossus.catalog.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "product_specification")
public class ProductSpecification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @Column(columnDefinition = "TEXT")
    @JsonProperty("description")
    private String description;

    @OneToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;

    public ProductSpecification() {
    }

    public ProductSpecification(Product product, String description) {
        this.product = product;
        this.description = description;
    }

    public Long id() {
        return id;
    }

    public String description() {
        return description;
    }

    public Product product() {
        return product;
    }

    public ProductSpecification updateWith(Product product) {
        this.product = product;
        return this;
    }

    public ProductSpecification updateWith(ProductSpecification productSpecification) {
        this.description = productSpecification.description();
        return this;
    }
}
