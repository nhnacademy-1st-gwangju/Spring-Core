package com.nhnacademy.edu.springframework.messagesender;

public class EmailMessageSender implements MessageSender {

    public EmailMessageSender() {
        System.out.println("EmailMessageSender 생성");
    }

    @Override
    public void sendMessage(User user, String message) {
        System.out.printf("Email Message Sent %s : %s\n", user.getEmail(), message);
    }
}
