package ru.vsu.cs.sheina.authservice.configuration;

public class RabbitQueues {
    public static final String toMailSender = "FROM_AUTH_TO_MAIL_QUEUE";

    public static final String toSocialService = "FROM_AUTH_TO_SOCIAL_QUEUE";
    public static final String toBlogService = "FROM_AUTH_TO_BLOG_QUEUE";
}
