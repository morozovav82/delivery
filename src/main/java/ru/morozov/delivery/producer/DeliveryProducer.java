package ru.morozov.delivery.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.morozov.delivery.service.MessageService;
import ru.morozov.messages.DeliveryRejectedMsg;
import ru.morozov.messages.DeliveryScheduledMsg;

@Service
@Slf4j
public class DeliveryProducer {

    @Autowired
    private MessageService messageService;

    @Value("${active-mq.DeliveryScheduled-topic}")
    private String deliveryScheduledTopic;

    @Value("${active-mq.DeliveryRejected-topic}")
    private String deliveryRejectedTopic;

    public void sendDeliveryScheduledMessage(DeliveryScheduledMsg message){
        messageService.scheduleSentMessage(deliveryScheduledTopic, null, message, DeliveryScheduledMsg.class);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void sendDeliveryRejectedMessage(DeliveryRejectedMsg message){
        messageService.scheduleSentMessage(deliveryRejectedTopic, null, message, DeliveryRejectedMsg.class);
    }
}