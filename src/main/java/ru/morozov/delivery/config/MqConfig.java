package ru.morozov.delivery.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqConfig {

    @Value("${active-mq.SagaScheduleDelivery-topic}")
    private String sagaScheduleDeliveryTopic;

    @Value("${active-mq.SagaScheduleDeliveryRollback-topic}")
    private String sagaScheduleDeliveryRollbackTopic;

    @Value("${active-mq.OrderDone-topic}")
    private String orderDoneTopic;

    @Value("${active-mq.DeliveryScheduled-topic}")
    private String deliveryScheduledTopic;

    @Value("${active-mq.DeliveryRejected-topic}")
    private String deliveryRejectedTopic;

    @Bean
    public Queue sagaScheduleDeliveryQueue() {
        return new Queue(sagaScheduleDeliveryTopic);
    }

    @Bean
    public Queue sagaScheduleDeliveryRollbackQueue() {
        return new Queue(sagaScheduleDeliveryRollbackTopic);
    }

    @Bean
    public Queue orderDoneQueue() {
        return new Queue(orderDoneTopic);
    }

    @Bean
    public Queue deliveryScheduledQueue() {
        return new Queue(deliveryScheduledTopic);
    }

    @Bean
    public Queue deliveryRejectedQueue() {
        return new Queue(deliveryRejectedTopic);
    }
}
