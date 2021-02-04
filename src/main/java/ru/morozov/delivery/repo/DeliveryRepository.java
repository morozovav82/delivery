package ru.morozov.delivery.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.morozov.delivery.entity.Delivery;

import java.util.List;
import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    Optional<Delivery> findOneByOrderIdAndStatus(Long orderId, String status);
    List<Delivery> findByStatus(String status);
}
