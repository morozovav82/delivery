package ru.morozov.delivery.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqConfig {

    @Value("${active-mq.SagaScheduleDelivery-topic}")
    private String sagaScheduleDeliveryTopic;

    @Value("${active-mq.SagaScheduleDeliveryRollback-topic}")
    private String sagaScheduleDeliveryRollbackTopic;

    @Value("${active-mq.OrderDone-exchange}")
    private String orderDoneExchange;

    @Value("${active-mq.DeliveryDone-topic}")
    private String deliveryDoneTopic;

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
    public Queue deliveryDoneQueue() {
        return new Queue(deliveryDoneTopic);
    }

    @Bean
    public Queue deliveryScheduledQueue() {
        return new Queue(deliveryScheduledTopic);
    }

    @Bean
    public Queue deliveryRejectedQueue() {
        return new Queue(deliveryRejectedTopic);
    }

    @Bean
    TopicExchange orderDoneExchange() {
        return new TopicExchange(orderDoneExchange);
    }

    @Bean
    Binding binding(@Qualifier("deliveryDoneQueue") Queue queue, @Qualifier("orderDoneExchange") TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("default");
    }
}
