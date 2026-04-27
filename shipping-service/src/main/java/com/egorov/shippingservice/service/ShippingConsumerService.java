package com.egorov.shippingservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.egorov.commonmodel.dto.Order;

@Service
public class ShippingConsumerService {

    @Autowired
    private KafkaTemplate<String, Order> kafkaTemplate;

    @Value("${kafka.topic.sent-orders:sent_orders}")
    private String sentOrdersTopic;

    @KafkaListener(topics = "payed_orders", groupId = "shipping-group", concurrency = "3")
    public void processShipping(Order order) {
        try {
            System.out.println("📦 Отгрузка заказа: " + order.getId());

            // Симуляция возможной ошибки при отгрузке (для демонстрации DLQ)
            if (order.getQuantity() > 100) {
                throw new RuntimeException("Ошибка отгрузки: слишком большое количество товара");
            }

            order.setStatus("SENT");
            kafkaTemplate.send(sentOrdersTopic, order.getUserId().toString(), order);

            System.out.println("✅ Заказ " + order.getId() + " отправлен в sent_orders");

        } catch (Exception e) {
            System.err.println("❌ Ошибка отгрузки заказа " + order.getId() + ": " + e.getMessage());
            throw new RuntimeException("Не удалось обработать отгрузку заказа " + order.getId(), e);
        }
    }
}
