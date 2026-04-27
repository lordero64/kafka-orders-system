package com.example.ordersservice.controller;

import com.example.ordersservice.service.OrderProducerService;
import com.egorov.commonmodel.dto.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderProducerService producerService;
    public OrderController(OrderProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody Order order) {
        order.setStatus("NEW");

        producerService.sendOrder(order);

        return ResponseEntity.status(HttpStatus.CREATED).body("Заказ отправлен в обработку = " + order.getId());
    }
}
