package com.egorov.paymentservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.egorov.commonmodel.dto.Order;

@Service
public class PaymentConsumerService {

    @Autowired
    private KafkaTemplate<String, Order> kafkaTemplate;

    @Value("${kafka.topic.payed-orders:payed_orders}")
    private String payedOrdersTopic;

    @KafkaListener(topics ="new_orders", groupId = "payment-group", concurrency = "3")
    public void processPayment(Order order) {
        try {
            System.out.println("Оплата заказа: " + order.getId());

            // Симуляция возможной ошибки при оплате (для демонстрации DLQ)
            if (order.getUserId() == 999) {
                throw new RuntimeException("Ошибка оплаты для пользователя 999");
            }

            order.setStatus("PAYED");

            kafkaTemplate.send(payedOrdersTopic, order.getUserId().toString(), order);

            System.out.println("Заказ " + order.getId() + " отправлен в payed_orders");

        } catch (Exception e) {
            System.err.println("Ошибка обработки заказа " + order.getId() + ": " + e.getMessage());
            throw new RuntimeException("Не удалось обработать заказ " + order.getId(), e);
        }
    }
}
