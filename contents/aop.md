# Aspect Oriented Programming (AOP)
## AOP prologue
운영 요구사항
- KoreanGreeter의 실행 속도를 로그로 남기려고 한다.
- StopWatch를 이용해서 다음과 같이 기록을 남겼다.

```java
public class KoreanGreeter implements Greeter {

    public KoreanGreeter() {
        System.out.println("KoreanGreeter initiated!!");
    }

    @Override
    public void sayHello() {
        StopWatch stopWatch = new StopWatch();
        try {
            stopWatch.start();
            
            // biz logic
            System.out.println("안녕 세상! ");
            
        } finally {
            stopWatch.stop();
            System.out.println(stopWatch.prettyPrint());
        }
    }
}
```

- KoreanGreeter의 `sayHello()` 실행시간 로그를 남기고 보니 전체 Greeter의 성능을 확인할 필요가 있는 것으로 판단되어 EnglishGreeter에도 수행시간 로그를 남기기로 했다.

```java
public class EnglishGreeter implements Greeter {

    public EnglishGreeter() {
        System.out.println("EnglishGreeter initiated!!");
    }

    @Override
    public void sayHello() {
        StopWatch stopWatch = new StopWatch();
        try {
            stopWatch.start();
            
            // biz logic
            System.out.println("Hello World!");
            
        }finally {
            stopWatch.stop();
            System.out.println(stopWatch.prettyPrint());
        }
    }
}
```

위 코드의 문제점은 다음과 같다.
- 반복 코드를 계속 작성해야 한다.
  - 운영 요구사항이 변경된다면?
  - 적용해야 할 Greeter가 매우 많다면?
- Greeting이라는 핵심 비즈니스 관심사에 해당하지 않는 소요시간 측정 코드가 비즈니스 코드에 섞이게 된다.

## Aspect-Oriented Programming
- 관점 지향 프로그래밍
- AOP란 프로그램 구조를 다른 방식으로 생각하게 함으로써 OOP를 보완한다.
- OOP에서 모듈화의 핵심단위는 클래스이지만, AOP의 모듈화의 핵심단위는 관점(aspect)이다.
- 관점은 다양한 타입과 객체에 걸친 트랜잭션 관리같은 관심(concern)을 모듈화 할 수 있게 한다.
  - crosscutting concerns: 횡단 관심사
  - core concerns: 주요 관심사

