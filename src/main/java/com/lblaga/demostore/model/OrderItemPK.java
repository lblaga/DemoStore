package com.lblaga.demostore.model;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Composite primary key of {@link OrderItem} entity.
 */
@Embeddable
public class OrderItemPK implements Serializable {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    OrderItemPK() {
    }

    public OrderItemPK(Order order, Product product) {
        this.order = order;
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public Product getProduct() {
        return product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderItemPK that = (OrderItemPK) o;

        return getOrder().getId().equals(that.getOrder().getId()) && getProduct().getId().equals(that.getProduct().getId());
    }

    @Override
    public int hashCode() {
        int result = getOrder().getId().hashCode();
        result = 31 * result + getProduct().getId().hashCode();
        return result;
    }

}
