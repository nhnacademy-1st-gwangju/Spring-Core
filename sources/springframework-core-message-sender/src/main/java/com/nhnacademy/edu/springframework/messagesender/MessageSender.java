package com.nhnacademy.edu.springframework.messagesender;

import org.springframework.stereotype.Component;

@Component
public interface MessageSender {

    boolean sendMessage(User user, String message);
}
