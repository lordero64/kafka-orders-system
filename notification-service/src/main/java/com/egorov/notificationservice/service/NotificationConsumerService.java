package com.egorov.notificationservice.service;

import com.egorov.commonmodel.dto.Order;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class NotificationConsumerService {
    private static final Logger log = LoggerFactory.getLogger(NotificationConsumerService.class);

    @KafkaListener(topics = "sent_orders", groupId = "notification-group", concurrency = "3")
    public void sendNotification(Order order) {
        log.info("📧 Отправка уведомления пользователю {}", order.getUserId());
        log.info("   Заказ {} успешно доставлен.", order.getId());
        log.info("   Статус заказа: {}", order.getStatus());


        System.out.println("📧 Уведомление: Ваш заказ " + order.getId() + " доставлен!");
    }
}