# Spring IoC (Inversion of Control)
## IoC (제어의 역전)
- 프로그래밍을 작성할 때 프로그램이 흘러가는 흐름이나 객체의 생성에 대한 제어권을 개발자가 가지지 않고 프레임워크가 가지는 것을 의미한다.
- 개발자가 코드의 흐름이나 객체 생성에 관련된 코드를 직접 작성하지 않고 프레임워크에 설정을 제공하면 이를 토대로 프레임워크가 객체를 생성하고 코드가 동작하는 순서를 결정하게 된다는 의미이다.

## 헐리우드 원칙
> 우리에게 전화(call)하지 마세요. 우리가 당신을 부를(call) 것입니다.

객체지향 프레임워크와 클래스 라이브러리의 큰 차이점은 프레임워크가 애플리케이션 코드를 호출한다는 것이다. 일반적으로는 애플리케이션 코드가 클래스 라이브러리를 호출한다. 이러한 제어의 역전은 때로 헐리우드 원칙이라고도 한다.

## IoC 예시 - 흐름의 제어 역전 (template method 패턴)
- 추상 클래스에 템플릿에 해당하는 메소드에서 실행의 흐름이 이미 결정되어 있다.
- 단, 각 메소드가 구체적으로 어떤 일을 할 것인지는 개발자의 구현에 따라 결정된다.
- `preProcess()` -> `mainProcess()` -> `postProcess()`

### template method
```java
public abstract class AbstractProcessor {

    public final void process() {
        preProcess();
        mainProcess();
        postProcess();
    }

    protected abstract void preProcess();
    protected abstract void mainProcess();
    protected abstract void postProcess();
}
```

```java
public class ConsoleLogProcessor extends AbstractProcessor {

    @Override
    protected void preProcess() {
        System.out.println("preProcess");
    }

    @Override
    protected void mainProcess() {
        System.out.println("mainProcess");
    }

    @Override
    protected void postProcess() {
        System.out.println("postProcess");
    }
}
```

```java
public class TemplateMethodMain {

    public static void main(String[] args) {
        AbstractProcessor processor = new ConsoleLogProcessor();
        processor.process();
    }
}
```

`ConsoleLogProcessor`는 실행의 흐름에 대한 어떤 책임도 없다. 단순히 `AbstractProcessor`에서 제공하는 필수 메소드를 구현하기만 했다. 구현은 `preProcess`, `mainProcess`, `postProcess` 를 했지만 실행의 흐름은 `AbstractProcessor` 에 의해서 제어가 된다.

구체 `Processor`가 여러 개 있어 이를 교체하고 싶다면 `TemplateMethodMain` 클래스만 수정하면 된다. `TemplateMethodMain` 클래스가 프레임워크 코드라면 개발자가 작성한 `AbstractProcessor` 및 `Processor` 객체 생성 제어를 프레임워크에게 위임한 형태가 될 것이다.

