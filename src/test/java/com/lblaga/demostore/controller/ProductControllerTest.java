package com.lblaga.demostore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lblaga.demostore.exception.ProductNotFoundException;
import com.lblaga.demostore.model.Product;
import com.lblaga.demostore.service.ProductService;
import com.lblaga.demostore.transfer.ProductRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for {@link ProductController}, mocks {@link ProductService}
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductService productService;

    @Test
    public void whenCreateProduct_thenStatus201andLocationHeader()
            throws Exception {

        ProductRequest p1 = new ProductRequest("P1", 1.59);
        Product createdProduct = new Product.Builder().id(1L).name(p1.getName()).price(p1.getPrice()).build();

        given(productService.create(p1)).willReturn(createdProduct);

        mvc.perform(MockMvcRequestBuilders.post("/products")
                .content((new ObjectMapper().writeValueAsString(p1)))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andExpect(header().exists("Location"));
    }

    @Test
    public void givenProducts_whenGetProducts_thenReturnJsonArray()
            throws Exception {

        Product p1 = new Product.Builder().id(1L).name("P1").price(1.59).build();
        Product p2 = new Product.Builder().id(2L).name("P2").price(6.89).build();

        List<Product> allProducts = Stream.of(p1, p2).collect(Collectors.toList());

        given(productService.all()).willReturn(allProducts);

        mvc.perform(MockMvcRequestBuilders.get("/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name", is(p1.getName())))
                .andExpect(jsonPath("$[1].price", is(p2.getPrice())));
    }

    @Test
    public void givenProduct_whenGetNonExistingProduct_thenReturnErrorJson()
            throws Exception {
        given(productService.get(1L)).willThrow(new ProductNotFoundException(1L));

        mvc.perform(MockMvcRequestBuilders.get("/products/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.code").value(404));
    }

}
