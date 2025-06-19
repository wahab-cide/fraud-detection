# Code Structure

## Directory Layout

```
src/main/java/com/fraud/
├── FraudCEP.java      # Main Flink job (entry point)
├── PaymentSource.java # Source: generates or ingests payment events
├── Payment.java       # Payment event model
└── AlertSink.java     # Sink: handles fraud alerts (to DynamoDB or stdout)
```

## Main Classes

### FraudCEP.java
- **Purpose:** Entry point for the Flink job.
- **Responsibilities:**
  - Sets up the Flink execution environment
  - Defines the payment event stream
  - Applies a CEP pattern: detects 3 high-value payments (> $2000) within 5 minutes
  - Sends detected fraud events to the alert sink

### PaymentSource.java
- **Purpose:** Provides payment events to the pipeline
- **Default:** Generates random payments (for demo/testing)
- **Production:** Replace with a Kafka source for real data ingestion

### Payment.java
- **Purpose:** Data model for a payment event
- **Fields:**
  - `amount` (double): Payment amount
  - `ts` (long): Timestamp (epoch ms)

### AlertSink.java
- **Purpose:** Handles fraud alerts
- **Default:** Prints alerts to stdout (for demo/testing)
- **Production:** Replace with a DynamoDB sink for persistent alerting

## Extension Points

- **Fraud Patterns:**
  - Modify or add CEP patterns in `FraudCEP.java` to detect different fraud scenarios
- **Sources:**
  - Swap `PaymentSource` for a Kafka or other real-time source
- **Sinks:**
  - Replace `AlertSink` with integrations to DynamoDB, Slack, SNS, etc.
- **State Management:**
  - Configure RocksDB/S3 for stateful processing and checkpointing

## See Also
- [fraud-logic.md](fraud-logic.md)
- [architecture.md](architecture.md) 