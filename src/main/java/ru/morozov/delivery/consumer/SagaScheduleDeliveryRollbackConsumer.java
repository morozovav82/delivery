package ru.morozov.delivery.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import ru.morozov.messages.SagaScheduleDeliveryRollbackMsg;
import ru.morozov.delivery.service.DeliveryService;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

@Component
@Slf4j
@RequiredArgsConstructor
public class SagaScheduleDeliveryRollbackConsumer implements MessageListener {

    private final DeliveryService deliveryService;

    private ObjectMessage receiveMessage(Message message) {
        ObjectMessage objectMessage;

        try {
            objectMessage = (ObjectMessage) message;
            log.info("Received Message: {}", objectMessage.getObject().toString());
            return objectMessage;
        } catch (Exception e) {
            log.error("Failed to receive message", e);
            return null;
        }
    }

    @Override
    @JmsListener(destination = "${active-mq.SagaScheduleDeliveryRollback-topic}")
    public void onMessage(Message message) {
        ObjectMessage objectMessage = receiveMessage(message);
        if (objectMessage == null) return;

        try {
            SagaScheduleDeliveryRollbackMsg msg = (SagaScheduleDeliveryRollbackMsg) objectMessage.getObject();
            deliveryService.rollback(msg.getOrderId());
        } catch (Exception e) {
            log.error("Failed to save delivery", e);
        }
    }
}
