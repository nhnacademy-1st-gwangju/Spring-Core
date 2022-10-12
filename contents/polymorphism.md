# 다형성 (Polymorphism)
## Java의 다형성
- Java의 객체는 부모클래스 타입의 참조 변수나 구현한 인터페이스 변수에 담을 수 있다.
- 참조 변수의 메소드를 호출하면 실제 구현 객체의 메소드가 실행된다.
- 즉, 동일한 메시지를 전송하지만 실제로 어떤 메소드가 실행될 것인지는 메시지를 수신하는 객체의 클래스가 무엇인가에 따라 결정된다.
- 이메일 메시지 전송, sms 메시지 전송을 수행하는 시스템을 다형성을 이용해 개발한다 가정하면 다음과 같이 설계할 수 있다.
  ![](https://velog.velcdn.com/images/songs4805/post/96929b5f-81fe-4e51-933d-b5bea81166cd/image.png)

`MessageSendService`는 컴파일 타임에 어떤 `MessageSender`가 실행되는지 알 수 없다.
```java
public class MessageSendService {

    private MessageSender messageSender;

    public MessageSendService(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    /**
     * 인터페이스의 메소드를 호출하지만 실제 구현 객체의 메소드가 실행된다.
     */
    public void doSendMessage() {
        messageSender.sendMessage(new User("songs4805@naver.com", "82-10-1111-1111"), "message");
    }
}
```

`Greeter` 객체 생성과 실행은 main 메소드에서 수행한다.
```java
public class Main {
    public static void main(String[] args) {
        new MessageSendService(new EmailMessageSender()).greet();
        new MessageSendService(new SmsMessageSender()).greet();
    }
}
```

## 다형성 활용을 위한 설계 변경
![](https://velog.velcdn.com/images/songs4805/post/98a1590b-a326-4290-9f15-a9ccabe5c3a4/image.png)