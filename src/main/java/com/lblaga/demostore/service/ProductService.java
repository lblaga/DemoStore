package com.lblaga.demostore.service;

import com.lblaga.demostore.model.Product;
import com.lblaga.demostore.transfer.ProductRequest;

/**
 * Product service interface.
 */
public interface ProductService {
    Iterable<Product> all();

    Product create(ProductRequest product);

    Product get(Long id);

    void update(ProductRequest product, Long id);

    void delete(Long id);
}
