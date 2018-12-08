package com.lblaga.demostore.controller.integration;

import com.lblaga.demostore.controller.ProductController;
import com.lblaga.demostore.model.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Integration test for {@link ProductController}. This will load a fully fledged string boot application, uses test
 * data created at application startup.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void givenProduct_whenUpdateOnlyPrice_thenStatus200() throws Exception {
        ResponseEntity<List<Product>> responseEntity =
                restTemplate.exchange("/products", HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Product>>(){});
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<Product> products = responseEntity.getBody();
        assertTrue(products.size() > 0);
        Long testId = products.get((int) (Math.random() * products.size())).getId();

        Product newProduct = new Product(null, Double.MAX_VALUE);

        HttpEntity<Product> request = new HttpEntity<>(newProduct);
        restTemplate.exchange("/products/{id}", HttpMethod.PUT, request, Void.class, testId);

        Product freshProduct = restTemplate.getForObject("/products/{id}", Product.class, testId);
        assertEquals((Double) Double.MAX_VALUE, freshProduct.getPrice());
    }
}
