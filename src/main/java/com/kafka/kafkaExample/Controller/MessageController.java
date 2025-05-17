package com.kafka.kafkaExample.Controller;

import com.kafka.kafkaExample.service.KafkaAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/api/v1/message")
public class MessageController {

    @Value("${application.topic}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaAdminService kafkaAdminService;

    // If you don't send specify the key, the messages will be sent to the same partition because of sticky partitioner
    @PostMapping("/publish/{key}")
    public String publishMessage(@PathVariable("key") String key,
                                 @RequestBody MessageRequest requestBody) {

        // Create a thread pool; the size can be tuned based on load.
        ExecutorService executor = Executors.newFixedThreadPool(5);

        // Multithreaded kafka producer to send messages concurrently - horizontal scaling of producer
        executor.submit(() -> {
            // This will enable atomicity, either all operations go through or none
            kafkaTemplate.executeInTransaction(operations -> {
                // This will handle the retry mechanism and error handling
                operations.send(topic, key, requestBody.message())
                        .whenComplete((result,ex) -> System.out.println("Message sent successfully: " + requestBody.message()))
                        .exceptionally(ex -> {
                            System.err.println("Failed to send message: " + ex.getMessage());
                            return null;
                        });
                return true; // dummy value
            });
        });


        // Topic creation using kafka's admin client
        kafkaAdminService.createTopic("new_topic", 1, (short) 1);
        return "Message published successfully!";
    }

    @GetMapping("/getTopicDetails")
    public String getTopicDetails() throws ExecutionException, InterruptedException {
        return kafkaAdminService.getTopicDetails();
    }
}
