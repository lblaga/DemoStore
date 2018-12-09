package com.lblaga.demostore.transfer;

/**
 * The ordered item in the order response.
 */
public final class OrderItemResponse {
    private Long productId;
    private String productName;
    private Double price;
    private Long amount;
    private Double total;

    OrderItemResponse() {
    }

    private OrderItemResponse(Builder builder) {
        this.productId = builder.productId;
        this.productName = builder.productName;
        this.price = builder.price;
        this.amount = builder.amount;
        this.total = builder.total;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public Double getPrice() {
        return price;
    }

    public Long getAmount() {
        return amount;
    }

    public Double getTotal() {
        return total;
    }

    public static class Builder {
        private Long productId;
        private String productName;
        private Double price;
        private Long amount;
        private Double total;

        public Builder productId(Long productId) {
            this.productId = productId;
            return this;
        }

        public Builder productName(String productName) {
            this.productName = productName;
            return this;
        }

        public Builder price(Double price) {
            this.price = price;
            return this;
        }

        public Builder amount(Long amount) {
            this.amount = amount;
            return this;
        }

        public Builder total(Double total) {
            this.total = total;
            return this;
        }

        public OrderItemResponse build() {
            return new OrderItemResponse(this);
        }
    }
}
