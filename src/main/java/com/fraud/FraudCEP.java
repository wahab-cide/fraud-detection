package com.fraud;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.cep.CEP;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.cep.pattern.conditions.IterativeCondition;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.cep.functions.PatternProcessFunction;
import org.apache.flink.util.Collector;

import java.util.List;
import java.util.Map;

public class FraudCEP {

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<Payment> payments = env
            .addSource(new PaymentSource())          
            .assignTimestampsAndWatermarks(
                WatermarkStrategy.<Payment>forMonotonousTimestamps()
                    .withTimestampAssigner((p, ts) -> p.getTs())
            );

        Pattern<Payment, ?> highValueThree = Pattern.<Payment>begin("first")
            .where(new IterativeCondition<Payment>() {
                @Override
                public boolean filter(Payment payment, Context<Payment> context) throws Exception {
                    return payment.getAmount() > 2000;
                }
            })
            .times(3)
            .within(org.apache.flink.streaming.api.windowing.time.Time.minutes(5));

        DataStream<Payment> alerts = CEP.pattern(payments, highValueThree)
            .process(new PatternProcessFunction<Payment, Payment>() {
                @Override
                public void processMatch(Map<String, List<Payment>> match, Context ctx, Collector<Payment> out) throws Exception {
                    // Get the first payment from the pattern match
                    Payment firstPayment = match.get("first").get(0);
                    out.collect(firstPayment);
                }
            });

        alerts.addSink(new AlertSink());            

        env.execute("Fraud CEP Demo");
    }
}
