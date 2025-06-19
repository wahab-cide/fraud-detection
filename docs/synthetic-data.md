# Synthetic Data Generation

## Overview

Generating synthetic payment data is essential for testing the fraud detection pipeline at scale. The original documentation referenced `tools/generator.py`, but this file is not present in the current repository.

## Alternatives for Data Generation

### 1. Built-in PaymentSource (Java)
- The default `PaymentSource.java` generates random payment events (amounts between $100 and $5000, 1 per second).
- **Usage:** This is automatically used when running the Flink job locally.

### 2. Custom Python/Script Generator
- You can write a simple Python script to produce payment events to Kafka.
- Example (using `kafka-python`):
  ```python
  from kafka import KafkaProducer
  import json, random, time

  producer = KafkaProducer(bootstrap_servers='localhost:9092', value_serializer=lambda v: json.dumps(v).encode('utf-8'))
  while True:
      payment = {'amount': random.uniform(100, 5000), 'ts': int(time.time() * 1000)}
      producer.send('payments.raw', payment)
      time.sleep(0.1)  # 10 events/sec
  ```
- Adjust the topic, rate, and fields as needed.

### 3. Use Open-Source Tools
- Tools like [Mockaroo](https://mockaroo.com/) or [Faker](https://faker.readthedocs.io/) can generate CSV/JSON data for batch ingestion.

## Integration
- For real-time testing, ensure your generator writes to the same Kafka topic (`payments.raw`) consumed by the Flink job.
- For batch testing, you can use Flink's file source to ingest static datasets.

## See Also
- [code-structure.md](code-structure.md)
- [architecture.md](architecture.md) 