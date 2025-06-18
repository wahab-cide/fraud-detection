package com.fraud;

import org.apache.flink.streaming.api.functions.source.SourceFunction;
import java.util.Random;

public class PaymentSource implements SourceFunction<Payment> {
    private volatile boolean isRunning = true;
    private final Random random = new Random();

    @Override
    public void run(SourceContext<Payment> ctx) throws Exception {
        while (isRunning) {
            // Generate random payment amounts between 100 and 5000
            double amount = 100 + random.nextDouble() * 4900;
            long timestamp = System.currentTimeMillis();
            
            ctx.collect(new Payment(amount, timestamp));
            
            // Sleep for 1 second between payments
            Thread.sleep(1000);
        }
    }

    @Override
    public void cancel() {
        isRunning = false;
    }
} 