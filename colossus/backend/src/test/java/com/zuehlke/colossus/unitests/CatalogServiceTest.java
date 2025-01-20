package com.zuehlke.colossus.unitests;

import com.zuehlke.colossus.catalog.CatalogService;
import com.zuehlke.colossus.catalog.model.Catalog;
import com.zuehlke.colossus.catalog.model.Product;
import com.zuehlke.colossus.catalog.model.ProductCategory;
import com.zuehlke.colossus.catalog.repositories.ProductCategoryRepository;
import com.zuehlke.colossus.catalog.repositories.ProductDetailsRepository;
import com.zuehlke.colossus.catalog.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


public class CatalogServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductCategoryRepository productCategoryRepository;

    @Mock
    private ProductDetailsRepository productDetailsRepository;

    private List<ProductCategory> categories = asList(new ProductCategory[] {
                new ProductCategory("Bike")
            });

    private List<Product> products = asList(new Product[]
            {
                    new Product("X-Bike H-XR", "The X-Bike H-XR is...", null),
                    new Product("Velocity D-1", "The Velocity D-1", null)
            });

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getCatalog() {
        // given
        when(productRepository.findAll()).thenReturn(products);
        when(productCategoryRepository.findAll()).thenReturn(categories);
        CatalogService catalogService = new CatalogService(productRepository, productDetailsRepository, productCategoryRepository);

        // when
        Catalog catalog = catalogService.getCatalog();

        // then
        assertThat(catalog.products().size()).isEqualTo(2);
        assertThat(catalog.products().get(0).name()).isEqualTo("X-Bike H-XR");
        assertThat(catalog.products().get(1).name()).isEqualTo("Velocity D-1");
        assertThat(catalog.categories().size()).isEqualTo(1);
        assertThat(catalog.categories().get(0).name()).isEqualTo("Bike");
    }

    @Test
    public void getProducts() {
        // given
        when(productRepository.findAll()).thenReturn(products);
        CatalogService catalogService = new CatalogService(productRepository, productDetailsRepository, productCategoryRepository);

        // when
        List<Product> products = catalogService.getProducts();

        // then
        assertThat(products.size()).isEqualTo(2);
        assertThat(products.get(0).name()).isEqualTo("X-Bike H-XR");
        assertThat(products.get(1).name()).isEqualTo("Velocity D-1");
    }

    @Test
    public void getCategories() {
        // given
        when(productCategoryRepository.findAll()).thenReturn(categories);
        CatalogService catalogService = new CatalogService(productRepository, productDetailsRepository, productCategoryRepository);

        // when
        List<ProductCategory> categories = catalogService.getCategories();

        // then
        assertThat(categories.size()).isEqualTo(1);
        assertThat(categories.get(0).name()).isEqualTo("Bike");
    }
}
