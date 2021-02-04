package ru.morozov.delivery.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DeliveryDto extends NewDeliveryDto {
    private Long id;
}
