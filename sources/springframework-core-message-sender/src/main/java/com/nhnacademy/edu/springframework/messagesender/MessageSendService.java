package com.nhnacademy.edu.springframework.messagesender;

import org.springframework.beans.factory.annotation.Autowired;

public class MessageSendService {

    private final MessageSender messageSender;

    @Autowired
    public MessageSendService(@Sms MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void doSendMessage() {
        messageSender.sendMessage(new User("songs4805@naver.com", "82-10-1111-1111"), "message");
    }
}
