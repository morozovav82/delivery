package ru.morozov.delivery.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "delivery")
@Getter
@Setter
public class Delivery {

    @Id
    @SequenceGenerator(name="delivery_gen", sequenceName="delivery_id_seq", allocationSize = 1)
    @GeneratedValue(strategy=SEQUENCE, generator="delivery_gen")
    private Long id;

    private Long orderId;
    private String deliveryDetails;
    private String status;
}
