package ru.morozov.delivery.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.morozov.messages.DeliveryRejectedMsg;
import ru.morozov.messages.DeliveryScheduledMsg;

@Component
@Slf4j
public class DeliveryProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${active-mq.DeliveryScheduled-topic}")
    private String deliveryScheduledTopic;

    @Value("${active-mq.DeliveryRejected-topic}")
    private String deliveryRejectedTopic;

    private void sendMessage(String topic, Object message){
        try{
            log.info("Attempting send message to Topic: "+ topic);
            rabbitTemplate.convertAndSend(topic, message);
            log.info("Message sent: {}", message);
        } catch(Exception e){
            log.error("Failed to send message", e);
        }
    }

    public void sendDeliveryScheduledMessage(DeliveryScheduledMsg message){
        sendMessage(deliveryScheduledTopic, message);
    }

    public void sendDeliveryRejectedMessage(DeliveryRejectedMsg message){
        sendMessage(deliveryRejectedTopic, message);
    }
}