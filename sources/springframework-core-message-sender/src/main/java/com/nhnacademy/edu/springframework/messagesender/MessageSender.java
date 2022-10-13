package com.nhnacademy.edu.springframework.messagesender;

import org.springframework.stereotype.Component;

@Component
public interface MessageSender {

    void sendMessage(User user, String message);
}
