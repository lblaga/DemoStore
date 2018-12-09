package com.lblaga.demostore.service;

import com.lblaga.demostore.exception.OrderNotFoundException;
import com.lblaga.demostore.misc.Round;
import com.lblaga.demostore.model.Order;
import com.lblaga.demostore.model.OrderItem;
import com.lblaga.demostore.model.OrderItemPK;
import com.lblaga.demostore.model.Product;
import com.lblaga.demostore.repository.OrderRepository;
import com.lblaga.demostore.transfer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Implementation on {@link OrderService}
 */
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    private final ProductService productService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ProductService productService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    @Override
    @Transactional
    public Order create(OrderRequest request) {
        Order order = new Order.Builder()
                .buyerEmail(request.getBuyerEmail())
                .createdDate(ZonedDateTime.now(ZoneOffset.UTC))
                .build();

        List<OrderItem> orderItemList = IntStream.range(0, request.getProducts().size())
                .mapToObj(i -> {
                    OrderItemRequest item = request.getProducts().get(i);
                    Product product = productService.get(item.getProductId());
                    return new OrderItem.Builder()
                            .id(new OrderItemPK(order, product))
                            .price(product.getPrice())
                            .amount(item.getAmount() != null ? item.getAmount() : 1L)
                            .sorter(i)
                            .build();
                }).collect(Collectors.toList());

        order.setOrderItems(orderItemList);
        return orderRepository.save(order);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse get(Long id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (!orderOptional.isPresent()) {
            throw new OrderNotFoundException(id);
        }
        Order order = orderOptional.get();
        List<OrderItemResponse> items = order.getOrderItems().stream()
                .sorted()
                .map(item -> {
                    Product product = item.getId().getProduct();
                    return new OrderItemResponse.Builder()
                        .productId(product.getId())
                        .productName(product.getName())
                        .price(item.getPrice())
                        .amount(item.getAmount())
                        .total(Round.round(item.getPrice() * item.getAmount(), 2))
                        .build();
                })
                .collect(Collectors.toList());
        Double totalPrice = Round.round(order.getOrderItems().stream()
                .mapToDouble(item -> item.getAmount() * item.getPrice()).sum(), 2);

        return new OrderResponse.Builder()
                .id(order.getId())
                .buyerEmail(order.getBuyerEmail())
                .createdDate(order.getCreatedDate())
                .totalPrice(totalPrice)
                .items(items)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderBriefResponse> byInterval(ZonedDateTime from, ZonedDateTime to) {
        List<Object[]> orders = orderRepository.findTotalPricedCreateDateBetween(from, to);

        return orders
                .stream()
                .map(o -> {
                    Order order = (Order) o[0];
                    return new OrderBriefResponse.Builder()
                        .id(order.getId())
                        .buyerEmail(order.getBuyerEmail())
                        .createdDate(order.getCreatedDate())
                        .totalPrice(Round.round((Double) o[1], 2))
                        .build();})
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }
}
