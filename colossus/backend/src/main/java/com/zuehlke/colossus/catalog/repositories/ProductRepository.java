package com.zuehlke.colossus.catalog.repositories;

import com.zuehlke.colossus.catalog.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {

}
