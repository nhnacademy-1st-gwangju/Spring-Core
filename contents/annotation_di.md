# Spring Dependency Injection (Annotation)
## Annotation 기반 설정
- XML 방식으로 Bean 의존성 주입을 Annotation으로 구현할 수 있다.

설정을 다음과 같이 변경해야 한다.
- 지금까지 작성한 `bean.xml`에 `<context:annotation-config />`를 추가한다.
- context 네임스페이스가 동작하도록 수정해야 한다.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="
        http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

    <!-- annotation설정을 위해 추가-->
    <context:annotation-config/>

    <bean id="englishGreeter" class="com.nhnacademy.edu.springframework.greeting.service.EnglishGreeter" scope="singleton">
    </bean>

    <bean id="koreanGreeter" class="com.nhnacademy.edu.springframework.greeting.service.KoreanGreeter" scope="prototype">
    </bean>

    <bean id="greetingService" class="com.nhnacademy.edu.springframework.greeting.GreetingService" autowire="byName">
    </bean>
    
</beans>
```

## Annotation 기반 설정 - `@Required`
- 반드시 의존성이 주입되어야 함을 강제하는 annotation이다.
- Spring Framework 5부터 deplicated 되었다.
  - 사용을 권장하지 않고 차기 버전에서 삭제될 수 있다.
  - Legacy 애플리케이션에서는 사용하고 있을 수 있다.

### 사용법
- `bean.xml`에 `RequiredAnnotationBeanPostProcessor`를 Bean으로 등록한다.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="
        http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd">

    <!-- annotation설정을 위해 추가-->
    <context:annotation-config />
    
    <!-- @Required 애너테이션 사용을 위해 추가 -->
    <bean class="org.springframework.beans.factory.annotation.RequiredAnnotationBeanPostProcessor" />
    
    .. 생략 ...
    
</beans>
```

- 의존성 주입이 반드시 성공해야 한다는 보장이 필요한 setter 메소드에 `@Required`를 설정한다.

```java
public class GreetingService {
    private Greeter greeter;

    @Required
    public void setKoreanGreeter(Greeter greeter) {
        System.out.println("setGreeter invoked!");
        this.greeter = greeter;
    }
    
    -- 이하 생략 --
```

`@Required` 작동을 확인하기 위해 `GreetingService` 빈 설정에서 autowire 속성을 제거하고 실행한다.

```xml
<bean id="greetingService" class="com.nhn.edu.springframework.ioc.GreetingService" >
</bean>
```

실행 결과

```log
Caused by: org.springframework.beans.factory.BeanInitializationException: Property 'koreanGreeter' is required for bean 'greetingService'
	at org.springframework.beans.factory.annotation.RequiredAnnotationBeanPostProcessor.postProcessPropertyValues(RequiredAnnotationBeanPostProcessor.java:158)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.populateBean(AbstractAutowireCapableBeanFactory.java:1436)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:619)
	... 11 more
```

## Annotation 기반 설정 - `@Autowired`
- `@Autowired`를 이용하여 XML의 autowire 속성을 대신할 수 있다.
- 아래에서 autowire 속성을 제거한다.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="
        http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd">

    <!-- annotation설정을 위해 추가-->
    <context:annotation-config />

<!--    <bean id="englishGreeter" class="com.nhn.edu.springframework.ioc.helloworld.EnglishGreeter" scope="singleton" init-method="init">-->
<!--    </bean>-->

    <bean id="koreanGreeter" class="com.nhn.edu.springframework.ioc.helloworld.KoreanGreeter" scope="prototype" >
    </bean>

    <bean id="greetingService" class="com.nhn.edu.springframework.ioc.GreetingService" >
    </bean>

    <bean class="org.springframework.beans.factory.annotation.RequiredAnnotationBeanPostProcessor" />

</beans>
```

- `@Target`에 정의된 위치에 `@Autowired` 애너테이션을 사용할 수 있다.

```java
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {
    boolean required() default true;
}
```

### Setter Injection
```java
public class GreetingService {
    private Greeter greeter;

    @Autowired
    public void setKoreanGreeter(Greeter greeter) {
        System.out.println("setGreeter invoked!");
        this.greeter = greeter;
    }

