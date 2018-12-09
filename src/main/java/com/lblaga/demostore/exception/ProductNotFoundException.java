package com.lblaga.demostore.exception;

/**
 * Exception thrown when product can't be located.
 */
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super(String.format("No product with id [%d]", id));
    }
}
