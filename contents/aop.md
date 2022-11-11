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
- 포인트컷 표현식은 `&&, ||, !` 으로 조합할 수 있다.

```java
// anyPublicOperation 포인트컷은 모든 public 메소드 실행에 매칭
@Pointcut("execution(public * *(..))")
private void anyPublicOperation() {} 

// inTrading 포인트컷은 com.xyz.myapp.trading 패키지 내의 메소드 실행에 매칭
@Pointcut("within(com.xyz.myapp.trading..*)")
private void inTrading() {} 

// tradingOperation 포인트컷은 com.xyz.myapp.trading 패키지 내의 퍼블릭 메소드 실행에 매칭
@Pointcut("anyPublicOperation() && inTrading()")
private void tradingOperation() {} 
```

// [참고: 6.2.3.2. Combining pointcut expressions](https://docs.spring.io/spring-framework/docs/2.0.x/reference/aop.html)

## 포인트컷 - 공통 포인트컷 공유
- 대규모 시스템에서 공통적인 포인트컷을 정의하여 참조하는 방식을 사용하는 것이 유리하다.

```java
package com.xyz.myapp;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class CommonPointcuts {

    /**
     * com.xyz.myapp.web 패키지와 서브패키지(web layer)를 
     * 지정하는 포인트컷
     */
    @Pointcut("within(com.xyz.myapp.web..*)")
    public void inWebLayer() {}

    /**
     * com.xyz.myapp.service 패키지와 서브패키지(service layer)를 
     * 지정하는 포인트컷
     */
    @Pointcut("within(com.xyz.myapp.service..*)")
    public void inServiceLayer() {}

    /**
     * com.xyz.myapp.dao 패키지와 서브패키지(data access layer)를 
     * 지정하는 포인트컷
     */
    @Pointcut("within(com.xyz.myapp.dao..*)")
    public void inDataAccessLayer() {}

    /**
     * 아래 businessService 포인트컷 정의는 서비스인터페이스가 service 패키지에 있고 
     * 구현체가 service 패키지 하위에 포한된 것을 가정하고 선언되어 있다.
     *
     * com.xyz.myapp.send.service, com.xyz.myapp.receive.service 와 같이 기능단위의 패키지 구성이라면  "execution(* com.xyz.myapp..service.*.*(..))" 포인트컷 표현식을 사용할 수 있다.
     * 
     * 만약 스프링빈 이름이 Service 로 항상 끝난다면 "bean(*Service)" 표현식을 사용할 수도 있다.
     */
    @Pointcut("execution(* com.xyz.myapp.service.*.*(..))")
    public void businessService() {}

    /**
     * 아래 dataAccessOperation 포인트컷 정의는 Dao 인터페이스가 dao 패키지에 있고 
     * 구현체가 dao 패키지 하위에 포한된 것을 가정하고 선언되어 있다.
     */
    @Pointcut("execution(* com.xyz.myapp.dao.*.*(..))")
    public void dataAccessOperation() {}

}
```

- 공유된 포인트컷 Aspect는 다른 설정에서 참조할 수 있다.

```xml
<aop:config>
    <aop:advisor
        pointcut="com.xyz.myapp.CommonPointcuts.businessService()"
        advice-ref="tx-advice"/>
</aop:config>

<tx:advice id="tx-advice">
    <tx:attributes>
        <tx:method name="*" propagation="REQUIRED"/>
    </tx:attributes>
</tx:advice>
```

## 포인트컷 - 표현식 예제
- **Spring AOP는 주로 execution 포인트컷 지정자를 사용한다.**

```
execution(modifiers-pattern? ret-type-pattern declaring-type-pattern?name-pattern(param-pattern) throws-pattern?)
```

- 모든 public 메소드

```java
execution(public * *(..))
```

- get~ 으로 시작하는 모든 메소드

```java
execution(* get*(..))
```

- `com.nhnent.edu.spring_core` 패키지에 있는 모든 메소드

```java
execution(* com.nhnent.edu.spring_core.*.*(..))
```

- `com.nhnent.edu.spring_core.service.MemberService` 인터페이스에 정의된 모든 메소드

```java
execution(com.nhnent.edu.spring_core.service.MemberService.*(..))
```

- `com.nhnent.edu.spring_core.service` 패키지의 모든 메소드실행

```java
within(com.nhnent.edu.spring_core.service.*)
```

- `TestService` 프록시 구현체의 메소드 실행

```java
this(com.nhnent.edu.spring_core.service.TestService)
```

- `TestService` 인터페이스의 구현 객체의 메소드 실행

```java
target(com.nhnent.edu.spring_core.service.TestService)
```

