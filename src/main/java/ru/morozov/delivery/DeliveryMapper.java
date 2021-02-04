package ru.morozov.delivery;

import ru.morozov.delivery.dto.NewDeliveryDto;
import ru.morozov.delivery.dto.DeliveryDto;
import ru.morozov.delivery.entity.Delivery;

public class DeliveryMapper {

    public static DeliveryDto convertDeliveryToDeliveryDto(Delivery delivery) {
        DeliveryDto deliveryDto = new DeliveryDto();
        deliveryDto.setId(delivery.getId());
        deliveryDto.setOrderId(delivery.getOrderId());
        deliveryDto.setDeliveryDetails(delivery.getDeliveryDetails());
        deliveryDto.setStatus(delivery.getStatus());

        return deliveryDto;
    }

    public static Delivery convertNewDeliveryDtoToDelivery(NewDeliveryDto newDeliveryDto) {
        Delivery delivery = new Delivery();
        delivery.setOrderId(newDeliveryDto.getOrderId());
        delivery.setDeliveryDetails(newDeliveryDto.getDeliveryDetails());
        delivery.setStatus(newDeliveryDto.getStatus());

        return delivery;
    }

    public static Delivery convertDeliveryDtoToDelivery(DeliveryDto deliveryDto) {
        Delivery delivery = convertNewDeliveryDtoToDelivery(deliveryDto);
        delivery.setId(deliveryDto.getId());

        return delivery;
    }
}
