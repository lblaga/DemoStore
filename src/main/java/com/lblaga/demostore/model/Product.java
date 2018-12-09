package com.lblaga.demostore.model;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * The Product entity, representing a store's actual product.
 */
@Entity
@Table(name = "products")
@DynamicUpdate
public class Product {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(unique=true)
    private String name;

    @NotNull
    private Double price;

    Product() {
    }

    private Product(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.price = builder.price;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return getName() != null ? getName().equals(product.getName()) : product.getName() == null;
    }

    @Override
    public int hashCode() {
        return getName() != null ? getName().hashCode() : 0;
    }

    public static class Builder {
        private Long id;
        private @NotNull String name;
        private @NotNull Double price;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(@NotNull String name) {
            this.name = name;
            return this;
        }

        public Builder price(@NotNull Double price) {
            this.price = price;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
