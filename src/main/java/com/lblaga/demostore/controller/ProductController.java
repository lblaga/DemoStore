package com.lblaga.demostore.controller;

import com.lblaga.demostore.model.Product;
import com.lblaga.demostore.service.ProductService;
import com.lblaga.demostore.transfer.ProductRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * REST API gateway for products.
 */
@RestController
@RequestMapping("/products")
@Api(value="product-api", description="Product API Gateway", tags = "Product API")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ApiOperation(value = "Creates a new product.",
            notes = "Creates a new product with the JSON object passed as parameter. " +
                    "In case of successful creation will respond with status code 201 (CREATED) " +
                    "and will set the response header's 'Location' to the URI of the newly created product.")
    public ResponseEntity<Object> create(
            @ApiParam(value = "Product create data", required=true)
            @RequestBody ProductRequest product) {
        Product newProduct = productService.create(product);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newProduct.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Retrieves a product.", notes = "Retrieves a product corresponding to the passed id.")
    public Product get(@ApiParam(value = "Product id", required=true, example = "1") @PathVariable("id") Long id) {
        return productService.get(id);
    }

    @GetMapping
    @ApiOperation(value = "Retrieves all products.", notes = "Retrieves all saved products.")
    public Iterable<Product> all() {
        return productService.all();
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Updates a product.", notes = "Updates the product identified by its id.")
    public void update(
            @ApiParam(value = "Product update data. Any field value can be null (missing). In this case the original field will be kept during update.",
                    required=true) @RequestBody ProductRequest product,
            @ApiParam(value = "Product id", required=true, example = "1") @PathVariable("id") Long id) {
        productService.update(product, id);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deletes a product.", notes = "Deletes a product corresponding to the passed id.")
    public void delete(@ApiParam(value = "Product id", required=true) @PathVariable("id") Long id) {
        productService.delete(id);
    }
}
