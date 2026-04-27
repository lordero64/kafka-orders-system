package com.egorov.paymentservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${kafka.partitions:3}")
    private int partitions;

    @Bean
    public NewTopic newOrdersTopic() {
        return TopicBuilder.name("payed_orders")
                .partitions(partitions)
                .replicas(1)
                .build();

    }
}
