package com.lblaga.demostore.service;

import com.lblaga.demostore.model.Order;
import com.lblaga.demostore.transfer.OrderBriefResponse;
import com.lblaga.demostore.transfer.OrderRequest;
import com.lblaga.demostore.transfer.OrderResponse;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Order service interface
 */
public interface OrderService {
    Order create(OrderRequest request);
    OrderResponse get(Long id);
    List<OrderBriefResponse> byInterval(ZonedDateTime from, ZonedDateTime to);
    void delete(Long id);
}
