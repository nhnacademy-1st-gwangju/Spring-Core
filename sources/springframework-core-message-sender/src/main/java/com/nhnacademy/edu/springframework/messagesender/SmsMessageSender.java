package com.nhnacademy.edu.springframework.messagesender;

public class SmsMessageSender implements MessageSender {

    @Override
    public void sendMessage(User user, String message) {
        System.out.printf("SMS Message Sent to %s : %s\n", user.getPhoneNumber(), message);
    }
}
