package com.nhnacademy.edu.springframework.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.nhnacademy.edu.springframework.messagesender")
public class ServiceConfig {

}
