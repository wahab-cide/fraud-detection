# FAQ & Troubleshooting

## General

**Q: I see references to tools/generator.py, infra/, or load-test/ but these folders are missing.**
- A: The current repository does not include these files. See [synthetic-data.md](synthetic-data.md) for alternatives to generator.py, and [load-testing.md](load-testing.md) for load testing tips.

**Q: How do I run the pipeline locally?**
- A: Follow the Quick Start in [README.md](../README.md). Use Docker Compose to spin up Kafka, Flink, and DynamoDB-Local.

**Q: How do I deploy to AWS?**
- A: Package your JAR and deploy to AWS Managed Flink, MSK, and DynamoDB. See [architecture.md](architecture.md) for details. Infrastructure-as-code (Terraform) is not included in this repo.

## Flink & Kafka

**Q: Flink job fails to connect to Kafka.**
- A: Ensure Kafka is running (`docker compose ps`). Check broker address and ports.

**Q: Flink UI is not accessible.**
- A: Visit http://localhost:8081. If unavailable, check Docker Compose logs for errors.

## Data & Alerts

**Q: No alerts are being generated.**
- A: Ensure payment events are flowing (see Flink UI). Check that the pattern in `FraudCEP.java` matches your test data.

**Q: How do I change the fraud detection logic?**
- A: Edit the CEP pattern in `FraudCEP.java`. See [fraud-logic.md](fraud-logic.md).

## Performance

**Q: How do I improve throughput or reduce latency?**
- A: Increase Flink parallelism, tune checkpointing intervals, and batch sizes. See [load-testing.md](load-testing.md).

## Contributions

**Q: How can I contribute or extend the project?**
- A: Fork the repo, open issues/PRs, and see [code-structure.md](code-structure.md) for extension points. 