package com.nhnacademy.edu.springframework.messagesender;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.nhnacademy.edu.springframework.config");

        MessageSendService messageSendService = context.getBean("messageSendService", MessageSendService.class);
        messageSendService.doSendMessage();
    }
}
