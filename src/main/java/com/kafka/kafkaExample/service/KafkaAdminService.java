package com.kafka.kafkaExample.service;

import org.apache.kafka.clients.admin.AdminClient;

import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class KafkaAdminService {

    @Autowired
    private AdminClient adminClient;

    public void createTopic(String topicName, int partitions, short replicationFactor) {
        // Logic to create a topic using adminClient
        adminClient.createTopics(Collections.singletonList(new NewTopic(topicName, partitions, replicationFactor)));
    }

    public String getTopicDetails() throws ExecutionException, InterruptedException {
        DescribeTopicsResult result = adminClient.describeTopics(List.of("kafkaTopic"));
        return result.allTopicNames().get().toString();
    }
}
