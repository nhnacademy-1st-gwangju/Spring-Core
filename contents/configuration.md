# Java Configuration
## Java Based Bean Configuration
- spring-javaconfig 라는 모듈이 개발되어 2009년 Spring Framework 3.0 부터 core에 포함하여 제공하기 시작했다. (XML 지옥에서 벗어날 수 있었음...)
- https://docs.spring.io/spring-javaconfig/docs/1.0.0.M4/reference/htmlsingle/spring-javaconfig-reference.html
- Spring IoC 컨테이너 설정을 순수한 Java로만 작성할 수 있는 도구이다.

## Spring JavaConfig 클래스 작성
- Spring JavaConfig 클래스에는 `@Configuration` 어노테이션을 설정해야 한다.
- Spring JavaConfig 클래스는 `@Bean` 어노테이션을 설정한 메소드로 구성된다.

## Bean 생성
- @Bean (Java Config)
```java
@Configuration
public class JavaConfig {
    @Bean/*(name = "dbms")*/
    public String dbms() {
        return new String("MYSQL");
    }
}
```
위의 설정은 다음의 XML 설정과 동일하다.

```xml
<bean id="dbms" class="java.lang.String">
    <constructor-arg type="java.lang.String" value="MYSQL" />
</bean>
```

- default

```java
public interface BaseJavaConfig {
    @Bean
    default String dbms() {
        return new String("MYSQL");
    }
}

@Configuration
public class JavaConfig implements BaseJavaConfig{
}
```
- default 메소드를 가진 인터페이스를 구현하는 방식으로 두 설정을 합성할 수 있다.

## AnnotationConfigApplicationContext
AnnotationConfigApplicationContext의 생성자 파라미터로 받을 수 있는 클래스는 다음과 같다.

`AnnotationConfigApplicationContext(Class<?>... componentClasses)`
- `@Configuration` 설정한 클래스
- `@Component` 설정한 클래스

AnnotationConfigApplicationContext의 생성자 파라미터에 basePackages(문자열)를 사용하면 패키지 하위의 Component 설정한 클래스를 검색하여 등록한다.

`AnnotationConfigApplicationContext(String... basePackages)`

JSR-330 Annotation을 처리할 수 있다. (`@Inject`)

```java
// 다음과 같이 JavaConfiguration에서 XML 설정을 사용할 수 있다.
@Configuration
@ImportResource("classpath:/beans.xml")
public class MainConfig {
}
```

## Bean Lifecycle
- `@Bean` Annotation에 빈의 생성, 소멸의 콜백 메소드를 지정할 수 있다.
- XML 설정의 init-method, destroy-method 속성과 동일한 기능을 제공한다.

```java
public class GreetingService {
    private final Greeter greeter;

    @Autowired
    public GreetingService(@GreeterQualifier(language = Language.KOREAN, dummy = false) Greeter greeter) {
        this.greeter = greeter;
    }

    public void greet() {
        greeter.sayHello();
    }

    public void init() {
        System.out.println(this.getClass().getCanonicalName()+ ": init!!");
    }

    public void cleanup() {
        System.out.println(this.getClass().getCanonicalName()+ ": cleanup!!");
    }
}


@Configuration
public class BeanConfig {
    @Bean(initMethod = "init", destroyMethod = "cleanup")
    public GreetingService greetingService(Greeter greeter) {
        GreetingService greetingService = new GreetingService(greeter);
        return greetingService;
    }
}
```

### 암묵적인 destroyMethod
- 빈 클래스에 public인 close, shutdown 메소드가 존재하면 자동으로 소멸 callback으로 등록된다.
- 이 동작을 비활성화 하려면 다음과 같이 `@Bean(destroyMethod="")`를 설정하면 된다.

```java
@Bean(destroyMethod="")
public DataSource dataSource() throws NamingException {
    return (DataSource) jndiTemplate.lookup("MyDS");
}
```

## Bean Scope
- `@Bean` annotation을 사용한 경우, `@Scope`를 설정해서 scope를 설정할 수 있다.

```java
@Bean
@Scope("prototype")
public Greeter koreanGreeter() {
  return new KoreanGreeter();
}
```

## Bean Naming
- java configuration에서 선언한 스프링 빈의 이름은 메소드 이름이다.

