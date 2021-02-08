package ru.morozov.delivery.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.morozov.delivery.DeliveryMapper;
import ru.morozov.delivery.dto.DeliveryDto;
import ru.morozov.delivery.dto.NewDeliveryDto;
import ru.morozov.delivery.entity.Delivery;
import ru.morozov.delivery.entity.Status;
import ru.morozov.delivery.exceptions.NotFoundException;
import ru.morozov.delivery.producer.DeliveryProducer;
import ru.morozov.delivery.repo.DeliveryRepository;
import ru.morozov.messages.DeliveryRejectedMsg;
import ru.morozov.messages.DeliveryScheduledMsg;

import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryProducer deliveryProducer;

    public DeliveryDto findByOrderId(Long orderId) {
        Optional<Delivery> res = deliveryRepository.findOneByOrderId(orderId);
        if (res.isPresent()) {
            Delivery delivery = res.get();
            return DeliveryMapper.convertDeliveryToDeliveryDto(delivery);
        } else {
            throw new NotFoundException(orderId);
        }
    }

    public void schedule(Long orderId, String deliveryInfo) {
        Assert.notNull(orderId, "OrderId is null");
        Assert.hasText(deliveryInfo, "DeliveryInfo is empty");

        if (deliveryInfo.toLowerCase().contains("воскресенье")) {
            deliveryProducer.sendDeliveryRejectedMessage(new DeliveryRejectedMsg(orderId));
            throw new IllegalArgumentException("По воскресеньям доставка не осуществляется. OrderId=" + orderId);
        }

        Optional<Delivery> res = deliveryRepository.findOneByOrderIdAndStatus(orderId, Status.ACTIVE.name());
        if (!res.isPresent()) {
            NewDeliveryDto delivery = new NewDeliveryDto();
            delivery.setOrderId(orderId);
            delivery.setDeliveryDetails(deliveryInfo);
            delivery.setStatus(Status.ACTIVE.name());

            deliveryRepository.save(DeliveryMapper.convertNewDeliveryDtoToDelivery(delivery));
            deliveryProducer.sendDeliveryScheduledMessage(new DeliveryScheduledMsg(orderId));
        } else {
            deliveryProducer.sendDeliveryRejectedMessage(new DeliveryRejectedMsg(orderId));
            throw new RuntimeException("Active delivery already exists. OrderId=" + orderId);
        }
    }

    public void rollback(Long orderId) {
        Assert.notNull(orderId, "OrderId is null");

        Optional<Delivery> res = deliveryRepository.findOneByOrderIdAndStatus(orderId, Status.ACTIVE.name());
        if (res.isPresent()) {
            Delivery delivery = res.get();
            delivery.setStatus(Status.CANCELED.name());

            deliveryRepository.save(delivery);
        } else {
            throw new NotFoundException(orderId);
        }
    }

    public void done(Long orderId) {
        Assert.notNull(orderId, "OrderId is null");

        Optional<Delivery> res = deliveryRepository.findOneByOrderIdAndStatus(orderId, Status.ACTIVE.name());
        if (res.isPresent()) {
            Delivery delivery = res.get();
            delivery.setStatus(Status.DONE.name());

            deliveryRepository.save(delivery);
        } else {
            throw new NotFoundException(orderId);
        }
    }
}
