package com.learn.cloud.gcp.pubsub.controller;

import com.learn.cloud.gcp.pubsub.model.Order;
import com.learn.cloud.gcp.pubsub.model.OrderResponse;
import com.learn.cloud.gcp.pubsub.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody Order order) {
        OrderResponse orderResponse = orderService.createOrder(order);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);

    }
}