```java
@Bean
public Greeter koreanGreeter() {
    return new KoreanGreeter();
}
```

- 이 이름은 `@Bean` annotation의 name 속성으로 변경할 수 있다.

```java
@Bean(name="korean")
public Greeter koreanGreeter() {
    return new KoreanGreeter();
}
```

## Bean Aliasing
- java configuration에서 선언한 스프링 빈의 이름을 2개 이상 설정할 수 있다.

```java
// 아래 스프링빈의 alias 는 korean, koreanGreeter 입니다.
@Bean(name={"korean", "koreanGreeter"})
public Greeter koreanGreeter() {
    
}
```

## Bean Dependencies
### 메서드 파라미터 전달
```java
@Configuration
public class JavaConfig {
    @Bean
    public ARepository aRepository() {
        return new ARepositoryImpl();
    }

    @Bean
    public AService aService(ARepository aRepository) {
        return new ASergice(aRepository);
    }
}
```

### 메서드 호출
- 빈이 같은 클래스 내에서 선언된 경우에만 사용할 수 있다.

```java
@Configuration
public class JavaConfig {
    @Bean
    public ARepository aRepository() {
        return new ARepositoryImpl();
    }

    // with method parameter
    @Bean
    public AService aService() {
        return new AService(aRepository());
    }
}
```

### 메서드 호출에서 주의할 것
- 아래 코드에서 `aRepository()` 메소드가 두 번 호출되었다.
- `ARepository` 빈은 몇 개가 만들어질까?
- `@Configuration` annotation에서 의존성 주입을 위해 호출된 메소드는 CGLIB 기술을 사용하여 scope에 따라 스프링 빈을 반환한다.

```java
@Configuration
public class JavaConfig {
    @Bean
    @Scope("singleton")
    public ARepository aRepository() {
        return new ARepositoryImpl();
    }

    @Bean
    public AService aService() {
        return new AService(aRepository());
    }
    
    @Bean
    public BService bService() {
        return new ASergice(aRepository());
    }
}
```

### @Autowired
- 다른 곳에서 설정된 Bean으로 의존성 주입을 하기 위해 `@Autowired` annotation을 사용할 수 있다.

```java
@Configuration
public class JavaConfig {
    @Autowired
    private ARepository aRepository;

    @Bean
    public AService aService() {
        return new AService(aRepository);
    }
    
    @Bean
    public BService bService() {
        return new ASergice(aRepository);
    }
}
```

### 생성자 주입
- 다른 곳에서 설정된 Bean으로 의존성 주입을 하기 위해 생성자를 사용할 수 있다.

```java
@Configuration
public class JavaConfig {
    @Autowired
    private ARepositoryConfig aRepositoryConfig;

    @Bean
    public AService aService() {
        return new AService(aRepositoryConfig.aRepository());
    }
}
```

### fully qualifying 주입
- 다른 곳에서 설정된 Bean으로 의존성 주입을 하기 위해 Configuration 객체 자체를 가져올 수 있다.

```java
@Configuration
public class JavaConfig {
    private final ARepository aRepository;
    
    public JavaConfig(ARepository aRepository) {
        this.aRepository = aRepository;
    }

    @Bean
    public AService aService() {
        return new AService(aRepository);
    }
    
    @Bean
    public BService bService() {
        return new ASergice(aRepository);
    }
}
```

## 빈 생성의 조건
### @Conditional 어노테이션
- 조건에 따라 `@Configuration`이나 `@Bean`이 동작하거나 동작하지 않도록 설정할 수 있다. (Spring 4.x)
- `@Conditional` 은 Condition 인터페이스 구현을 설정해야 한다.
- Condition 인터페이스는 matches 메소드를 제공하는데, 반환값이 true이면 설정은 동작한다. 반대인 경우 동작하지 않는다.

```java
@Conditional(TestCondition.class)
@Bean
public GreetingService greetingService(Greeter greeter) {
    GreetingService greetingService = new GreetingService(greeter);
    return greetingProcessor;
}

class TestCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return true;
    }
}
```
- `@Profile` 어노테이션이 `@Conditional`을 활용한 대표적인 예이다.

## Bean Scanning
- 실제로 XML이나 Java Configuration에서 비즈니스 로직을 포함한 모든 bean을 일일이 등록해서 사용해야 하나?
- Bean Scanning을 이용한 방법
  - Bean Scanning = Component Scanning = Classpath Scanning

