package com.lblaga.demostore.controller.integration;

import com.lblaga.demostore.controller.OrderController;
import com.lblaga.demostore.transfer.OrderBriefResponse;
import com.lblaga.demostore.transfer.OrderResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Integration test for {@link OrderController}. This will load a fully fledged string boot application, uses test
 * data created at application startup.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void givenOrders_whenSearchByCreatedDate_thenAllOrdersHaveCalculatedPrice()
            throws Exception {
        String today = LocalDate.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_DATE);
        ResponseEntity<List<OrderBriefResponse>> responseEntity =
                restTemplate.exchange("/orders/search?from={from}&to={to}", HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<OrderBriefResponse>>(){}, today, today);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<OrderBriefResponse> orders = responseEntity.getBody();
        assertTrue(orders.size() > 0);
        for (OrderBriefResponse order : orders) {
            assertNotNull(order.getTotalPrice());
        }
    }

    @Test
    public void givenOrders_whenGetOrder_thenReturnDetailedOrder()
            throws Exception {
        String today = LocalDate.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_DATE);
        ResponseEntity<List<OrderBriefResponse>> responseEntity =
                restTemplate.exchange("/orders/search?from={from}&to={to}", HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<OrderBriefResponse>>(){}, today, today);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<OrderBriefResponse> orders = responseEntity.getBody();
        assertTrue(orders.size() > 0);
        Long testId = orders.get((int) (Math.random() * orders.size())).getId();

        OrderResponse orderResponse = restTemplate.getForObject("/orders/{id}", OrderResponse.class, testId);

        assertNotNull(orderResponse.getId());
        assertNotNull(orderResponse.getTotalPrice());
        assertNotNull(orderResponse.getCreatedDate());

    }
}
