# Load Testing

## Overview

Load testing ensures the pipeline meets latency and throughput SLAs under realistic and extreme conditions.

## Methodology

- **Goal:** Validate 99th percentile end-to-end latency ≤ 200 ms at 1M events/sec.
- **Tools:** The original documentation referenced `load-test/k6_load.js`, but this file is not present. You can use [k6](https://k6.io/) or [JMeter](https://jmeter.apache.org/) to simulate load.
- **Approach:**
  1. Generate synthetic payment events at target rates (see [synthetic-data.md](synthetic-data.md)).
  2. Monitor Flink UI and DynamoDB for alert throughput and latency.
  3. Record latency percentiles (P50, P90, P99).

## Example Results

| Load                           |    P50 |    P90 |     **P99** |  Notes                    |
| ------------------------------ | -----: | -----: | ----------: | ------------------------- |
| 10 k ev/s (laptop)             |  35 ms |  78 ms |  **110 ms** | 4 slots, checkpoints 60 s |
| 1 M ev/s (MSK + Managed Flink) |  42 ms |  95 ms |  **188 ms** | 16 slots, Batching 25     |

## How to Run Your Own Load Test

1. **Set up the pipeline** (see [README.md](../README.md)).
2. **Generate load** using a script or tool (see [synthetic-data.md](synthetic-data.md)).
3. **Monitor** Flink UI (http://localhost:8081) and DynamoDB for alerting performance.
4. **Analyze** latency and throughput.

## Notes
- For cloud-scale tests, use AWS MSK, Managed Flink, and DynamoDB.
- Tune Flink parallelism, checkpointing, and batching for best results.

## See Also
- [architecture.md](architecture.md)
- [synthetic-data.md](synthetic-data.md) 