## Component Scan
- `@Configuration`을 지정한 클래스에 `@ComponentScan`을 설정하여 스캐닝을 활성화 할 수 있다.

```java
@Configuration
@ComponentScan(basePackages = "com.nhnacademy.edu.springframework.greeting")
public class BeanConfig {
    // ...생략...
}
```

## Stereotype annotations
### Bean Scanning의 대상이 되는 어노테이션들
- `@Configuration`
- `@Component` : 기본 스프링 관리 컴포넌트
- `@Controller` : Spring Web MVC 에서 Controller
- `@Service` : Service layer의 컴포넌트 마커
- `@Repository` : Data Access Object를 의미하는 marker 어노테이션 (Exception Translation 기능 제공)

## Component Scan - Filter
- `@ComponentScan` 어노테이션은 includeFilters 와 excludeFilters 속성으로 스캔할 대상 빈을 선별한다.

### includeFilters와 excludeFilters의 사용 예
- 정규식으로 "*Stub.*Repository " 는 포함하고 Repository.class 어노테이션이 설정된 클래스는 검색에서 제외한다.

```java
@Configuration
@ComponentScan(basePackages = "org.example",
        includeFilters = @Filter(type = FilterType.REGEX, pattern = ".*Stub.*Repository"),
        excludeFilters = @Filter(Repository.class))
public class AppConfig {
    // ...
}
```

|Filter type|Example Expression|설명|
|--|--|--|
|annotation(default)|org.example.SomeAnnotation|어노테이션이나 메타 어노테이션이 설정된 컴포넌트|
|assignable|org.example.SomeClass|특정 클래스를 상속/구현한 컴포넌트|
|aspectj|org.example..*Service+|AspectJ 표현식에 대응 되는 컴포넌트|
|regex|org\.example\.Default.*|정규식 표현에 대응되는 컴포넌트|
|custom|org.example.MyTypeFilter|org.springframework.core.type.TypeFilter 인터페이스의 커스텀 구현체|

## Component 내부에서 Bean 사용
- `@Component` 클래스에서도 `@Configuration` 과 동일하게 `@Bean` 을 선언할 수 있다.
- `@Configuration` 클래스에서 작성한 것과 마찬가지로 `@Scope`, `@Qualifier`, `@Lazy` 등을 사용할 수 있다.

```java
@Component
public class FactoryMethodComponent {

    @Bean
    @Qualifier("public")
    public TestBean publicInstance() {
        return new TestBean("publicInstance");
    }

    public void doWork() {
        // Component method implementation omitted
    }
}
```

## @Component 빈 이름
- `@ComponentScan` 으로 `@Component` 빈을 설정할때 빈의 이름은 BeanNameGenerator 전략으로 생성이 된다.
- `@Component` 의 이름이 지정되지 않으면 기본 BeanNameGenerator 가 소문자화된 클래스 이름으로 생성한다.

### @Component 빈 이름
- 아래와 같이 설정하면 빈 이름은 simpleMovieLister로 지정된다.

```java
@Service
public class SimpleMovieLister {
    // ...
}
```

### @Component 빈 이름 지정 예
- 아래와 같이 이름을 지정하면 빈 이름은 myMovieLister로 지정된다.

```java
@Service("myMovieLister")
public class SimpleMovieLister {
    // ...
}
```

- 기본 BeanNameGenerator 의 방식과 다른 이름을 쓰고 싶다면 BeanNameGenerator를 재정의 할 수 있다.
- 개발자가 정의한 BeanNameGenerator 를 지정하고 싶으면 아래와 같이 설정할 수 있다.

```java
@Configuration
@ComponentScan(basePackages = "org.example", nameGenerator = MyNameGenerator.class)
public class AppConfig {
    // ...
}
```

## 생각해볼거리
- 어떤 기준으로 스프링 빈을 만들어야 할까?
  - ApplicationContext는 스프링 빈의 lifecycle을 관리하고 의존성이 있는 스프링 빈을 주입한다.
  - `@Autowired`, `@Qualifier` 애너테이션
  - ApplicationContext는 사용자가 생성한 스프링 빈 이외에도 프레임워크에서 제공하는 스프링 빈들도 있다.