- 런타임에 `Serializable` 타입의 단일 파라미터가 전달되는 메소드 실행 (인자 값 검사 기능에 많이 사용된다.)

```java
args(java.io.Serializable)
```

- `@Transactional` 어노테이션을 가진 모든 타겟 객체의 메소드 실행

```java
@target(org.springframework.transaction.annotation.Transactional)
```

## Advice
Advice는 포인트컷과 관련하여 메소드 실행 전, 후, 전/후 를 결정하기 위해 사용한다.

|Advice 형태|설명|
|--|--|
|before|Join Point 앞에서 실행할 Advice|
|After|Join Point 뒤에서 실행할 Advice|
|AfterReturning|Join Point가 완전히 정상 종료된 뒤 실행하는 Advice|
|Around|Join Point 앞과 뒤에서 실행되는 Advice|
|AfterThrowing|Join Point에서 예외가 발생했을 때 실행되는 Advice|

![](https://velog.velcdn.com/images/songs4805/post/4ac51bbe-54b0-42ee-8b1a-f9e2c00cd0d7/image.png)

## Advice - Advice 선언
Advice의 포인트컷은 미리 선언한 포인트컷을 참조하거나 직접 포인트컷 표현식을 사용할 수 있다.

## Advice - Before
Aspect 내에 조인포인트 전에 실행을 위한 `@Before` Advice를 다음과 같이 선언한다.

```java
@Aspect
public class BeforeExample {

    @Before("com.xyz.myapp.CommonPointcuts.dataAccessOperation()")
    public void doAccessCheck() {
        // ...
    }

    @Before("execution(* com.xyz.myapp.dao.*.*(..))")
    public void doAccessCheck() {
        // ...
    }
}
```

## Advice - AfterReturning
- Aspect 내에 조인포인트(메소드 실행) 반환 후에 실행을 위한 `@AfterReturning` Advice 를 다음과 같이 선언한다.
- 메소드 실행중에 Exception 이 발생하여 throw 될때는 `@AfterReturning` Advice가 실행되지 않는다.

```java
@Aspect
public class AfterReturningExample {

    @AfterReturning("com.xyz.myapp.CommonPointcuts.dataAccessOperation()")
    public void doAccessCheck() {
        // ...
    }
}
```

- Advice 내부에서 반환 값에 접근해야 하는 경우, returning 속성을 이용해서 advice 메소드 파라미터에 바인드 할 수 있다.

```java
@Aspect
public class AfterReturningExample {

    @AfterReturning(
        pointcut="com.xyz.myapp.CommonPointcuts.dataAccessOperation()",
        returning="retVal")
    public void doAccessCheck(Object retVal) {
        // ...
    }
}
```

## Advice - After Throwing Advice
- Aspect 내에 조인포인트(메소드 실행) 에서 Exception 이 발생한 후에 실행을 위한 @AfterThrowing Advice 를 다음과 같이 선언한다.

```java
@Aspect
public class AfterThrowingExample {

    @AfterThrowing("com.xyz.myapp.CommonPointcuts.dataAccessOperation()")
    public void doRecoveryActions() {
        // ...
    }
}
```

- 원하는 타입의 Exception이 발생할 때만 매칭이 되고, 발생한 Exception에 접근하기를 원한다면 throwing 속성을 추가할 수 있다.

```java
@Aspect
public class AfterThrowingExample {

    @AfterThrowing(
        pointcut="com.xyz.myapp.CommonPointcuts.dataAccessOperation()",
        throwing="ex")
    public void doRecoveryActions(DataAccessException ex) {
        // ...
    }
}
```

## Advice - After (Finally) Advice
- Aspect 내에 조인 포인트(메소드 실행)에서 종료될 때 실행을 위한 `@After` Advice를 다음과 같이 선언한다.
- try-catch 구문의 finally 구문과 유사하기 때문에 메소드 실행중에 exception이 발생하더라도 실행한다.

```java
@Aspect
public class AfterFinallyExample {

    @After("com.xyz.myapp.CommonPointcuts.dataAccessOperation()")
    public void doReleaseLock() {
        // ...
    }
}
```

## Advice - Around Advice
- 메소드 실행의 전, 후에 advice를 실행할 수 있는 기회를 제공한다.
- 심지어 대상 메소드가 실행하거나 하지 않도록 제어할 수도 있다.
- Around Advice는 Object를 반환해야 하고, 첫 번째 인자는 `ProceedingJoinPoint`이어야 한다.
- `ProceedingJoinPoint`의 `proceed()`를 호출하면 타겟 메소드가 실행된다.

```java
@Aspect
public class AroundExample {

    @Around("com.xyz.myapp.CommonPointcuts.businessService()")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
        // start stopwatch
        Object retVal = pjp.proceed();
        // stop stopwatch
        return retVal;
    }
}
```

## Advice - JoinPoint 활용하기
모든 Advice 메소드에는 첫번째 인자로 `JoinPoint`를 받을 수 있다. (Around Advice는 `JoinPoint`의 서브 클래스인 `ProceedingJoinPoint`를 반드시 사용해야 한다.)

### JoinPoint의 메소드
- `getArgs()` : 타겟 메소드의 인자
- `getThis()` : 프록시 객체
- `getTarget()` : 타겟 객체
- `getSignature()` : 타겟 객체의 메소드 시그니쳐
- `toString()` : 타겟 객체의 메소드 정보

## Advice - Advice에 파라미터 넘기기
- args 포인트컷 지정자를 이용해서 `Advice`에 파라미터를 넘길 수 있다.

```java
@Before("com.xyz.myapp.CommonPointcuts.dataAccessOperation() && args(account,..)")
public void validateAccount(Account account) {
    // ...
}
```

- args(account,..) 표현식은 두 가지 의미를 내포한다.
  - 1개 이상의 파라미터를 받는 메소드 실행에 매칭, 첫 번재 인자는 Account 클래스의 인스턴스여야 한다.
  - Account 객체는 Advice의 account 파라미터에 바인딩한다.
- 포인트컷과 Advice를 분리해서 선언하는 경우는 다음과 같이 설정할 수 잇다.

```java
@Pointcut("com.xyz.myapp.CommonPointcuts.dataAccessOperation() && args(account,..)")
private void accountDataAccessOperation(Account account) {}

@Before("accountDataAccessOperation(account)")
public void validateAccount(Account account) {
    // ...
}
```

## Advice - Custom Annotation 매칭
- Annotation 을 기준으로 매칭한 경우의 예제는 다음과 같다.
- `Auditable.java` 로 Annotation 을 작성한다.

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Auditable {
    AuditCode value();
}
```

- `@annotation` 포인트컷 지정자로 설정된 Annotation을 Advice 파라미터로 참조할 수 있다.

```java
@Before("com.xyz.lib.Pointcuts.anyPublicMethod() && @annotation(auditable)")
public void audit(Auditable auditable) {
    AuditCode code = auditable.value();
    // ...
}
```

## Advice - 파라미터와 제네릭
- Spring AOP는 제네릭도 지원한다.

```java
public interface Sample<T> {
    void sampleGenericMethod(T param);
    void sampleGenericCollectionMethod(Collection<T> param);
}
```

- Advice의 파라미터의 타입으로 매칭을 제한할 수 있다.

```java
@Before("execution(* ..Sample+.sampleGenericMethod(*)) && args(param)")
public void beforeSampleMethod(MyType param) {
    // Advice implementation
}
```

- 아래의 Collection은 내부의 모든 엘리먼트를 검사해야 하고 null이 포함되면 결정할 수 없기 때문에 **지원하지 않는다.**

```java
@Before("execution(* ..Sample+.sampleGenericCollectionMethod(*)) && args(param)")
public void beforeSampleMethod(Collection<MyType> param) {
    // Advice implementation
}
```

- 꼭 해야 한다면, Advice의 파라미터 타입을 `Collection<?>`으로 지정하고 Advice 내에서 검사할 수 있다.

## Advice - argNames 속성
- 포인트컷 표현식에서 파라미터 이름으로 매칭하는 방법을 제공한다.
- `@Pointcut` 과 Advice 에는 모두 argNames 속성을 옵션으로 제공한다.

```java
@Before(value="com.xyz.lib.Pointcuts.anyPublicMethod() && target(bean) && @annotation(auditable)",
        argNames="bean,auditable")
public void audit(Object bean, Auditable auditable) {
    AuditCode code = auditable.value();
    // ... use code and bean
}
```

## Advice - Argument 로 proceed 호출
Around Advice에서 Argument를 넘기는 방법은 다음과 같다.

```java
@Around("execution(List<Account> find*(..)) && " +
        "com.xyz.myapp.CommonPointcuts.inDataAccessLayer() && " +
        "args(accountHolderNamePattern)")
public Object preProcessQueryPattern(ProceedingJoinPoint pjp, String accountHolderNamePattern) throws Throwable {

    String newPattern = preProcess(accountHolderNamePattern);
    return pjp.proceed(new Object[] {newPattern});
}
```

## Advice - Ordering
- 같은 조인포인트에 여러 Advice가 적용된다면 `org.springframework.core.Ordered`를 implements 하거나 `@Order`로 우선순위를 결정할 수 있다.
- Order의 우선순위는 숫자가 낮을 수록 높은 우선순위를 가진다.

## Introductions
- Introduction을 사용하면 adviced 된 객체를 특정 인터페이스의 구현체인 것처럼 동작하게 만들 수 있다.

```java
@Aspect
@Component
public class UsageTracking {

    @DeclareParents(value="com.xzy.myapp.service.*+", defaultImpl=DefaultUsageTracked.class)
    public static UsageTracked mixin;

    @Before("com.xyz.myapp.CommonPointcuts.businessService() && this(usageTracked)")
    public void recordUsage(UsageTracked usageTracked) {
        usageTracked.incrementUseCount();
    }

}
```

- `@DeclareParents`를 통해 introduction을 생성할 수 있으며 value 값인 `com.xyz.myapp.service.*+` 패키지에 속한 bean들이 `UsageTracked` 인터페이스의 구현체가 된다.
- 다음의 코드로 `UsageTracked` 의 구현체인지 확인할 수 있다.

```java
UsageTracked usageTracked = (UsageTracked) context.getBean("myService");
```

## AOP 선택 - Spring AOP vs Full AspectJ
||Spring AOP|AspectJ|
|--|--|--|
|구현|순수 자바|자바 언어 확장 사용|
|Goal|Simple Solution|Complete Solution|
|특징|별도의 컴파일 과정 불필요|AspectJ compiler(ajc)가 필요|
|Weaving|Runtime weaving|compile-time, post-compile, load-time weaving 지원|
|대상|Spring Container에 의해 관리되는 `Spring Bean`|모든 객체들|
|JoinPoint|Method 실행시에만 가능|Method 실행시, Constructor 실행시, field 참조시, field 할당시 등등|
|성능|비교적 느리다|비교적 빠르다|

## Spring AOP 선택 - @AspectJ vs XML
- XML 설정은 순수한 POJO를 지원할 수 있다.
- XML 설정파일에서 Aspect의 설정 내역을 명시적으로 확인할 수 있다.
- XML과 Aspect의 설정이 분리되어 완벽하게 캡슐화되지 않는 단점이 있다.
- XML 설정은 @AspectJ에 비해 포인트컷 표현식에 제약이 있다.

## Spring AOP Proxies
- Spring AOP는 JDK Proxy와 CGLIB을 활용하여 AOP 기능을 제공한다.
- Target 메소드가 실행되는 시점에 IoC 컨테이너에 의해 Proxy 빈을 생성한다. (Runtime Weaving)
- TargetObject(스프링 빈)가 인터페이스를 구현한 경우 JDK Proxy를 사용하고 그렇지 않은 경우는 CGLIB Proxy를 사용한다.

![](https://velog.velcdn.com/images/songs4805/post/c7f7695f-c08d-4c02-8c7c-128238aebc87/image.png)

## CGLIB 프록시 강제
- 인터페이스의 존재와 상관없이 CGLIB을 사용하도록 설정하려면 proxy-target-class를 사용할 수 있다.

```xml
<aop:config proxy-target-class="true">
    <!-- other beans defined here... -->
</aop:config>
```

- `aspectj-autoproxy`를 사용하는 경우는 다음과 같이 설정한다.

```xml
<aop:aspectj-autoproxy proxy-target-class="true"/>
```

- `@EnableAspectJAutoProxy` 어노테이션은 다음과 같이 사용할 수 있다.

```java
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AopConfig {
}
```

## AOP 가 동작하지 않는 경우
스프링 빈 내부에서 내부 메소드를 실행하는 경우, Proxy 가 개입할 수 없기 때문에 AOP 가 동작하지 않는다.

### Aspect 설정
```java
@Aspect
@Component
public class AspectClass {

    @Around("@annotation(testAnnotation)")
    public void test(ProceedingJoinPoint pjp, TestAnnotation testAnnotation) {
        try{
            System.out.println("testAnnotaion 실행");
            pjp.proceed();
            System.out.println("testAnnotaion 종료");
        }catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
```

### 업무 코드
`businessLogic()` 메소드에서 `test1()` 메소드와 BService의 `test2()` 메소드를 호출한다.

```java
@Service
public class AService {

    @Autowired
    BService bService;

    public void businessLogic() {
        test1();
        bService.test2();
    }

    @TestAnnotation
    public void test1() {
        System.out.println("test1");
    }

}

@Service
public class BService {

    @TestAnnotation
    public void test2() {
        System.out.println("test2");
    }

}
```

### 실행 결과
- 예상

```log
testAnnotation 실행
test1
testAnnotation 종료
testAnnotation 실행
test2
testAnnotation 종료
```

- 실제 결과

```log
test1
testAnnotation 실행
test2
testAnnotation 종료
```

## Spring Framework에서 AOP 사용의 예
- Transaction Management
- Cache Abstraction