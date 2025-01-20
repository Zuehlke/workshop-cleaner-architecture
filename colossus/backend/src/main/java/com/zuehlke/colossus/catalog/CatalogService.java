package com.zuehlke.colossus.catalog;

import com.zuehlke.colossus.catalog.model.Catalog;
import com.zuehlke.colossus.catalog.model.Product;
import com.zuehlke.colossus.catalog.model.ProductCategory;
import com.zuehlke.colossus.catalog.model.ProductAndSpecification;
import com.zuehlke.colossus.catalog.repositories.ProductCategoryRepository;
import com.zuehlke.colossus.catalog.repositories.ProductDetailsRepository;
import com.zuehlke.colossus.catalog.repositories.ProductRepository;
import com.zuehlke.colossus.exceptions.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CatalogService implements CatalogServiceInterface {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductDetailsRepository productDetailsRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    public CatalogService() {
    }

    public CatalogService(ProductRepository productRepository, ProductDetailsRepository productDetailsRepository, ProductCategoryRepository productCategoryRepository) {
        this.productRepository = productRepository;
        this.productDetailsRepository = productDetailsRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public Catalog getCatalog() {
        return new Catalog(getProducts(), getCategories());
    }

    @Override
    public List<Product> getProducts() {
        List<Product> productList = new ArrayList<>();
        this.productRepository.findAll().forEach(productList::add);
        return productList;
    }

    @Override
    public ProductAndSpecification getProductDetails(Long productId) {
        Optional<Product> product = this.productRepository.findById(productId);
        if (product.isEmpty()) {
            throw new ApplicationException("", ApplicationException.NOT_FOUND);
        }
        return new ProductAndSpecification(product.get(), product.get().productSpecification());
    }

    @Override
    public List<ProductCategory> getCategories() {
        List<ProductCategory> list = new ArrayList<>();
        this.productCategoryRepository.findAll().forEach(list::add);
        return list;
    }

    @Override
    public List<Product> getProductsOfCategory(Long categoryId) {
        List<Product> productList = new ArrayList<>();
        this.productCategoryRepository.findProductByCategoryId(categoryId).forEach(productList::add);
        return productList;
    }

    @Override
    public Catalog addProduct(ProductAndSpecification productAndSpecification) {
        Product product = productAndSpecification.product().addProductSpecification(productAndSpecification.specification());
        this.productRepository.save(product);
        return getCatalog();
    }

    @Override
    public Catalog deleteProduct(Long productId) {
        this.productRepository.deleteById(productId);
        return getCatalog();
    }

    @Override
    public Catalog updateProduct(Long productId, ProductAndSpecification newProduct) {
        ProductAndSpecification currentProduct = getProductDetails(productId);
        Product updatedProduct = currentProduct.product().updateWith(newProduct);
        this.productRepository.save(updatedProduct);
        return getCatalog();
    }

    @Override
    public Catalog updateProductSummary(Long productId, String newSummary) {
        ProductAndSpecification currentProduct = getProductDetails(productId);
        Product updatedProduct = currentProduct.product().updateSummary(newSummary);
        this.productRepository.save(updatedProduct);
        return getCatalog();
    }

    @Override
    public Catalog updateProductDetails(Long productId, String newDetails) {
        ProductAndSpecification currentProduct = getProductDetails(productId);
        Product updatedProduct = currentProduct.product().updateDetails(newDetails);
        this.productRepository.save(updatedProduct);
        return getCatalog();
    }

    @Override
    public Catalog addProductImage(Long productId, byte[] image, boolean isMain) {
        ProductAndSpecification currentProduct = getProductDetails(productId);
        Product updatedProduct = currentProduct.product().addNewImage(image, isMain);
        this.productRepository.save(updatedProduct);
        return getCatalog();
    }


}
