package com.lblaga.demostore.transfer;

import java.time.ZonedDateTime;

/**
 * Represents an order without order items (used as an item of order search result)
 */
public final class OrderBriefResponse {
    private Long id;
    private String buyerEmail;
    private ZonedDateTime createdDate;
    private Double totalPrice;

    public OrderBriefResponse() {
    }

    public OrderBriefResponse(Long id, String buyerEmail, ZonedDateTime createdDate, Double totalPrice) {
        this.id = id;
        this.buyerEmail = buyerEmail;
        this.createdDate = createdDate;
        this.totalPrice = totalPrice;
    }

    private OrderBriefResponse(Builder builder) {
        this.id = builder.id;
        this.buyerEmail = builder.buyerEmail;
        this.createdDate = builder.createdDate;
        this.totalPrice = builder.totalPrice;
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

    public static class Builder {
        private Long id;
        private String buyerEmail;
        private ZonedDateTime createdDate;
        private Double totalPrice;

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

        public OrderBriefResponse build() {
            return new OrderBriefResponse(this);
        }
    }
}
