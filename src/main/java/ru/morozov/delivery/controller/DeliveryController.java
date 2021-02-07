package ru.morozov.delivery.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.morozov.delivery.dto.DeliveryDto;
import ru.morozov.delivery.exceptions.NotFoundException;
import ru.morozov.delivery.service.DeliveryService;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class DeliveryController {

    private final DeliveryService deliveryService;

    @GetMapping("/{orderId:\\d+}")
    public ResponseEntity<DeliveryDto> info(@PathVariable("orderId") Long orderId) {
        try {
            return new ResponseEntity(
                    deliveryService.findByOrderId(orderId),
                    HttpStatus.OK
            );
        } catch (NotFoundException e) {
            log.warn(e.getMessage());
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
