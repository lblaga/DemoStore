package com.lblaga.demostore.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * The order item entity, representing an ordered product from the store.
 */
@Entity
@Table(name = "order_items")
public class OrderItem implements Serializable, Comparable<OrderItem> {
    @EmbeddedId
    private OrderItemPK id;

    @NotNull
    private Double price;

    @NotNull
    private Long amount;

    private int sorter;

    OrderItem() {
    }

    private OrderItem(Builder builder) {
        this.id = builder.id;
        this.price = builder.price;
        this.amount = builder.amount;
        this.sorter = builder.sorter;
    }

    public OrderItemPK getId() {
        return id;
    }

    public Double getPrice() {
        return price;
    }

    public Long getAmount() {
        return amount;
    }

    public int getSorter() {
        return sorter;
    }

    public static class Builder {
        private OrderItemPK id;
        private @NotNull Double price;
        private @NotNull Long amount;
        private int sorter;

        public Builder id(OrderItemPK id) {
            this.id = id;
            return this;
        }

        public Builder price(@NotNull Double price) {
            this.price = price;
            return this;
        }

        public Builder amount(@NotNull Long amount) {
            this.amount = amount;
            return this;
        }

        public Builder sorter(int sorter) {
            this.sorter = sorter;
            return this;
        }

        public OrderItem build() {
            return new OrderItem(this);
        }
    }

    @Override
    public int compareTo(OrderItem o) {
        return Integer.valueOf(this.sorter).compareTo(o.getSorter());
    }
}
