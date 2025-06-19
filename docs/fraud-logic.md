# Fraud Detection Logic

## Default Pattern

The pipeline uses **Apache Flink CEP** (Complex Event Processing) to detect suspicious payment activity in real time.

### Out-of-the-Box Pattern
- **Rule:** Trigger an alert if a user makes **3 high-value payments (> $2000)** within **5 minutes**.
- **Implementation:**
  - Defined in `FraudCEP.java` using Flink's CEP API:
    ```java
    Pattern<Payment, ?> highValueThree = Pattern.<Payment>begin("first")
        .where(payment -> payment.getAmount() > 2000)
        .times(3)
        .within(Time.minutes(5));
    ```
  - When the pattern matches, the first payment in the sequence is sent to the alert sink.

## How It Works
1. **Event Stream:** Payments are ingested and assigned timestamps/watermarks.
2. **Pattern Matching:** CEP engine looks for 3 qualifying payments in a 5-minute window.
3. **Alerting:** On match, an alert is emitted (to stdout or DynamoDB, depending on sink).

## Customizing Patterns
- **Change Thresholds:** Adjust the amount or count in `FraudCEP.java`.
- **Add More Rules:**
  - Use Flink CEP to define more complex patterns (e.g., different users, time windows, or event types).
  - Example: Detect 5 payments from different locations in 10 minutes.
- **Dynamic Rules:**
  - Use Flink's broadcast state to update rules at runtime (see Flink docs for advanced usage).

## Example: Custom Pattern
```java
Pattern<Payment, ?> rapidFire = Pattern.<Payment>begin("first")
    .where(payment -> payment.getAmount() > 1000)
    .times(5)
    .within(Time.minutes(2));
```

## References
- [Flink CEP Documentation](https://nightlies.apache.org/flink/flink-docs-release-1.20/docs/libs/cep/)
- [architecture.md](architecture.md)
- [code-structure.md](code-structure.md) 