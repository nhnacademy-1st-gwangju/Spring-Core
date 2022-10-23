# 의존성 (Dependency)
## 의존성 정의
- 코드에서 두 모듈간의 연결의 의존성이라고 한다.
- 객체지향 언어에서 두 클래스 간의 관계를 말하기도 한다.

## Dependency (의존 관계)
- A 클래스가 B 클래스를 일시적으로 참조하는 형태이다.

![](https://velog.velcdn.com/images/songs4805/post/4f847165-2b67-4cff-af20-7631f770d46e/image.png)


```java
public class B {
    private int numB;
    
    public int getNumB() {
      return this.numB;
    }
}

public class A {
    private int numA;
    
    // add 메소드가 반환한 이후에는 B 클래스의 b 객체는 제거된다. 
    public int add(B b) {
      return numA + b.getNumB();
    }
}
```

## Association (연관 관계)
- 클래스의 필드로 다른 클래스의 객체를 가지고 있는 관계

![](https://velog.velcdn.com/images/songs4805/post/064b0947-b6f6-4fa0-9bb3-ce9a597c41c4/image.png)

```java
public class B {
    private int numB;
    
    public int getNumB() {
      return this.numB;
    }
}

public class A {
    private int numA;
    private B b;
    
    // add 메소드가 반환한 이후에도 B 클래스의 b 객체는 여전히 남아 있다.
    public int add() {
      return numA + this.b.getNumB();
    }
}
```

## Aggregation (집합 관계)
- Association의 특수한 형태
- 클래스 A와 클래스 B의 생명주기는 반드시 일치하지 않는다.

![](https://velog.velcdn.com/images/songs4805/post/bd420827-a20b-422c-8310-bed1a0de23df/image.png)

```java
public class B {
    private int numB;
    
    public int getNumB() {
      return this.numB;
    }
}

public class A {
    private int numA;
    private B b;
    
    public A(B externalB) {
        this.b = externalB;
    }
}
```

## Composition (합성 관계)
- Association의 특수한 형태
- Aggregation 보다 강결합
- 클래스 A와 클래스 B의 생명주기가 일치한다.

![](https://velog.velcdn.com/images/songs4805/post/6e469765-9008-472c-89a1-08b7c35acf9d/image.png)

```java
public class B {
}

public class A {
    private B b;
    
    public A(B externalB) {
        this.b = new B();
    }
}
```