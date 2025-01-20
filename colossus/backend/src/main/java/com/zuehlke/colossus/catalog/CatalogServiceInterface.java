package com.zuehlke.colossus.catalog;

import com.zuehlke.colossus.catalog.model.Catalog;
import com.zuehlke.colossus.catalog.model.Product;
import com.zuehlke.colossus.catalog.model.ProductCategory;
import com.zuehlke.colossus.catalog.model.ProductAndSpecification;

import java.util.List;

public interface CatalogServiceInterface {

    Catalog getCatalog();

    List<Product> getProducts();

    Catalog addProduct(ProductAndSpecification productAndSpecification);

    Catalog deleteProduct(Long productId);

    ProductAndSpecification getProductDetails(Long productId);

    List<ProductCategory> getCategories();

    List<Product> getProductsOfCategory(Long categoryId);

    Catalog updateProduct(Long productId, ProductAndSpecification product);

    Catalog updateProductSummary(Long productId, String newSummary);

    Catalog updateProductDetails(Long productId, String newDetails);

    Catalog addProductImage(Long productId, byte[] image, boolean isMain);
}
