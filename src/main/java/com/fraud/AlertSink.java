package com.fraud;

import org.apache.flink.streaming.api.functions.sink.SinkFunction;

public class AlertSink implements SinkFunction<Payment> {
    
    @Override
    public void invoke(Payment payment, Context context) throws Exception {
        System.out.println("🚨 FRAUD ALERT: High-value payment detected!");
        System.out.println("   Amount: $" + String.format("%.2f", payment.getAmount()));
        System.out.println("   Timestamp: " + payment.getTs());
        System.out.println("   Pattern: 3 payments > $2000 within 5 minutes");
        System.out.println("----------------------------------------");
    }
} 