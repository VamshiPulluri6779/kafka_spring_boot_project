package com.kafka.kafkaExample.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${application.topic}")
    private String topic;

    @Bean
    public NewTopic newTopic() {
        return TopicBuilder.name(topic).partitions(1)
                                        .replicas(2)
                                        .config("min.insync.replicas", "2")
                                        .build();
    }
}
