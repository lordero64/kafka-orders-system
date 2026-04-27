package com.egorov.shippingservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${kafka.partitions.sent-orders:3}")
    private int partitions;

    @Value("${kafka.replicas:1}")
    private short replicas;

    @Bean
    public NewTopic sentOrdersTopic() {
        return TopicBuilder.name("sent_orders")
                .partitions(partitions)
                .replicas(replicas)
                .build();
    }
}
