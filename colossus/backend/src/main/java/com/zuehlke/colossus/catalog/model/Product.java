package com.zuehlke.colossus.catalog.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("summary")
    @Column(columnDefinition = "TEXT")
    private String summary;

    @JsonProperty("details")
    @Column(columnDefinition = "TEXT")
    private String details;

    @JsonProperty("categoryId")
    private Long categoryId;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonProperty("specification")
    @JsonManagedReference
    private ProductSpecification productSpecification;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonProperty("images")
    @JsonManagedReference
    private List<ProductImage> images;

    public Product() {
    }

    public Product(String name, String summary, Long categoryId) {
        this.name = name;
        this.summary = summary;
        this.categoryId = categoryId;
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String summary() {
        return summary;
    }

    public String details() {
        return details;
    }

    public Long categoryId() {
        return categoryId;
    }

    public ProductSpecification productSpecification() {
        return productSpecification;
    }

    public List<ProductImage> images() {
        return images;
    }

    public Product addProductSpecification(ProductSpecification newProductSpecification) {
        this.productSpecification = newProductSpecification.updateWith(this);
        return this;
    }

    public Product updateWith(ProductAndSpecification newProduct) {
        this.productSpecification = this.productSpecification.updateWith(newProduct.specification());
        this.name = newProduct.product().name();
        this.summary = newProduct.product().summary();
        this.details = newProduct.product().details();
        this.categoryId = newProduct.product().categoryId();
        return this;
    }

    public Product updateSummary(String newSummary) {
        this.summary = newSummary;
        return this;
    }

    public Product updateDetails(String newDetails) {
        this.details = newDetails;
        return this;
    }

    public Product addNewImage(byte[] image, boolean isMain) {
        if (this.images == null)
            this.images = new ArrayList<>();
        ProductImage productImage = new ProductImage(image, isMain);
        this.images.add(productImage);
        productImage.updateWith(this);
        return this;
    }
}
