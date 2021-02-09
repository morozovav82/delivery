package ru.morozov.delivery.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.morozov.delivery.service.DeliveryService;
import ru.morozov.messages.SagaScheduleDeliveryMsg;

@Component
@Slf4j
@RequiredArgsConstructor
@RabbitListener(queues = "${active-mq.SagaScheduleDelivery-topic}")
public class SagaScheduleDeliveryConsumer {

    private final DeliveryService deliveryService;

    @RabbitHandler
    public void receive(SagaScheduleDeliveryMsg msg) {
        log.info("Received Message: {}", msg.toString());
        try {
            deliveryService.schedule(msg.getOrderId(), msg.getDeliveryInfo());
        } catch (Exception e) {
            log.error("Failed to save delivery", e);
        }
    }
}
