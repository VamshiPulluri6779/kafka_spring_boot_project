package com.kafka.kafkaExample.config;

import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.Map;

@Configuration
public class KafkaAdminClientConfig {

    @Bean
    public AdminClient adminClient(KafkaAdmin kafkaAdmin){
        Map<String, Object> configs = kafkaAdmin.getConfigurationProperties(); // uses bootstrap servers and other configs
        return AdminClient.create(configs);
    }
}
