package ru.vsu.cs.sheina.authservice.configuration;

public enum RabbitQueues {
    MAIL_SENDER_QUEUE("MAIL_SENDER_QUEUE");

    private String name;

    RabbitQueues(String name) {
        this.name = name;
    }
}
