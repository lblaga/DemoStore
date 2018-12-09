package com.lblaga.demostore.repository;

import com.lblaga.demostore.model.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Test case for {@link ProductRepository}
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    // this is just a basic test to find an already created product by its id
    @Test
    public void whenCreate_thenReturnProduct() {
        // given
        Product product = new Product.Builder().name("laptop").price(999.89).build();
        Product newProduct = entityManager.persist(product);
        entityManager.flush();

        // when
        Optional<Product> found = productRepository.findById(newProduct.getId());

        // then
        assertTrue(found.isPresent());

        Product foundProduct = found.get();

        assertNotNull(foundProduct.getId());

        assertEquals(newProduct.getId(), foundProduct.getId());

        assertEquals("laptop", foundProduct.getName());

        assertEquals((Double) 999.89, foundProduct.getPrice());
    }
}
