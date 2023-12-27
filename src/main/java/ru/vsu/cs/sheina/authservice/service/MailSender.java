package ru.vsu.cs.sheina.authservice.service;

import lombok.RequiredArgsConstructor;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.vsu.cs.sheina.authservice.configuration.RabbitQueues;
import ru.vsu.cs.sheina.authservice.dto.MessageDTO;
import ru.vsu.cs.sheina.authservice.dto.enums.TypeLetter;

@Service
@RequiredArgsConstructor
public class MailSender {

    @Value("${link.activation}")
    private String activateLink;

    @Value("${link.email}")
    private String activateEmailLink;

    private final RabbitTemplate rabbitTemplate;

//    public void sendActivateMessage(String email) {
//        MessageDTO messageDTO = new MessageDTO(activateLink, email, TypeLetter.ACTIVATE_ACCOUNT);
//        rabbitTemplate.convertAndSend(RabbitQueues.toMailSender, messageDTO);
//    }
//
//    public void sendConfirmEmailMessage(String email) {
//        MessageDTO messageDTO = new MessageDTO(activateEmailLink, email, TypeLetter.CONFIRM_EMAIL);
//        rabbitTemplate.convertAndSend(RabbitQueues.toMailSender, messageDTO);
//    }
//
//    public void sendNewPassMessage(String email, String newPass) {
//        MessageDTO messageDTO = new MessageDTO(newPass, email, TypeLetter.FORGET_PASSWORD);
//        rabbitTemplate.convertAndSend(RabbitQueues.toMailSender, messageDTO);
//    }
//
//    public void sendConfirmNewPassMessage(String email, String code) {
//        MessageDTO messageDTO = new MessageDTO(code, email, TypeLetter.CHANGE_PASSWORD);
//        rabbitTemplate.convertAndSend(RabbitQueues.toMailSender, messageDTO);
//    }
}
