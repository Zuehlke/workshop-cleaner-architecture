package com.zuehlke.colossus.catalog.repositories;

import com.zuehlke.colossus.catalog.model.Product;
import com.zuehlke.colossus.catalog.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    @Query(("SELECT p FROM Product p WHERE p.categoryId = :category_id"))
    List<Product> findProductByCategoryId(@Param("category_id") Long categoryId);

}
