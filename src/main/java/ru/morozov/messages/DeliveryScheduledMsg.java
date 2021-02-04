package ru.morozov.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class DeliveryScheduledMsg implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long orderId;
}