    public void greet() {
        greeter.sayHello();
    }
}
```

### Field Injection
```java
public class GreetingService {
    @Autowired
    private Greeter greeter;

    public void greet() {
        greeter.sayHello();
    }
}
```

### Constructor Injection
Spring Framework에서 권장하는 생성자 방식으로 의존성을 주입한다.
  - Spring 4.3 부터 생성자가 1개인 경우 생략 가능

```java
public class GreetingService {
    private final Greeter greeter;

    @Autowired
    public GreetingService(Greeter greeter) {
        this.greeter = greeter;
    }

    public void greet() {
        greeter.sayHello();
    }
}
```

- 같은 타입의 객체가 여러 개 존재하는 경우, 배열이나 Collection으로 의존성을 주입할 수 있다.

```java
public class GreetingService {
    private final List<Greeter> greeters;

    @Autowired
    public GreetingService(List<Greeter> greeters) {
        // EnglishGreeter, KoreanGreeter 스프링 빈이 주입 됨.
        // 순서는 알 수 없다.
        this.greeters = greeters;
    }

    public void greet() {
        greeters.forEach(Greeter::sayHello);
    }
}
```

## @Autowired 미세 조정
### required 속성
- `@Required` 어노테이션과 동일한 효과를 가진다.
- 기본 값은 true이다.

```java
@Autowired(required = true)
public void setKoreanGreeter(Greeter greeter) {
    System.out.println("setGreeter invoked!");
    this.greeter = greeter;
}
```

### Optional 타입
- 스프링 빈을 `Optional` 타입으로 받으면 자동으로 required=false 옵션이 설정된다고 볼 수 있다.

```java
public void setKoreanGreeter(Optional<Greeter> greeter) {
    System.out.println("setGreeter invoked!");
    this.greeter = greeter.orElseThrow();
}
```

### primary 속성
- type으로 autowire 한 경우, 같은 타입의 여러 빈이 존재할 경우에 오류가 발생한다. 이 때, 특정 빈을 지정하는 방법을 제공한다.
- 빈 설정에 primary 를 설정해주면 같은 타입의 빈이 있더라도 우선 적용할 수 있다.

```xml
<bean id="koreanGreeter" class="com.nhn.edu.springframework.ioc.helloworld.KoreanGreeter" scope="prototype" primary="true" >
</bean>
```

### @Qualifier
- `@Autowired` 할 때 , `@Qualifier`를 지정하여 빈의 이름으로 의존성을 주입할 수 있다.

```java
public class GreetingService {

    private final Greeter greeter;

    @Autowired
    public GreetingService(@Qualifier("englishGreeter") Greeter greeter) {
        this.greeter = greeter;
    }

    public boolean greet() {
        // 인터페이스의 메소드를 호출하지만 실제 구현 객체의 메소드가 실행됩니다.
        return greeter.sayHello();
    }
}
```

## 커스텀 @Qualifier
### 개발자가 직접 `@Qualifier`를 대신하는 어노테이션을 만들 수 있다.

```java
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface Lang {
    String value();
}
```

- 위에서 선언한 `@Lang` 어노테이션을 다음과 같이 사용하면 `@Qualifier` 역할을 하도록 한다.

```java
@Autowired
public GreetingService(@Lang("englishGreeter") Greeter greeter) {
    this.greeter = greeter;
}
```

### value 없는 어노테이션을 생성하여 어노테이션을 설정하는 것 만으로도 처리를 할 수 있다.

```java
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface Lang {
}
```

- 위에서 선언한 `@Lang` 어노테이션을 다음과 같이 사용하면 `@Qualifier` 역할을 하도록 한다.

```java
@Autowired
public GreetingService(@Lang Greeter greeter) {
    this.greeter = greeter;
}
```

- `@Korean` 어노테이션을 동작하게 하려면 Bean 설정에서 Qualifer 설정을 추가한다.

```xml
<bean id="englishGreeter" class="com.nhnacademy.edu.springframework.greeting.service.EnglishGreeter" scope="singleton">
    <qualifier type="com.nhnacademy.edu.springframework.greeting.annotation.Lang"/>
