package com.lblaga.demostore.repository;

import com.lblaga.demostore.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * CRUD repository for {@link Product}
 */
@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
}
