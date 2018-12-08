package com.lblaga.demostore.service;

import com.lblaga.demostore.model.Order;
import com.lblaga.demostore.model.OrderItem;
import com.lblaga.demostore.model.OrderItemPK;
import com.lblaga.demostore.model.Product;
import com.lblaga.demostore.repository.OrderRepository;
import com.lblaga.demostore.transfer.OrderResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

/**
 * Test class for {@link OrderService}. mocks {@link OrderRepository}
 */
@RunWith(SpringRunner.class)
public class OrderServiceTest {
    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private ProductService productService;

    private OrderService orderService;

    @Before
    public void setUp() {
        Product p1 = new Product("P1", 1.25);
        p1.setId(1L);
        Product p2 = new Product("P2", 2.15);
        p1.setId(2L);

        Order order = new Order();
        order.setId(1L);
        order.setBuyerEmail("testbuyer@email.com");
        order.setCreatedDate(ZonedDateTime.now(ZoneOffset.UTC));

        OrderItem oi1 = new OrderItem.Builder()
                .id(new OrderItemPK(order, p1))
                .price(p1.getPrice())
                .amount(2L)
                .sorter(0)
                .build();

        OrderItem oi2 = new OrderItem.Builder()
                .id(new OrderItemPK(order, p2))
                .price(p2.getPrice())
                .amount(1L)
                .sorter(1)
                .build();
        List<OrderItem> orderItems = Stream.of(oi1, oi2).collect(Collectors.toList());
        order.setOrderItems(orderItems);
        Mockito.when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        orderService = new OrderServiceImpl(orderRepository, productService);
    }

    @Test
    public void whenOrder_thenCheckOrderResponse() {
        OrderResponse orderResponse = orderService.get(1L);

        assertEquals((Long) 1L, orderResponse.getId());

        assertEquals(2, orderResponse.getItems().size());
    }

    @Test
    public void whenOrder_thenCheckOrderTotalPrice() {
        OrderResponse orderResponse = orderService.get(1L);

        assertEquals((Double) (1.25*2 + 2.15), orderResponse.getTotalPrice());
    }
}
