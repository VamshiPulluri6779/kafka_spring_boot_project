package com.kafka.kafkaExample.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.transaction.KafkaTransactionManager;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootStrapServer;

    public Map<String, Object> producerConfig(){
        Map<String, Object> props = new HashMap<>();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put("enable.idempotence", "true"); // Enables idempotence
        props.put(ProducerConfig.RETRIES_CONFIG, 3); // This is number of retries if sending a msg fails
        props.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 1000); // This is delay between retries
        props.put(ProducerConfig.LINGER_MS_CONFIG, 5);   // This is delay for batching

        // Since my key is string, I am using a string serializer and assuming value is also string
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "transactional-id"); // This is used to create a transaction id for producer

        return props;
    }

    /*
         * Here the second value in generic of ProducerFactory and KafkaTemplate is our custom one like Notification object
         * or some structure we want to put in Kafka

          *This will allow to create Kafka producer
     */
    @Bean
    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    /*
         * Below is the way to send message using KafkaTemplate - injecting producerFactory using dependency injection
     */
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(
            ProducerFactory<String, String> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    /*
         * This is used to create a transaction manager for Kafka producer
         * This is used to send messages in a transaction
     */
    @Bean
    public KafkaTransactionManager<String, String> kafkaTransactionManager(ProducerFactory<String, String> producerFactory) {
        return new KafkaTransactionManager<>(producerFactory);
    }
}
