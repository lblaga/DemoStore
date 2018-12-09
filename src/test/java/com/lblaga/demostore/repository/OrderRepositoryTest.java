package com.lblaga.demostore.repository;

import com.lblaga.demostore.misc.Round;
import com.lblaga.demostore.model.Order;
import com.lblaga.demostore.model.OrderItem;
import com.lblaga.demostore.model.OrderItemPK;
import com.lblaga.demostore.model.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

/**
 * Test class for {@link OrderRepository}
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class OrderRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderRepository orderRepository;

    // tests whether total price of the order is calculated correctly from the ordered item's proce and amount
    @Test
    public void whenGetOrder_calculateTotalPrice() {
        //given
        Product p1 = new Product.Builder().name("P1").price(1.25).build();
        Product p2 = new Product.Builder().name("P2").price(2.15).build();

        entityManager.persist(p1);
        entityManager.persist(p2);

        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
        Order order = new Order.Builder()
                .buyerEmail("testbuyer@email.com")
                .createdDate(now)
                .build();

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

        entityManager.persist(order);
        entityManager.flush();

        //when
        ZoneId zoneId = ZoneId.of("UTC");
        LocalDate ld = now.toLocalDate();

        List<Object[]> response = orderRepository.findTotalPricedCreateDateBetween(
                ld.atStartOfDay().atZone(zoneId),
                ld.plusDays(1).atStartOfDay(zoneId).minusNanos(1)
        );

        //then
        assertEquals(1, response.size());
        Double expected = Round.round(order.getOrderItems().stream()
                .mapToDouble(item -> item.getAmount() * item.getPrice()).sum(), 2);

        assertEquals(expected, Round.round((Double) response.get(0)[1], 2));

    }
}