</bean>

<bean id="koreanGreeter" class="com.nhnacademy.edu.springframework.greeting.service.KoreanGreeter" scope="prototype">
</bean>

<bean id="greetingService" class="com.nhnacademy.edu.springframework.greeting.GreetingService">
</bean>
```

### 커스텀 Qualifier는 value 뿐만 아니라 다른 이름의 속성들을 정의하여 지정할 수 있다.
```java
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface GreeterQualifier {
  Language language();
  boolean dummy();
}
```

```java
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public enum Language {
  KOREAN, ENGLISH
}
```

- 위에서 선언한 `@GreeterQualifier` 어노테이션을 다음과 같이 사용하면 `@Qualifier` 역할을 하도록 한다.

```java
@Autowired
public GreetingProcessor(@GreeterQualifier(language = Language.ENGLISH, dummy = false) Greeter greeter) {
    this.greeter = greeter;
}
```

- `@GreeterQualifier` 어노테이션을 동작하게 하려면 Bean 설정에서 Qualifier 설정을 추가한다.

```xml
<bean id="englishGreeter" class="com.nhn.edu.springframework.ioc.helloworld.EnglishGreeter" scope="singleton" init-method="init">
    <qualifier type="GreeterQualifier">
        <attribute key="language" value="ENGLISH"/>
        <attribute key="dummy" value="false"/>
    </qualifier>
</bean>

<bean id="koreanGreeter" class="com.nhn.edu.springframework.ioc.helloworld.KoreanGreeter" scope="prototype" primary="true" >
    <qualifier type="GreeterQualifier">
        <attribute key="language" value="KOREAN"/>
        <attribute key="dummy" value="false"/>
    </qualifier>
</bean>
```

## CustomAutowireConfigurer
- 커스텀 Qualifier를 등록하기 위한 BeanFactoryPostProcessor의 구현체이다.
- Custom Qualifier에 `@Qualifier`를 설정하지 않아도 동작하도록 구성할 수 있다.

```xml
<bean class="org.springframework.beans.factory.annotation.CustomAutowireConfigurer">
    <property name="customQualifierTypes">
        <set>
            <value>com.nhn.edu.springframework.ioc.stereotype.GreeterQualifier</value>
        </set>
    </property>
</bean>
```

```java
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
//@Qualifier  <-- 주석처리를 해도 CustomAutowireConfigurer 때문에 동작합니다.
public @interface GreeterQualifier {
    Language language();
    boolean dummy();
}
```


## @Value
- `@Value`는 주로 외부 속성을 주입하기 위해 사용한다.
- 외부 속성은 `src/main/resource` 디렉토리에 `greeting.properties`에 다음과 같이 설정할 수 있다.

```
// greeting.properties
from=Ramos
```

```xml
<beans>
  ....
  <context:property-placeholder location="classpath:greeter.properties" />
</beans> 
```

이 속성을 주입하려면 `@Value` 어노테이션을 사용한다.

```java
public class GreetingService {

    private final Greeter greeter;

    @Value("${from}")
    private String from;

    @Autowired
    public GreetingService(@Qualifier("koreanGreeter") Greeter greeter) {
        this.greeter = greeter;
    }

    public boolean greet() {
        // 인터페이스의 메소드를 호출하지만 실제 구현 객체의 메소드가 실행됩니다.
        System.out.println("From : " + from);
        return greeter.sayHello();
    }
}
```

실행 결과
```log
From : Ramos
안녕 세상!
```

## 생각해볼 주제
- Annotation 기반 설정이 XML 보다 항상 좋은가?

## 장점
- 짧고 간결하게 설정을 할 수 있다.
- java 코드와 동일한 방식으로 구성할 수 있어 배우기 쉽다.

## 단점
- 침습적(invasive) : Annotation을 사용하면 스프링 빈 코드에 Spring Framework의 의존성이 발생한다. 더이상 POJO가 아니라고 할 수 있다.
- 설정을 변경하려면 컴파일을 해야한다. (XML은 컴파일이 필요하지 않다.)
- 설정이 여기저기 분산되어 관리가 어렵다는 의견이 있다.