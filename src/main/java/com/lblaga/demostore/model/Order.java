package com.lblaga.demostore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * The Order entity, modelling an order in the store.
 */
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @NotNull
    @Email
    private String buyerEmail;

    @NotNull
    private ZonedDateTime createdDate;

    @OneToMany(mappedBy = "id.order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    @NotEmpty
    private List<OrderItem> orderItems;

    public Order() {
    }

    private Order(Builder builder) {
        this.buyerEmail = builder.buyerEmail;
        this.createdDate = builder.createdDate;
        this.orderItems = builder.orderItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public static class Builder {
        private @NotNull @Email String buyerEmail;
        private @NotNull ZonedDateTime createdDate;
        private @NotEmpty List<OrderItem> orderItems;

        public Builder id() {
            return this;
        }

        public Builder buyerEmail(@NotNull @Email String buyerEmail) {
            this.buyerEmail = buyerEmail;
            return this;
        }

        public Builder createdDate(@NotNull ZonedDateTime createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public Builder orderItems(@NotEmpty List<OrderItem> orderItems) {
            this.orderItems = orderItems;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
