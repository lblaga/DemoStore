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
        Product p1 = new Product.Builder().id(1L).name("P1").price(1.25).build();
        Product p2 = new Product.Builder().id(2L).name("P2").price(2.15).build();

        Order order = new Order.Builder().id(1L).buyerEmail("testbuyer@email.com")
                .createdDate(ZonedDateTime.now(ZoneOffset.UTC)).build();

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
