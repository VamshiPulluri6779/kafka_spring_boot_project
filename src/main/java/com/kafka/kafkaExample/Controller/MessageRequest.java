package com.kafka.kafkaExample.Controller;

public record MessageRequest(String message) {
    // This is a record class in Java, which is a special kind of class that is used to hold data.
    // It automatically generates getters, equals, hashCode, and toString methods.
    // The 'message' field will hold the message that we want to send to Kafka.
}
