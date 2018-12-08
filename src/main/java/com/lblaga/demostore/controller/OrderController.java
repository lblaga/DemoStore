package com.lblaga.demostore.controller;

import com.lblaga.demostore.model.Order;
import com.lblaga.demostore.service.OrderService;
import com.lblaga.demostore.transfer.OrderBriefResponse;
import com.lblaga.demostore.transfer.OrderRequest;
import com.lblaga.demostore.transfer.OrderResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

/**
 * REST API gateway for order related requests.
 */
@RestController
@RequestMapping("/orders")
@Api(value="order-api", description="Order API Gateway", tags = "Order API")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ApiOperation(value = "Creates a new order.", notes = "Creates a new order based on the passed request data." +
            "In case of successful creation will respond with status code 201 (CREATED) " +
            "and will set the response header's 'Location' to the URI of the newly created order.")
    public ResponseEntity<Object> create(
            @ApiParam(value = "Order create data", required=true) @RequestBody @Valid OrderRequest request) {
        Order order = orderService.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(order.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Retrieves an order.", notes = "Fetches an order with its detailed order items.")
    public OrderResponse get(
            @ApiParam(value = "Order id", required=true, example = "20")
            @PathVariable("id") Long id) {
        return orderService.get(id);
    }

    @GetMapping("/search")
    @ApiOperation(value = "Searches for orders.", notes = "Search for orders based on their created date. " +
            "Result will be a list of brief orders (without order items, but with calculated total price) " +
            "ordered by created date descending.")
    public List<OrderBriefResponse> search(
            @ApiParam(value = "Order created day from in YYYY-MM-dd format", example = "2018-12-10", required=true)
            @RequestParam("from") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
            @ApiParam(value = "Order created day to in YYYY-MM-dd format", example = "2018-12-10", required=true)
            @RequestParam("to") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to) {

        ZoneId zoneId = ZoneId.of("UTC"); //TODO probably the timezone should be the client's timezone
        return orderService.byInterval(from.atStartOfDay().atZone(zoneId),
                to.plusDays(1).atStartOfDay(zoneId).minusNanos(1));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deletes an order.", notes = "Deletes an order identified by its id.")
    public void deleteOrder(@ApiParam(value = "Order id", example = "20", required=true) @PathVariable("id") Long id) {
        orderService.delete(id);
    }
}
