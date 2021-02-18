package ru.morozov.delivery.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.morozov.delivery.service.MessageService;
import ru.morozov.messages.OrderDoneMsg;
import ru.morozov.messages.SagaScheduleDeliveryMsg;
import ru.morozov.messages.SagaScheduleDeliveryRollbackMsg;

@RestController
@RequestMapping("/tests")
@RequiredArgsConstructor
@Slf4j
public class TestController {

    @Autowired
    private MessageService messageService;

    @Value("${mq.SagaScheduleDelivery-topic}")
    private String sagaScheduleDeliveryTopic;

    @Value("${mq.SagaScheduleDeliveryRollback-topic}")
    private String sagaScheduleDeliveryRollbackTopic;

    @Value("${mq.DeliveryDone-topic}")
    private String deliveryDoneTopic;

    @PostMapping("/sendSagaScheduleDeliveryMsg")
    public void sendSagaScheduleDeliveryMsg(@RequestBody SagaScheduleDeliveryMsg message) {
        messageService.scheduleSentMessage(sagaScheduleDeliveryTopic, null, message, SagaScheduleDeliveryMsg.class);
    }

    @PostMapping("/sendSagaScheduleDeliveryRollbackMsg")
    public void sendSagaScheduleDeliveryRollbackMsg(@RequestBody SagaScheduleDeliveryRollbackMsg message) {
        messageService.scheduleSentMessage(sagaScheduleDeliveryRollbackTopic, null, message, SagaScheduleDeliveryRollbackMsg.class);
    }

    @PostMapping("/sendDeliveryDoneMsg")
    public void sendDeliveryDoneMsg(@RequestBody OrderDoneMsg message) {
        messageService.scheduleSentMessage(deliveryDoneTopic, null, message, OrderDoneMsg.class);
    }
}
