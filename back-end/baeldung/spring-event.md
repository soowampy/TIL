# Spring Event

ref https://www.baeldung.com/spring-events

## Event란?

- Spring의 Bean과 Bean 사이의 데이터를 전달하는 방법 중 하나
- ApplicationContext에서 제공한다
  - ApplicationEvent - 이벤트 객체 (상속받아야함)
  - ApplicationEventPublisher - 이벤트 발행자
  - ApplicationListener - 이벤트 리스너



## Custom Event

- **이벤트 생성 순서**
  1. ApplicationEvent 객체 생성
  2. 1에서 생성한 ApplicationEvent 객체를 ApplicationEventPublisher(이벤트 발행자)에게 발행 요청
  3. 이벤트가 발행되면 ApplicationListener에서 이벤트 처리



1. ApplicationEvent를 상속 받는 class 생성

```java
public class CustomSpringEvent extends ApplicationEvent {
    private String message;
    
    public CustomSpringEvent(Object source, String message) {
        super(source);
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
}
```

2. 위 이벤트를 발행하는 publisher 클래스 생성
   - publisher는 *ApplicationEventPublisher* 를 주입받고 publishEvent() 메소드를 사용하면됨

```java
@Component
public class CustomSpringEventPublisher {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    
    public void publishCustomEvent(final String message) {
        System.out.println("Publishing custom event. ");
        CustomSpringEvent customSpringEvent = new CustomSpringEvent(this, message);
        // 이벤트 발행
        applicationEventPublisher.publishEvent(customSpringEvent);
    }
} 
```

3. Listner
   - ApplicationListener 인터페이스를 구현

```java
@Component
public class CustomSpringEventListener implements ApplicationListener<CustomSpringEvent> {
    @Override
    public void onApplicationEvent(CustomSpringEvent event) {
        // 처리되는 이벤트
        System.out.println("Received spring custom event - " + event.getMessage());
    }
}
```



## 비동기식 이벤트 생성

```java
@Configuration
public class AsynchronousSpringEventsConfig {
    @Bean(name = "applicationEventMulticaster")
    public ApplicationEventMulticaster simpleApplicationEventMulticaster() {
        SimpleApplicationEventMulticaster eventMulticaster =
          new SimpleApplicationEventMulticaster();
        
        eventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
        return eventMulticaster;
    }
}
```



## 트랜잭션 바인딩 이벤트

- `@TransactionalEventListener` 를 사용하면 트랜잭션의 여러 위치에서 이벤트 리스너를 수행할 수 있다.
  - AFTER_COMMIT (default) : 트랜잭션 커밋 후 리스너 실행
  - AFTER_ROLLBACK : 트랜잭션이 롤백 될 때만 실행
  - AFTER_COMPLETION : 트랜잭션 종료 후(커밋 or 롤백) 리스너 실행
  - BEFORE_COMMIT : 트랜잭션이 커밋되기 전에 리스너 실행

```java
@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
public void handleCustom(CustomSpringEvent event) {
    System.out.println("Handling event inside a transaction BEFORE COMMIT.");
}
```



# DDD Aggregates and @DomainEvents

https://www.baeldung.com/spring-data-ddd





# 이벤트를 사용하는 이유?

- 복잡한 연관 관계... 동시에 처리할 때..
- 하나의 요청에 함께 묶여 수행되는 로직
- 하지만 이렇게 요청에 묶인 트랜잭션에서 많은 일을 수행하게 되면, 사용자가 원하는 요청의 의도와 서버에서 실제로 수행되는 로직 간의 차이가 생기게 된다.

- 트랜잭션 안의 관심사를 분리할 수 있지만, 결과적으로 결합도를 낮출 수 있는 장점이 있음