![](https://velog.velcdn.com/images/songs4805/post/85b9b88a-d461-4ac1-9da4-3e01016e1dd2/image.png)

- 횡단 관심사와 주요 관심사를 분리하여 관점별로 각각 기능을 모듈화 할 수 있다.
- 설정을 추가하여 Weaving 한다.

![](https://velog.velcdn.com/images/songs4805/post/e5da59bb-1292-411e-85b9-912ed12bd5d5/image.png)

## AOP 주요 용어
### Aspect
- 여러 클래스에 걸친 횡단 관심사의 모듈 (클래스)
- 하나 이상의 Pointcut과 Advice의 조합으로 만들어지는 AOP의 기본 모듈
- Spring Framework에서는 `@Aspect`를 사용하거나 XML에서 설정할 수 있다.

### Join Point
- 프로그램 실행 중의 어떤 포인트를 의미 (메소드 실행, Exception 처리 등)
- Pointcut의 후보라고 생각할 수 있다.
- Spring AOP에서는 메소드 실행만 대상이다.

```java
package org.aspectj.lang;

public interface JoinPoint {

     /* 중간 생략 */

    /**
     * The legal return values from getKind()
     */
	String METHOD_EXECUTION = "method-execution";
    String METHOD_CALL = "method-call";
    String CONSTRUCTOR_EXECUTION = "constructor-execution";
    String CONSTRUCTOR_CALL = "constructor-call";
    String FIELD_GET = "field-get";
    String FIELD_SET = "field-set";
    String STATICINITIALIZATION = "staticinitialization";
    String PREINITIALIZATION = "preinitialization";
    String INITIALIZATION = "initialization";
    String EXCEPTION_HANDLER = "exception-handler";
    String SYNCHRONIZATION_LOCK = "lock";
    String SYNCHRONIZATION_UNLOCK = "unlock";

    String ADVICE_EXECUTION = "adviceexecution";
}
```

### Advice
- 타겟에 제공할 부가 기능을 담은 모듈
- 특정 Join Point에서 Aspect가 취하는 행동
- ex) around, before, after

### Pointcut
- Advice를 적용할 Join Point를 선별하는 작업 또는 그 기능을 적용한 모듈
- Advice는 Pointcut 표현식과 연결되고 Pointcut이 매치한 Join Point에서 실행된다.

### Target object
- 부가 기능을 부여할 대상
- 하나 이상의 Aspect로 어드바이즈된(advised) 객체
- advised object라고 부르기도 함

### AOP Proxy
- 클라이언트와 타겟 사이에 투명하게 존재하면서 부가 기능을 제공하는 오브젝트
- aspect 계약 (어드바이스 메서드 실행 등)을 위해 AOP에 의해 생성된 객체

### Advisor
- Pointcut과 Advice를 하나 씩 갖고있는 객체
- Spring AOP에서만 사용되는 용어

### Weaving
- 다른 어플리케이션 타입이나 어드바이즈된 객체를 생성하는 객체와 관점을 연결하는 행위를 의미

![](https://velog.velcdn.com/images/songs4805/post/27fc6ef9-86db-4dda-bde7-c3b97d2c1eb8/image.png)


## Spring AOP vs @AspectJ
- Spring AOP
  - AOP 개념을 스프링 빈(Spring Bean)에 적용하기 위한 것.
  - Spring Bean 대상이므로 ApplicationContext가 처리한다.
  - 런타임 Weaving
- AspectJ
  - AOP 개념을 모든 객체에 적용하기 위한 것
  - 컴파일 시점, 로드 시점 Weaving

## Spring AOP 설치
- `pom.xml` 파일에 spring-aspects 라이브러리 의존성을 추가한다.

```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-aspects</artifactId>
    <version>5.3.15</version>
</dependency>
```

## @AspectJ (Annotation) 지원
- `@AspectJ` 스타일은 일반 java에 annotation을 설정하는 방식이다.
- 스프링 프레임워크는 AspectJ 5의 annotation을 사용하지만 **AspectJ의 컴파일러나 위버(Weaver)를 사용하지 않는다.**  
ex) `import org.aspectj.lang.annotation.Aspect`

## @AspectJ 지원 활성화
- Java `@Configuration`에서 `@AspectJ` 지원을 활성화하려면 `@EnableAspectJAutoProxy`를 사용하여 설정한다.

```java
@Configuration
@EnableAspectJAutoProxy
public class AppConfig {
}
```

- XML 설정에서 `@AspectJ` 지원을 활성화 하려면 `aop:aspectj-autoproxy/`를 사용하여 설정한다.

```xml
<aop:aspectj-autoproxy/>
```

## Aspect 선언
- AspectJ 지원이 활성화 된 상태에서, Bean으로 선언하고 @Aspect annotation을 설정하면 해당 스프링 빈은 Aspect가 된다.

```java
@Aspect
@Component
public class LoggingAspect {
  // ...
}
```

![](https://velog.velcdn.com/images/songs4805/post/92cb3534-8804-430a-adcc-0977516c350a/image.png)


## 포인트컷
- 타겟의 여러 조인 포인트 중에 어드바이스를 적용할 대상을 지정하는 키워드이다.
- 스프링 AOP는 스프링 빈의 메소드 실행 조인 포인트만 지원한다.
- 포인트컷 선언은 **포인트컷 표현식과 포인트컷 시그니쳐**로 구성된다.

```java
@Pointcut("execution(* transfer(..))") // the pointcut expression
private void anyOldTransfer() {} // the pointcut signature
```

- 위의 예제는 anyOldTransfer가 포인트컷의 이름이고 모든 스프링 빈에서의 transfer 메소드 실행에 매칭이 된다.

## 포인트컷 - Pointcut Designator
스프링 AOP에서 지원하는 포인트컷 지정자

### execution
- 메소드 실행 조인 포인트와 매칭
- 스프링 AOP의 주요 포인트컷 지정자

### within
- 주어진 타입(클래스)으로 조인 포인트 범위를 제한

### this
- 주어진 타입을 구현한 스프링 AOP Proxy 객체에 매칭
- 보통 Proxy 객체를 Advice 파라미터에 바인딩하는 용도로 쓰인다.

### target
- 주어진 타입을 구현한 타겟 객체에 매칭
- 보통 타겟 객체를 Advice 파라미터에 바인딩하는 용도로 쓰인다.

### args
- 주어진 타입의 인수들을 이용해 매칭
- 보통 메소드 인자를 Advice 파라미터에 바인딩하는 용도로 쓰인다.

### @target
- 주어진 타입의 애너테이션을 가진 클래스의 인스턴스를 매칭

### @args
- 실제 인수의 런타임 타입의 주어진 타입의 애너테이션을 가질 경우 매칭

### @within
- 주어진 타입의 애너테이션을 타입들로 제한하여 매칭

### @annotation
- 주어진 애너테이션을 가지고 있을 경우 매칭

### bean
- 스프링 AOP에서 지원하는 추가적인 포인트컷 지정자
- 스프링 빈의 이름에 해당하는 메서드 실행을 매칭

```java
bean(idOrNameOfBean)
```

## 포인트컷 - 조합