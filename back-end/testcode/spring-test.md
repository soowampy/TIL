# Spring Test


## Spring Boot 테스트 분류

| 종류              | 요약                    | Bean 범위                           |
| ----------------- | ----------------------- | ----------------------------------- |
| `@SpringBootTest` | 전체 테스트 어노테이션  | 애플리케이션에 주입된 Bean 전체     |
| `@WebMvcTest`     | Controller Layer 테스트 | MVC 관련 Bean (Controller, Service) |
| `@DataJpaTest`    | JPA (DB I/O) 테스트     | JPA 관련 Bean (EntityManager)       |
| `@RestClientTest` | Rest API 테스트         | RestTemplate 등 일부 Bean           |
| `@JsonTest`       | Json 데이터 테스트      | Json 관련 일부 Bean                 |


-   애플리케이션 규모가 커지는 경우 테스트 시간이 길어지기 때문에, 신규 기능이나 버그 패치 등 일부 기능만 테스트하고자 할때는 WebMvcTest가 적당
-   단위 테스트를 위해 전체 테스트인 @SpringBootTest를 이용한다면 모든 Bean이 다 로드되기 때문에 많은 테스트 비용이 소모됨


## SpringBoot Test

-   프로젝트 내부에 있는 **스프링 빈을 모두 등록**하여 테스트에 필요한 의존성을 추가
-   실제 운영 환경에서 사용될 클래스들을 통합하여 테스트
    -   주로 **통합 테스트**를 할 때 많이 사용
-   기능 검증을 위한 것이 아니라, Spring에서 전체적으로 Flow가 제대로 동작하는지 검증하기 위해 사용
    ![width:480px height:300px](https://user-images.githubusercontent.com/58503875/194851361-0183aadf-38e6-44df-bee2-cae6c6806e12.png)


## WebMvcTest

-   Controller Layer만을 테스트하기 적합한 테스트 어노테이션
-   전체 애플리케이션을 실행하는 것이 아닌 **Controller만을 로드**하여 테스트를 진행할 수 있는 방법
    ![](https://user-images.githubusercontent.com/58503875/194860115-f8c6399a-c0f5-4772-813e-ed58c3c7539e.png)


## MockMvc

-   웹 어플리케이션을 애플리케이션 서버에 배포하지 않고 테스트용 MVC환경을 만들어 요청 및 전송, 응답기능을 제공해주는 유틸리티 클래스



# 4. mock



## Mock이란?

-   '테스트를 위해 만든 모형'을 의미
-   모킹(Mocking) : 테스트를 위해 실제 객체와 비슷한 모의 객체를 만드는 것
-   목업 (Mock-up) : 모킹한 객체를 메모리에서 얻어내는 과정



## Mock의 필요성

-   객체를 테스트하려면 테스트 대상 객체가 메모리에 있어야 함
-   스프링과 Junit을 이용해서 테스트 코드를 작성하다 보면 테스트 환경(database, api)을 구현하는 코드까지 작성해야 하고 실제 테스트할 코드보다 환경을 구현하는 코드가 훨씬 더 복잡해짐 -> Mock의 등장
-   생성하는 데 복잡한 객체를 테스트하기 위해 실제 객체와 비슷한 가짜 객체를 만들어 테스트에 필요한 기능만 가지도록 모킹하면 테스트가 쉬워짐



## Mockito

### 모든 Mock 객체의 행동

-   Optional 타입은 Optional.empty를 나머지 타입은 Null을 리턴한다. (즉, 비어있다 )
-   콜렉션은 비어있는 콜렉션
-   Void 메서드는 예외를 던지지 않고 아무런 일도 발생 x



### Mock을 사용하지 않았을 때 테스트 코드 작성

-   MemberRepository를 주입받아야 함 -> 테스트하는 데 시간이 소요됨
    ![](https://user-images.githubusercontent.com/58503875/194903236-8b10ba11-7a0d-43b8-ab39-84416161cb52.png)



### @Mock

-   Mock 객체를 이용하여 MemberService 의존성 해결
    ![](https://user-images.githubusercontent.com/58503875/194905973-51dbea99-9329-4305-9d2a-abc32e7d22ef.png)
-   `@Mock` 어노테이션 사용
    ![](https://user-images.githubusercontent.com/58503875/194906286-6dd22d09-b757-4b3e-9fc7-d9428117065e.png)



-   `@Mock` : mock 객체를 생성
-   `@InjectMocks`
    -   `@Mock`이 붙은 Mock 객체를 주입시킬 수 있음
    -   `@InjectMocks(Service)`, `@Mock(DAO)` 식으로 사용

## ![](https://user-images.githubusercontent.com/58503875/194907355-f44ca105-5e97-4d37-b981-86a2ac5215b1.png)



### `when` (given)

-   테스트에 필요한 Mock 객체의 동작을 지정. 이것을 Stubbing이라고 함
-   `when(mockList.get(0)).thenReturn("apple");`
-   when에 스터빙할 메소드를 넣고 그 이후에 어떤 동작을 어떻게 제어할지를 메소드 체이닝 형태로 작성

![image-20221011023826935](C:\Users\82104\AppData\Roaming\Typora\typora-user-images\image-20221011023826935.png)



### `verify` (then)

-   mock 객체에 어떤 API가 호출되었는지, 몇번 호출되었는지 확인할 수 있음
-   `verify(mockList).add("apple");`

![](https://user-images.githubusercontent.com/58503875/194927451-01fdcbdb-307d-4af0-934a-8a4fee659290.png)


### `@Spy`

-   지정한 기능들만 Mocking 하고, 나머지는 기존 기능 그대로 사용
-   @Spy는 실제 인스턴스를 사용해서 mocking을 하고, @Mock은 실제 인스턴스 없이 가상의 mock 인스턴스를 직접 만들어 사용한다.
