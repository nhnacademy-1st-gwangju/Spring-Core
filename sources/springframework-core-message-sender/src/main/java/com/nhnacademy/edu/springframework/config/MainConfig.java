package com.nhnacademy.edu.springframework.config;

import com.nhnacademy.edu.springframework.messagesender.EmailMessageSender;
import com.nhnacademy.edu.springframework.messagesender.MessageSender;
import com.nhnacademy.edu.springframework.messagesender.SmsMessageSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainConfig {

    @Bean
    public MessageSender emailMessageSender() {
        return new EmailMessageSender();
    }

    @Bean
    public MessageSender smsMessageSender() {
        return new SmsMessageSender();
    }
}
