package com.nhnacademy.edu.springframework.messagesender;

import com.nhn.dooray.client.DoorayHook;
import com.nhn.dooray.client.DoorayHookSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Primary
public class DoorayMessageSender implements MessageSender {

    @Value("${hookUrl}")
    private String hookUrl;
    @Value("${realName}")
    private String realName;
    @Value("${message}")
    private String sendMessage;

    public DoorayMessageSender() {
        System.out.println("DoorayMessageSender 생성");
    }

    @Override
    public boolean sendMessage(User user, String message) {
        new DoorayHookSender(new RestTemplate(), hookUrl)
                .send(DoorayHook.builder()
                        .botName(realName)
                        .text(sendMessage)
                        .build());
        return true;
    }
}
