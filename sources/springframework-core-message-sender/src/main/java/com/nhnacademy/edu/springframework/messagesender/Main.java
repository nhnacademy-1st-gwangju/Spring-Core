package com.nhnacademy.edu.springframework.messagesender;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        MessageSender emailMessageSender = context.getBean("emailMessageSender", EmailMessageSender.class);
        MessageSender smsMessageSender = context.getBean("smsMessageSender", SmsMessageSender.class);

        MessageSender emailMessageSender2 = context.getBean("emailMessageSender", EmailMessageSender.class);
        MessageSender smsMessageSender2 = context.getBean("smsMessageSender", SmsMessageSender.class);

        emailMessageSender.sendMessage(new User("songs4805@naver.com", "82-10-1111-1111"), "message");
        smsMessageSender.sendMessage(new User("songs4805@naver.com", "82-10-1111-1111"), "message");

        emailMessageSender2.sendMessage(new User("songs4805@naver.com", "82-10-1111-1111"), "message");
        smsMessageSender2.sendMessage(new User("songs4805@naver.com", "82-10-1111-1111"), "message");
    }
}