## ApplicationContext
### Spring IoC Container
![](https://velog.velcdn.com/images/songs4805/post/0b60a82a-cae7-4a35-adcf-aba4084150a1/image.png)

- `org.springframework.context.ApplicationContext` interface represents the **Spring IoC Container**
- and is responsible for **instantiating, configuring, and assembling the beans**
- by reading configuration metadata

### Spring Bean
- Spring Bean은 name, type, object로 구성되어 있다.
- Spring Framework에서 중요하게 관리하는 객체로 이해

> 📌 JavaBeans  
> - public default (no argument) constructor
> - getter/setter
> - implement java.io.Serializable

Spring Beans와 JavaBeans는 다르다.

### Bean Factory, ApplicationContext
![](https://velog.velcdn.com/images/songs4805/post/2c3419f1-c971-43ad-bc80-cbac9b8c9879/image.png)


Bean Factory + a = ApplicationContext

![](https://velog.velcdn.com/images/songs4805/post/5437a0c9-dbb7-4a30-a0a1-9b858d485752/image.png)

ApplicationContext은 Bean Factory의 기능인 빈의 생성과 관계설정과 더불어 추가적인 기능으로 별도의 설정 정보를 참고하고 IoC를 적용하여 빈의 생성, 관계 설정 등의 제어 작업을 총괄한다. 애플리케이션 컨텍스트에는 직접 오브젝트를 생성하고 관계를 맺어주는 코드가 없고, 그런 생성정보와 연관관계 정보(ex: `@Configuration`)에 대한 설정을 읽어 처리한다.

- Bean factory methods for accessing application components.
- The ability to load file resources in a generic fashion.
- The ability to publish events to registered listeners.
- The ability to resolve messages to support internationalization.
- Inheritance from a parent context.

단순하게 요약하자면, Spring Bean의 생성/파기/주입을 담당한다.

- `Xml...ApplicationContext`
- `Annotation...ApplicationContext`
- `...Groovy...ApplicationContext`
- `...Web...ApplicationContext`

![](https://velog.velcdn.com/images/songs4805/post/e397c25c-5921-4bf3-9907-e86d09bbc7f5/image.jpeg)

실제로 객체 생성을 하는 예시는 다음과 같다.

`beans.xml`을 `src/main/resources` 내에 두고 다음과 같이 정의한다.
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="
        http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">


    <bean id="englishGreeter" class="com.nhnacademy.edu.springframework.greeting.service.EnglishGreeter" >
    </bean>

    <bean id="koreanGreeter" class="com.nhnacademy.edu.springframework.greeting.service.KoreanGreeter" >
    </bean>

</beans>
```

main에서 `ClassPathXmlApplicationContext`를 사용하여 `beans.xml`을 로딩하고 Spring Bean 객체를 받아온다.
```java
package com.nhnacademy.edu.springframework.greeting.xml;

import com.nhnacademy.edu.springframework.greeting.service.Greeter;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XmlMain {
    public static void main(String[] args) {
        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml")) {
            Greeter koreanGreeter = context.getBean("koreanGreeter", Greeter.class);
            Greeter englishGreeter = context.getBean("englishGreeter", Greeter.class);

            koreanGreeter.sayHello();
            englishGreeter.sayHello();
        }
    }
}
```

## Bean 객체의 생명주기
## Bean Scope
- singleton - default
- prototype
- Only valid in the context of a web-aware Spring ApplicationContext
  - request - lifecycle of a single HTTP request
  - session - lifecycle of an HTTP Session
  - application - lifecycle of a ServletContext
  - websocket - lifecycle of a WebSocket
  - ~~global session~~ - portlet (dropped in spring 5)

![](https://velog.velcdn.com/images/songs4805/post/46f6a8d2-1108-496b-be4e-7ed877bf9b7a/image.png)

![](https://velog.velcdn.com/images/songs4805/post/9bd6d906-d138-4342-9f67-1b495f490c3c/image.png)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="
        http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">


    <bean id="englishGreeter" class="com.nhn.edu.springframework.ioc.helloworld.EnglishGreeter"  scope="singleton">
    </bean>

    <bean id="koreanGreeter" class="com.nhn.edu.springframework.ioc.helloworld.KoreanGreeter" scope="prototype">
    </bean>

</beans>
```

해당 객체들을 생성할 때 마다 로그를 찍어보면 싱글톤은 한 번, 프로토타입은 생성 할 때마다 찍히는 것을 확인할 수 있다.

## 객체의 생명 주기 callbacks - 초기화
- `org.springframework.beans.factory.InitializingBean` 인터페이스를 구현한 빈은 생성시에 초기화 작업을 수행할 수 있다.
- InitializingBean은 `afterPropertiesSet()` 이라는 단일 메서드를 가진다.

```java
void afterPropertiesSet() throws Exception;
```

```java
package com.nhn.edu.springframework.ioc.helloworld;

import org.springframework.beans.factory.InitializingBean;

public class EnglishGreeter implements Greeter, InitializingBean {

    public EnglishGreeter() {
        System.out.println("EnglishGreeter initiated!!");
    }
    
    public void afterPropertiesSet() {
        // do some initialization work
    }
    
    @Override
    public void sayHello() {
        System.out.println("Hello World!");
    }
}
```
위 방식은 컴포넌트에 스프링 프레임워크의 의존성이 발생하므로 권장하지 않는다고 한다.

XML 빈 설정에서 init-method 에 초기화 메서드 이름을 지정하여 초기화 작업을 등록할 수 있다.
```xml
<bean id="koreanGreeter" class="com.nhn.edu.springframework.ioc.helloworld.EnglishGreeter" init-method="init">
```

```java
package com.nhn.edu.springframework.ioc.helloworld;

public class EnglishGreeter implements Greeter {

    public EnglishGreeter() {
        System.out.println("EnglishGreeter initiated!!");
    }
    
    public void init() {
        System.out.println("EnglishGreeter init called!!");
    }
    
    @Override
    public void sayHello() {
        System.out.println("Hello World!");
    }
}
```

## 객체의 생명 주기 callbacks - 소멸
- `org.springframework.beans.factory.DisposableBean` 인터페이스를 구현한 빈은 소멸시에 호출된다.
- DisposableBean은 `destroy()` 이라는 단일 메서드를 가진다.

```java
void destroy() throws Exception;
```

이 방식도 권장하지 않는다.

XML 빈 설정에서 destroy-method 에 초기화 메서드 이름을 지정하여 초기화 작업을 등록할 수 있다.
```xml
<bean id="koreanGreeter" class="com.nhn.edu.springframework.ioc.helloworld.EnglishGreeter" destroy-method="cleanup">
```

```java
package com.nhn.edu.springframework.ioc.helloworld;

public class EnglishGreeter implements Greeter {

    public EnglishGreeter() {
        System.out.println("EnglishGreeter initiated!!");
    }
    
    public void cleanup() {
        System.out.println("EnglishGreeter cleanup called!!");
    }
    
    @Override
    public void sayHello() {
        System.out.println("Hello World!");
    }
}
```

## 객체의 생명주기 - 초기와 소멸 기본 callbacks
- XML beans 설정에서 default-init-method 에 초기화 메서드 이름을 지정하여 기본 초기화 작업을 등록할 수 있다.
- XML beans 설정에서 default-destroy-method 에 소멸 callback 메서드 이름을 지정하여 기본 소멸 처리 작업을 등록할 수 있다.

```xml
<beans default-init-method="init" default-destory-method="cleaup" >
    <bean id="koreanGreeter" class="com.nhn.edu.springframework.ioc.helloworld.EnglishGreeter">
</beans>
```

## 객체의 생명주기 - BeanPostProcessor
- Spring IoC 의 생명주기 처리를 변경할 수 있는 확장 포인트
- BeanPostProcesor 가 설정되면 Bean 생명주기 이벤트를 가로채서 처리할 수 있다.

구현 메소드는 다음과 같다.
- postPostBeforeInitialization : init-method 에 지정된 메서드가 호출되기 전에 호출된다.
- postPostAfterInitialization : init-method 에 지정된 메서드가 호출된 후에 호출된다.
- init-method 가 지정되어 있지 않더라도 자동으로 호출된다.