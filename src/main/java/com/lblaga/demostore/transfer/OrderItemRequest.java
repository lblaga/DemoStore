package com.lblaga.demostore.transfer;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * An item (product) from {@link OrderRequest} order.
 */
public final class OrderItemRequest {
    @NotNull
    @ApiModelProperty(value = "Buyer's email", example = "10")
    private Long productId;
    @NotNull
    @ApiModelProperty(value = "The ordered amount of the product", example = "1")
    private Long amount;

    public Long getProductId() {
        return productId;
    }

    public Long getAmount() {
        return amount;
    }

    OrderItemRequest() {
    }

    public OrderItemRequest(Long productId, Long amount) {
        this.productId = productId;
        this.amount = amount;
    }
}
