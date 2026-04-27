package com.example.ordersservice.service;

import com.egorov.commonmodel.dto.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducerService {

    private final KafkaTemplate<String, Order> kafkaTemplate;

    @Value("${kafka.topic.new-orders}")
    private String topic;

    public OrderProducerService(KafkaTemplate<String, Order> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrder(Order order) {
        kafkaTemplate.send(topic, order.getUserId().toString(), order);
    }
}
