package com.nhnacademy.edu.springframework.messagesender;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        User user = new User("songs4805@naver.com", "82-10-1111-1111");

        context.getBean("emailMessageSender", EmailMessageSender.class).sendMessage(user, "message");
        context.getBean("smsMessageSender", SmsMessageSender.class).sendMessage(user, "message");

        context.getBean("emailMessageSender", EmailMessageSender.class).sendMessage(user, "message");
        context.getBean("smsMessageSender", SmsMessageSender.class).sendMessage(user, "message");

        context.close();
    }
}
