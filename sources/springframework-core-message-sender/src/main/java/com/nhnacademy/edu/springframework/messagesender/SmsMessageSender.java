package com.nhnacademy.edu.springframework.messagesender;

public class SmsMessageSender implements MessageSender {

    public SmsMessageSender() {
        System.out.println("SmsMessageSender 생성");
    }

    @Override
    public void sendMessage(User user, String message) {
        System.out.printf("SMS Message Sent to %s : %s\n", user.getPhoneNumber(), message);
    }
}
