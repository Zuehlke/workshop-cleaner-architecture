package com.zuehlke.colossus.integrationtests.catalog;

import com.zuehlke.colossus.ColossusApplication;
import com.zuehlke.colossus.catalog.CatalogService;
import com.zuehlke.colossus.catalog.model.ProductAndSpecification;
import com.zuehlke.colossus.catalog.model.Catalog;
import com.zuehlke.colossus.catalog.model.Product;
import com.zuehlke.colossus.catalog.model.ProductCategory;
import com.zuehlke.colossus.catalog.model.ProductSpecification;
import com.zuehlke.colossus.catalog.repositories.ProductCategoryRepository;
import com.zuehlke.colossus.catalog.repositories.ProductDetailsRepository;
import com.zuehlke.colossus.catalog.repositories.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = ColossusApplication.class)
public class CatalogServiceTest {

    private final static String DESC_DEFAULT = "A default product description....";

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductDetailsRepository productDetailsRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private CatalogService catalogService;

    private List<Product> products;

    private List<ProductSpecification> productDetails;

    private List<ProductCategory> productCategories;

    private static byte[] testImage;

    @BeforeEach
    public void setUp() throws IOException {
        // Initialize test data before each test method
        ProductCategory productCategory = new ProductCategory("Bike");
        productCategories = new ArrayList<>();
        productCategories.add(productCategory);
        productCategories.stream().forEach(p -> productCategoryRepository.saveAndFlush(p));

        products = new ArrayList<>();
        products.add(new Product("X-Bike H-XR", "This is X-Bike H-XR ...", productCategory.id()));
        products.add(new Product("Velocity D-1", "This is Velocity D-1", productCategory.id()));
        products.stream().forEach(p -> productRepository.saveAndFlush(p));

        productDetails = new ArrayList<>();
        productDetails.add(new ProductSpecification(products.get(0), DESC_DEFAULT));
        productDetails.add(new ProductSpecification(products.get(1), DESC_DEFAULT));
        productDetails.stream().forEach(d -> productDetailsRepository.saveAndFlush(d));

        productDetails.forEach(pd ->  {
            productRepository.saveAndFlush(pd.product().addProductSpecification(pd));
        });

        entityManager.clear();

        testImage = Thread.currentThread().getContextClassLoader().getResourceAsStream("bike.png").readAllBytes();
    }

    @AfterEach
    public void tearDown() {
        // Release test data after each test method
        productDetailsRepository.deleteAll();
        productRepository.deleteAll();
        productCategoryRepository.deleteAll();
    }

    @Test
    public void getCatalog() {
        // given

        // when
        Catalog catalog = catalogService.getCatalog();

        // then
        Assertions.assertNotNull(catalog);
        Assertions.assertEquals(2, catalog.products().size());
    }

    @Test
    public void getProducts() {
        // given

        // when
        List<Product> products = catalogService.getProducts();

        // then
        Assertions.assertNotNull(products);
        Assertions.assertEquals(2, products.size());
    }

    @Test
    public void getCategories() {
        // given

        // when
        List<ProductCategory> categories = catalogService.getCategories();

        // then
        Assertions.assertNotNull(categories);
        Assertions.assertEquals(1, categories.size());
    }

    @Test
    public void getProductsOfCategory() {
        // given

        // when
        List<Product> products = catalogService.getProductsOfCategory(productCategories.get(0).id());

        // then
        Assertions.assertNotNull(products);
        Assertions.assertEquals(2, products.size());
    }

    @Test
    public void getProductAndSpecification() {
        // given
        Long productId = products.get(0).id();

        // when
        ProductAndSpecification productDetails =
                catalogService.getProductDetails(productId);

        // then
        Assertions.assertNotNull(productDetails);
        Assertions.assertEquals(productId, productDetails.product().id());
        Assertions.assertEquals(DESC_DEFAULT, productDetails.specification().description());
    }

    @Test
    public void updateProductSummary() {
        // given
        Long productId = products.get(0).id();
        String newSummary = "This is a new summary";

        // when
        Catalog catalog = catalogService.updateProductSummary(productId, newSummary);

        // then
        Product product = catalog.products().stream().filter(p -> p.id().equals(productId)).findFirst().get();
        Assertions.assertEquals(product.summary(), "This is a new summary");
    }

    @Test
    public void updateProductDetails() {
        // given
        Long productId = products.get(0).id();
        String newDetails = "This is a new detail";

        // when
        Catalog catalog = catalogService.updateProductDetails(productId, newDetails);

        // then
        Product product = catalog.products().stream().filter(p -> p.id().equals(productId)).findFirst().get();
        Assertions.assertEquals(product.details(), "This is a new detail");
    }

    @Test
    public void addProduct() {
        // given
        Product product = new Product("Excelio-23D", "This is Excelio-23D...", productCategories.get(0).id());
        ProductSpecification productSpecification = new ProductSpecification(product, "The Excelio-23D is...");
        ProductAndSpecification productAndSpecification = new ProductAndSpecification(product, productSpecification);

        // when
        Catalog catalog = catalogService.addProduct(productAndSpecification);

        // then
        Assertions.assertEquals(catalog.products().get(2).name(), "Excelio-23D");
        Assertions.assertEquals(catalog.products().get(2).summary(), "This is Excelio-23D...");
        Assertions.assertEquals(catalog.products().get(2).productSpecification().description(), "The Excelio-23D is...");
    }

    @Test
    public void deleteProduct() {
        // given

        // when
        Catalog catalog = catalogService.deleteProduct(products.get(0).id());

        // then
        Assertions.assertEquals(catalog.products().size(), 1);
    }

    @Test
    public void updateProductAndSpecification() {
        // given
        Product newProduct = new Product("XRay-777", "The XRay-777 is...", productCategories.get(0).id());
        ProductSpecification newProductSpecification = new ProductSpecification(newProduct, "The XRay-777 is...");
        ProductAndSpecification newProductAndSpecification = new ProductAndSpecification(newProduct, newProductSpecification);

        // when
        Catalog catalog = catalogService.updateProduct(products.get(0).id(), newProductAndSpecification);

        // then
        Assertions.assertEquals(catalog.products().size(), 2);
        Product updatedProduct = catalog.products().stream().filter(p -> p.id().equals(products.get(0).id())).findFirst().get();
        Assertions.assertEquals(updatedProduct.name(), "XRay-777");
        Assertions.assertEquals(updatedProduct.summary(), "The XRay-777 is...");
        Assertions.assertEquals(updatedProduct.details(), null);
        Assertions.assertEquals(updatedProduct.categoryId(), productCategories.get(0).id());
        Assertions.assertEquals(updatedProduct.productSpecification().description(), "The XRay-777 is...");
    }

    @Test
    public void addProductImage() {
        // given
        Long productId = products.get(0).id();

        // when
        Catalog catalog = catalogService.addProductImage(productId, testImage, false);

        // then
        Product product = catalog.products().stream().filter(p -> p.id().equals(productId)).findFirst().get();
        Assertions.assertEquals(product.images().size(), 1);
    }
}
