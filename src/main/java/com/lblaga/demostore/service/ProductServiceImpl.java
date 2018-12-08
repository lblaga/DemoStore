package com.lblaga.demostore.service;

import com.lblaga.demostore.exception.ProductNotFoundException;
import com.lblaga.demostore.model.Product;
import com.lblaga.demostore.repository.ProductRepository;
import com.lblaga.demostore.transfer.ProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * Product service implementation.
 */
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public Product create(ProductRequest request) {
        return productRepository.save(new Product(request.getName(), request.getPrice()));
    }

    @Override
    @Transactional(readOnly = true)
    public Product get(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()) {
            throw new ProductNotFoundException(id);
        }
        return product.get();
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Product> all() {
        return productRepository.findAll();
    }

    @Override
    @Transactional
    public void update(ProductRequest request, Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (!productOptional.isPresent()) {
            throw new ProductNotFoundException(id);
        }

        Product oldProduct = productOptional.get();
        Product newProduct = new Product();
        newProduct.setId(id);
        newProduct.setName(StringUtils.hasText(request.getName()) ? request.getName() : oldProduct.getName());
        newProduct.setPrice(request.getPrice() != null ? request.getPrice() : oldProduct.getPrice());

        productRepository.save(newProduct);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
