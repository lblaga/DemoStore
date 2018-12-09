package com.lblaga.demostore.transfer;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * This class encapsulates a detailed order data, with the list of ordered items and the total price.
 */
public final class OrderResponse {
    private Long id;
    private String buyerEmail;
    private ZonedDateTime createdDate;
    private Double totalPrice;
    private List<OrderItemResponse> items;

    OrderResponse() {
    }

    private OrderResponse(Builder builder) {
        this.id = builder.id;
        this.buyerEmail = builder.buyerEmail;
        this.createdDate = builder.createdDate;
        this.totalPrice = builder.totalPrice;
        this.items = builder.items;
    }

    public Long getId() {
        return id;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public List<OrderItemResponse> getItems() {
        return items;
    }

    public static class Builder {
        private Long id;
        private String buyerEmail;
        private ZonedDateTime createdDate;
        private Double totalPrice;
        private List<OrderItemResponse> items;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder buyerEmail(String buyerEmail) {
            this.buyerEmail = buyerEmail;
            return this;
        }

        public Builder createdDate(ZonedDateTime createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public Builder totalPrice(Double totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public Builder items(List<OrderItemResponse> items) {
            this.items = items;
            return this;
        }

        public OrderResponse build() {
            return new OrderResponse(this);
        }
    }
}
