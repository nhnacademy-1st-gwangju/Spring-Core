package com.nhnacademy.edu.springframework.messagesender;

public class MessageSendService {

    private MessageSender messageSender;

    public MessageSendService(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void doSendMessage() {
        messageSender.sendMessage(new User("songs4805@naver.com", "82-10-1111-1111"), "message");
    }
}
