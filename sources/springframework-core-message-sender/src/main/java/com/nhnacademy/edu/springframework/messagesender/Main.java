package com.nhnacademy.edu.springframework.messagesender;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {

        String basePackage = "com.nhnacademy.edu.springframework.config";
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(basePackage);

        MessageSender emailMessageSender = MessageSender.class.cast(context.getBean("emailMessageSender"));
        MessageSender smsMessageSender = MessageSender.class.cast(context.getBean("smsMessageSender"));

        User user = new User("songs4805@naver.com", "82-10-1111-1111");
        emailMessageSender.sendMessage(user, "message");
        smsMessageSender.sendMessage(user, "message");
    }
}
