package com.nhnacademy.edu.springframework.messagesender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class MessageSendService {

    @Value("${name}")
    private String name;

    private final MessageSender messageSender;

    @Autowired
    public MessageSendService(@Sms MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void doSendMessage() {
        System.out.println("From: " + name);
        messageSender.sendMessage(new User("songs4805@naver.com", "82-10-1111-1111"), "message");
    }
}
