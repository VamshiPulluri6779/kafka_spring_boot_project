package com.kafka.kafkaExample;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class KafkaListenerClass {

    @Value("${application.topic}")
    public String topic;

    // Group is nothing but consumer group
    @KafkaListener(
            topics = "#{__listener.topic}",
            groupId = "my-consumer-group",
            containerFactory = "listenerContainerFactory"
    )
    void listener(String data, Acknowledgment acknowledgment){

        System.out.println("Received message: " + data);
        // Process the message (simulate processing)
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        // Once processing is complete, commit the offset explicitly.
        acknowledgment.acknowledge();
        System.out.println("Offset committed.");
    }
}
