package com.nhnacademy.edu.springframework.messagesender;

import com.nhnacademy.edu.springframework.config.MainConfig;
import com.nhnacademy.edu.springframework.config.ServiceConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class, ServiceConfig.class);

        MessageSendService messageSendService = context.getBean("messageSendService", MessageSendService.class);
        messageSendService.doSendMessage();
    }
}
