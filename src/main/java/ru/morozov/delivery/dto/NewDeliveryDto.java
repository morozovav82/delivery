package ru.morozov.delivery.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewDeliveryDto {
    private Long orderId;
    private String deliveryDetails;
    private String status;
}
