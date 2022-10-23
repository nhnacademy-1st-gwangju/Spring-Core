# Spring Dependency Injection (XML)
## 의존성 주입 (Dependency Injection)
- Dependency Injection (DI)
  - IoC 의 패턴 중 하나
  - DI = Dependency Injection
  - Object 간의 의존성을 낮춘다.
  - 외부에서 객체를 생성하고 전달한다.

## Dependency Inversion Principle
![](https://velog.velcdn.com/images/songs4805/post/92165278-e13c-4e66-b439-7f1e964f6e03/image.png)

- 상위 모듈이 하위 모듈에 의존관계를 가지지 않도록 구현해야 한다.
- 추상 클래스는 그 구현체의 내용에 의존관계를 가지지 않는다.
- 구현체가 추상클래스에 의존관계를 가질 수 있다.

## DI의 예시 - Factory Method 패턴
- https://ko.wikipedia.org/wiki/소프트웨어_디자인_패턴

### 문제
- 사용자에게 다양한 문서를 읽어 객체로 결과를 반환하는 프레임워크 개발을 해야 한다.
- `Application` 클래스와 `Document` 클래스로 추상화 할 수 있는데, 이 두 클래스는 모두 추상 클래스이고 이 클래스들을 상속해서 문서의 종류에 따른 대응을 할 수 있다.
- `Application` 클래스는 언제 `Document` 클래스를 생성하고 사용해야 하는 지는 알 수 있지만 프레임워크에서 구체적으로 어떤 문서를 처리할 `Document`를 생성해야 하는지는 결정할 수 없다.

### 해법
- `Document`의 서브 클래스 중 어느 클래스를 생성하는 지는 `Application` 클래스의 서브 클래스가 결정하도록 설계한다.
- `Application` 클래스의 서브 클래스는 추상화된 `createDocument()` 메소드를 정의하여 적당한 `Document` 클래스의 서브 클래스를 반환하도록 한다.
- `createDocument()` 메소드를 **Factory Method**라 한다.

![](https://velog.velcdn.com/images/songs4805/post/d2f1aa67-2197-48d2-8ea5-c496d0a0a244/image.png)

### 구현 코드 (Framework)
- `Application.java`
  - 어떤 Document를 처리할 지 Application 추상 객체에서 결정하지 않는다.

```java
public abstract class Application {
    public void openDocument() {
        // do something
        Document document = createDocument();
        document.open();
        // do something
    }

    // 구현 객체가 runtime 에 결정된다.(Dependency Injection)
    protected abstract Document createDocument();
}
```

- `Document.java`

```java
public abstract class Document {
    public abstract void open();
}
```

### 구현 코드 (구현체)
- `CsvApplication.java`

```java
public class CsvApplication extends Application {
    @Override
    protected Document createDocument() {
        return new CsvDocument();
    }
}
```

- `CsvDocument.java`

```java
public class CsvDocument extends Document {
    @Override
    public void open() {
        System.out.println("csvDocument opened");
    }
}
```

### 구현 코드 (실행)
- `Main.java`

```java
public class Main {
    public static void main(String[] args) {
        Application csvApplication = new CsvApplication();
        csvApplication.openDocument();

        Application jsonApplication = new JsonApplication();
        jsonApplication.openDocument();
    }
}
```

### 결과
- 구체적인 클래스 (`CsvDocument`)가 추상 클래스 (`Application`)에 종속되지 않도록 구현할 수 있다.

→ Spring Framework의 DI를 사용하면 Application 클래스와 같이 사용할 클래스의 구현체를 확정하지 않는 업무 코드를 작성할 수 있다.

## Dependency Injection
- DI의 정의  
  → 프로그래밍에서 구성 요소 간의 의존 관계가 소스코드 내부가 아닌 외부의 설정 파일 등을 통해 정의되게 하는 디자인 패턴 중의 하나
- DI는 디자인 패턴이다. 핵심 원칙은 의존성 이슈로부터 행동을 분리시키는 것이다.
- DI는 IoC의 구현일 뿐이다.

## Spring Framework Dependency Injection (Bean Wiring) 방법
- Constructor Injection
- Setter Injection
- Field Injection

## Constructor Injection
- 생성자를 통한 의존성 주입
- Bean을 정의할 때 `constructor-arg` 태그를 추가하여 주입할 수 있다.

### 예시 (XML)
- 생성자 주입 방식을 사용하여, `GreetingService` 스프링 빈에 `Greeter` 스프링 빈을 주입하는 예제
- `GreetingService`가 `Greeter`를 생성자 인자로 받을 수 있도록 생성자를 추가한다.

```java
public GreetingService(Greeter greeter) {
    this.greeter = greeter;
}
```

- `GreetingService`를 빈으로 등록한다.
- `GreetingService`의 생성자를 이용해서 `koreanGreeter` 빈을 주입한다.

```xml
<bean id="greetingService" class="com.nhnacademy.edu.springframework.greeting.GreetingService" >
    <constructor-arg ref="koreanGreeter" />
</bean>
```

- `GreetingService` 빈을 `greetingService`으로 빈을 선언하였기 때문에 런타임에 `ApplicationContext`에 `GreetingService` 객체가 생성되어 등록되어 있다. (IoC)
- `constructor-arg`를 이용하여 `koreanGreeter`를 의존성으로 주입한다.
- 이를 조회하여 실행한다.

```java
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConstructorInjectionMain {

    public static void main(String[] args) {
        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml")) {
            GreetingService service = context.getBean("greetingService", GreetingService.class);
            service.greet();
        }
    }
}
```

## Setter Injection
- setter 메소드를 이용하여 의존성 주입
- 클래스를 생성할 때 setter 메소드를 작성하여 의존성을 주입하도록 한다.

### 얘시
- `GreetingService`가 `Greeter`를 setter 메소드 인자로 받을 수 있도록 setter 메소드를 추가한다.

```java
//    final keyword 이므로 객체를 생성한뒤 greeter 변수에 값을 할당할 수 없다.
//    private final Greeter greeter;
private Greeter greeter;

//    Setter Injection 기본 생성자가 필요하다.
public GreetingService() {}
    
public void setGreeter(Greeter greeter) {
    System.out.println("setGreeter invoked!");
    this.greeter = greeter;
}
```

- `GreetingService`의 setter 메소드를 이용해서 `koreanGreeter` 빈을 주입한다.

```xml
<bean id="greetingService" class="com.nhnacademy.edu.springframework.greeting.GreetingService" >
    <property name="greeter" ref="koreanGreeter" />
</bean>
```

## Autowired Injection
- autowired 속성을 사용하여 자동으로 주입
- autowired injection은 다음의 방식을 사용할 수 있다.
  - byType
  - byName
  - constructor

### byType
- `GreetingService`에 autowire 설정을 추가한다.

```xml
<!--    byType으로 autowire 를 하려면 해당되는 type 의 bean 이 1개만 존재해야 합니다.-->
<!--    <bean id="englishGreeter" class="com.nhnacademy.edu.springframework.greeting.service.EnglishGreeter" scope="singleton">-->
<!--    </bean>-->

<bean id="koreanGreeter" class="com.nhnacademy.edu.springframework.greeting.service.KoreanGreeter" scope="prototype">
</bean>

<bean id="greetingService" class="com.nhnacademy.edu.springframework.greeting.GreetingService" autowire="byType">
</bean>
```

### byName
- `GreetingService`에 autowire 설정을 추가한다.

```xml
<bean id="englishGreeter" class="com.nhnacademy.edu.springframework.greeting.service.EnglishGreeter" scope="singleton">
</bean>

<bean id="koreanGreeter" class="com.nhnacademy.edu.springframework.greeting.service.KoreanGreeter" scope="prototype">
</bean>

<bean id="greetingService" class="com.nhnacademy.edu.springframework.greeting.GreetingService" autowire="byName">
</bean>
```

- autowire 속성을 byName으로 설정했기 때문에 setter 메소드의 이름을 `setKoreanGreeter`로 수정한다.

```java
public class GreetingService {
    private Greeter greeter;

    public void setKoreanGreeter(Greeter greeter) {
        System.out.println("setGreeter invoked!");
        this.greeter = greeter;
    }

    // 기본 생성자가 필요하기 때문에 아래는 주석처리
//    public GreetingService(Greeter greeter) {
//        this.greeter = greeter;
//    }

    public void greet() {
        greeter.sayHello();
    }
}
```