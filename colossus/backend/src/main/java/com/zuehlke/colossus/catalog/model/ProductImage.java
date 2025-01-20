package com.zuehlke.colossus.catalog.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "product_image")
public class ProductImage {

    @Id
    @GeneratedValue
    private Long id;

    @Lob
    @JsonProperty("image")
    private byte[] image;

    @JsonProperty("main")
    private boolean isMain;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;

    public ProductImage() {
    }

    public ProductImage(byte[] image, boolean isMain) {
        this.image = image;
        this.isMain = isMain;
    }

    public Long getId() {
        return id;
    }

    public byte[] image() {
        return image;
    }

    public boolean isMain() {
        return isMain;
    }

    public Product product() {
        return product;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductImage updateWith(Product product) {
        this.product = product;
        return this;
    }
}
