package ru.morozov.delivery.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;
import ru.morozov.messages.SagaScheduleDeliveryMsg;
import ru.morozov.messages.SagaScheduleDeliveryRollbackMsg;

@RestController
@RequestMapping("/tests")
@RequiredArgsConstructor
@Slf4j
public class TestController {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${active-mq.SagaScheduleDelivery-topic}")
    private String sagaScheduleDeliveryTopic;

    @Value("${active-mq.SagaScheduleDeliveryRollback-topic}")
    private String sagaScheduleDeliveryRollbackTopic;

    private void sendMessage(String topic, Object message){
        try{
            log.info("Attempting send message to Topic: "+ topic);
            jmsTemplate.convertAndSend(topic, message);
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
}
