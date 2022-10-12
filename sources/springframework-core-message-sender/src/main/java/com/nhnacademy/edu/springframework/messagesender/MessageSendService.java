package com.nhnacademy.edu.springframework.messagesender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class MessageSendService {

    private final MessageSender messageSender;
    private final String name;

    @Autowired
    public MessageSendService(@Sms MessageSender messageSender,
                              @Value("${name}") String name) {
        this.messageSender = messageSender;
        this.name = name;
    }

    public void doSendMessage() {
        System.out.println("From: " + name);
        messageSender.sendMessage(new User("songs4805@naver.com", "82-10-1111-1111"), "message");
    }
}
