package ru.vsu.cs.sheina.authservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.vsu.cs.sheina.authservice.configuration.RabbitQueues;
import ru.vsu.cs.sheina.authservice.dto.field.IdDTO;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BlogSender {

    private final RabbitTemplate rabbitTemplate;

    public void sendCreateUserRequestBlog(UUID id) {
        IdDTO idDTO = new IdDTO(id);
        rabbitTemplate.convertAndSend(RabbitQueues.toBlogService, idDTO);
    }
}
