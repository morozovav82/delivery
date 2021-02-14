package ru.morozov.delivery.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.morozov.delivery.entity.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySentIsNullOrderById();
}
