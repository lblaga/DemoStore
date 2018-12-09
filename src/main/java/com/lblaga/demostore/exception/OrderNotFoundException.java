package com.lblaga.demostore.exception;

/**
 * Exception thrown when order can't be located.
 */
public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long id) {
        super(String.format("No order with id [%d]", id));
    }
}
