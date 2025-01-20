package com.zuehlke.colossus.catalog.repositories;

import com.zuehlke.colossus.catalog.model.ProductSpecification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductDetailsRepository extends JpaRepository<ProductSpecification, Long> {

}
