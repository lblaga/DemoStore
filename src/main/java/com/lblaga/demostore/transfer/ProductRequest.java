package com.lblaga.demostore.transfer;

import io.swagger.annotations.ApiModelProperty;

/**
 * The product create/update request data
 */
public final class ProductRequest {
    @ApiModelProperty(value = "Product name", allowEmptyValue = true, example = "notebook")
    private String name;
    @ApiModelProperty(value = "Product price", allowEmptyValue = true, example = "18.89")
    private Double price;

    public ProductRequest() {
    }

    public ProductRequest(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductRequest that = (ProductRequest) o;

        return getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }
}
