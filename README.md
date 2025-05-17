# Kafka Basics

### 1. **Set Log4j Configuration**
In every terminal you open for running Kafka broker, consumer, producer, or any Kafka-related command, execute the following command to ensure the file path is recognized by Kafka:

```bash
export KAFKA_LOG4J_OPTS="-Dlog4j.configurationFile=file:///C:/kafka_2.13-4.0.0/config/tools-log4j2.yaml"
```

---

### 2. **Generate a Random Cluster ID**
Run the following command to generate a random cluster ID:

```bash
KAFKA_CLUSTER_ID="$(bin/kafka-storage.sh random-uuid)"
```

Verify the generated cluster ID:

```bash
echo $KAFKA_CLUSTER_ID
```

---

### 3. **Format Log Directories**
Format the log directories using the generated cluster ID:

```bash
bin/kafka-storage.sh format --standalone -t $KAFKA_CLUSTER_ID -c config/server.properties
```

> **Note:** If you encounter any errors during this step, delete the `kraft-combined-logs` folder under `C:\tmp` and rerun the command.

---

### 4. **Start Kafka Server/Broker**
Start the Kafka server with KRaft on port 9092:

```bash
bin/kafka-server-start.sh config/server.properties
```

---

### 5. **Create a Topic**
Create a topic to store messages:

```bash
bin/kafka-topics.sh --create --topic newCustomTopic --bootstrap-server localhost:9092
```

---

### 6. **Create a Producer**
Start a producer to send messages to the topic:

```bash
bin/kafka-console-producer.sh --topic newCustomTopic --bootstrap-server localhost:9092
```

---

### 7. **Create a Consumer**
Start a consumer to read messages from the topic:

```bash
bin/kafka-console-consumer.sh --topic quickstart-events --from-beginning --bootstrap-server localhost:9092
```

---

### 8. **Describe Consumer Group Partitions**
Use the following command to describe the partitions assigned to consumers in the same consumer group:

```bash
bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group my-consumer-group
```

---

### Notes
- In the producer console, any messages you type will be visible in the consumer console.
- Ensure you run the `export` command in every terminal to recognize the file path.
- Follow the topics and sub-topics of this [Udemy course](https://www.udemy.com/course/apache-kafka-for-beginners) for a deeper understanding of Kafka.
