package ru.morozov.delivery.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.morozov.messages.OrderDoneMsg;
import ru.morozov.messages.SagaScheduleDeliveryMsg;
import ru.morozov.messages.SagaScheduleDeliveryRollbackMsg;

@RestController
@RequestMapping("/tests")
@RequiredArgsConstructor
@Slf4j
public class TestController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${active-mq.SagaScheduleDelivery-topic}")
    private String sagaScheduleDeliveryTopic;

    @Value("${active-mq.SagaScheduleDeliveryRollback-topic}")
    private String sagaScheduleDeliveryRollbackTopic;

    @Value("${active-mq.OrderDone-topic}")
    private String orderDoneTopic;

    private void sendMessage(String topic, Object message){
        try{
            log.info("Attempting send message to Topic: "+ topic);
            rabbitTemplate.convertAndSend(topic, message);
            log.info("Message sent: {}", message);
        } catch(Exception e){
            log.error("Failed to send message", e);
        }
    }

    @PostMapping("/sendSagaScheduleDeliveryMsg")
    public void sendSagaScheduleDeliveryMsg(@RequestBody SagaScheduleDeliveryMsg message) {
        sendMessage(sagaScheduleDeliveryTopic, message);
    }

    @PostMapping("/sendSagaScheduleDeliveryRollbackMsg")
    public void sendSagaScheduleDeliveryRollbackMsg(@RequestBody SagaScheduleDeliveryRollbackMsg message) {
        sendMessage(sagaScheduleDeliveryRollbackTopic, message);
    }

    @PostMapping("/sendOrderDoneMsg")
    public void sendOrderDoneMsg(@RequestBody OrderDoneMsg message) {
        sendMessage(orderDoneTopic, message);
    }
}
