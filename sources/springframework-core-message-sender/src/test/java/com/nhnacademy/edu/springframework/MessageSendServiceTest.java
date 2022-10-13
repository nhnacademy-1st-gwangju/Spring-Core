package com.nhnacademy.edu.springframework;

import com.nhnacademy.edu.springframework.messagesender.MessageSendService;
import com.nhnacademy.edu.springframework.messagesender.MessageSender;
import com.nhnacademy.edu.springframework.messagesender.User;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.*;

class MessageSendServiceTest {

    @Test
    public void test() throws Exception {
        //given
        MessageSender messageSender = mock(MessageSender.class);
        MessageSendService messageSendService = new MessageSendService(messageSender);
        ReflectionTestUtils.setField(messageSendService, "name", "Ramos");

        //when
        messageSendService.doSendMessage();

        //then
        verify(messageSender, times(1))
                .sendMessage(any(User.class), any(String.class));
    }
}
