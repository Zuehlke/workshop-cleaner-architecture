package com.zuehlke.colossus.rest.endpoints;

import com.zuehlke.colossus.catalog.CatalogService;
import com.zuehlke.colossus.catalog.model.Catalog;
import com.zuehlke.colossus.catalog.model.ProductAndSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CatalogEndpoint {

    @Autowired
    private CatalogService catalogService;

    @GetMapping("/catalog")
    public ResponseEntity<Catalog> catalog() {
        return ResponseEntity.ok().body(catalogService.getCatalog());
    }

    @GetMapping("/catalog/products/{id}")
    public ResponseEntity<ProductAndSpecification> product(@PathVariable Long id) {
        ProductAndSpecification productDetails = catalogService.getProductDetails(id);
        return ResponseEntity.ok().body(productDetails);
    }

    @PostMapping("/catalog/products/")
    public ResponseEntity<Catalog> addProduct(@RequestBody ProductAndSpecification product) {
        return ResponseEntity.ok().body(catalogService.addProduct(product));
    }

    @DeleteMapping("/catalog/products/{id}")
    public ResponseEntity<Catalog> deleteProduct(@PathVariable Long id) {
        return ResponseEntity.ok().body(catalogService.deleteProduct(id));
    }

    @PutMapping("/catalog/products/{id}")
    public ResponseEntity<Catalog> updateProduct(@PathVariable Long productId, @RequestBody ProductAndSpecification product) {
        return ResponseEntity.ok().body(catalogService.updateProduct(productId, product));
    }

}
