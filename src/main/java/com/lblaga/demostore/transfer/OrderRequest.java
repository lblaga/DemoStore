package com.lblaga.demostore.transfer;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Models the request json that will be transferred to create a new order
 */
public final class OrderRequest {
    @NotEmpty
    @Email
    @ApiModelProperty(value = "Buyer's email", example = "somebody@email.com")
    private String buyerEmail;

    @NotNull
    @NotEmpty
    @ApiModelProperty(value = "The ordered products")
    private List<OrderItemRequest> products;

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public List<OrderItemRequest> getProducts() {
        return products;
    }

    public void setProducts(List<OrderItemRequest> products) {
        this.products = products;
    }

    public OrderRequest() {
    }

    public OrderRequest(@NotEmpty @Email String buyerEmail, @NotNull @NotEmpty List<OrderItemRequest> products) {
        this.buyerEmail = buyerEmail;
        this.products = products;
    }
}
