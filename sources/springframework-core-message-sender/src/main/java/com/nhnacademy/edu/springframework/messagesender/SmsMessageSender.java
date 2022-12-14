package com.nhnacademy.edu.springframework.messagesender;

import org.springframework.stereotype.Component;

@Component
public class SmsMessageSender implements MessageSender {

    public SmsMessageSender() {
        System.out.println("SmsMessageSender 생성");
    }

    public void init() {
        System.out.println("init method called in SmsMessageSender");
    }

    @Override
    public boolean sendMessage(User user, String message) {
        System.out.printf("SMS Message Sent to %s : %s\n", user.getPhoneNumber(), message);
        return true;
    }
}
