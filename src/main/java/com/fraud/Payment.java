package com.fraud;

public class Payment {
    private double amount;
    private long ts;

    public Payment(double amount, long ts) {
        this.amount = amount;
        this.ts = ts;
    }

    public double getAmount() {
        return amount;
    }

    public long getTs() {
        return ts;
    }

    @Override
    public String toString() {
        return "Payment{amount=" + amount + ", ts=" + ts + "}";
    }
} 