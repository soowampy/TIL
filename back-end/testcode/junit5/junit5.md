# 1. 테스트의 범위
- 단위 테스트, 통합 테스트
- 테스트 클래스와 메소드
- **단위 테스트** : 응용 프로그램에서 테스트 가능한 가장 작은 소프트웨어를 실행하여 예상대로 동작하는지 확인하는 테스트
  - 일반적으로 테스트 대상 단위 크기는 클래스 또는 메소드 수준으로 정해짐
  - 단위의 크기가 작을수록 단위의 복잡성이 낮아짐
  - Java는 주로 JUnit으로 테스트
- **통합 테스트** : 단위 테스트보다 더 큰 동작을 달성하기 위해 여러 모듈들을 모아 이들이 의도대로 협력하는지 확인하는 테스트
  - 개발자가 변경할 수 없는 부분까지 묶어 검증할 때 사용
  - 스프링 부트에서는 클래스 상단에 @SpringBootTest 어노테이션을 붙어 수행


### 테스트 클래스
- 최상위 클래스, 스태틱 멤버 클래스, @Nested 클래스에 적어도 한 개의 @Test 어노테이션이 달린 테스트 메소드가 포함되어 있는 걸 말함
- 하나의 생성자가 있어야 한다

### 테스트 메소드
- `@Test`, `@RepeatedTest`, `@ParamterizedTest` , `@TestFactory`, `@TestTemplate` 같은 메타 어노테이션이 메소드에 붙여진 메소드

### 라이플사이클 메소드
- `@BeforeAll`, `@AfterAll`, `@BeforeEach`, `@AfterEach` 같은 메타 어노테이션이 메소드에 붙여진 메소드


# 2. Junit5의 주요 어노테이션
- 대부분 `junit-jupiter-api` 모듈 안의 `org.junit.jupiter.api` 패키지 안에 존재함
- `@DisplayName`, `@DisplayNameGeneration`
- `@BeforeEach`, `@AfterEach`
- `@BeforeAll`, `@AfterAll`
- `@Nested`
- `@Disabled`

## @DisplayName, @DisplayNameGeneration
- `@DisplayName` : 테스트 클래스나 테스트 메소드에 이름을 붙여줄 때 사용
- `@DisplayNameGeneration` : 클래스에 해당 애노테이션을 붙이면 @Test 메소드 이름에 언더바(\_)로 표시한 모든 부분은 공백으로 처리

## @BeforeEach, @AfterEach
- **각각 테스트 메소드**가 실행되기 전에 실행되어야 하는 메소드를 명시해줌 (테스트 메소드마다 실행)
- `@Test`, `@RepeatedTest`, `@ParameterizedTest`, `@TestFactory` 가 붙은 테스트 메서드가 실행되기 전에 실행됨
- 테스트하기 전에 필요한 목업 데이터를 미리 세팅해주기 위해 사용

## @BeforeAll, @AfterAll
- 테스트가 시작하기 전, 끝난 후 **딱 한 번만 실행**

## @Nested
- 테스트 그룹 사이의 관계를 표현할 수 있게 해줌
- Nested 테스트 클래스를 작성할 때 사용되며, static이 아닌 중첩 클래스(Inner 클래스)여야만 함

## @Disabled
- 테스트 클래스나 메소드의 테스트를 비활성화 함

# 3. `Assertions`, `@Nested`, `@ParameterizedTest`
## `Assertions`
- 테스트 케이스의 수행 결과를 판별할 수 있음
- 모든 JUnit Jupiter assertion은 정적 메소드이며, `org.junit.jupiter.api.Assertions` 클래스 안에 있음
- JUnit Jupiter가 제공해주는 assertion이 많은 테스트 시나리오에서 부족할 수 있다
  - 그럴땐 AssertJ , Hamcrest , Truth 등 써드 파티 라이브러리를 쓰는걸 추천

### assertEquals, assertTrue
- `assertTrue, assertFalse (boolean condition, String message)`
- argument로 특정 조건 및 boolean 값을 넘기고 assertTrue인 경우 false일때, assertFalse인경우 true일때, junit 에러를 발생시키며 message를 메시지로 리턴한다.
  ![](https://user-images.githubusercontent.com/58503875/194698784-2d780ae3-5dbb-4582-bfe7-5e13a13fa5cb.png)

### assertAll
- assert류의 모든 실행을 책임지는 메소드
- `assertAll(Executable... executables)`
- `Executable...` -> 여러개의 람다가 포함될 수 있음
  ![](https://user-images.githubusercontent.com/58503875/194698913-3c9a082c-b3bc-43ce-aec0-5d3ca6272f1a.png)


## `@Nested` 중첩 테스트
- `@Nested` : 여러 테스트 그룹 간의 관계를 표현할 수 있음
- 오직 non-static인 nested 클래스만 `@Nested`를 붙일 수 있다.
- `@BeforeAll` 과 `@AfterAll` 은 기본적으로 작동하지 않는다.


![width:400px height:500px](https://user-images.githubusercontent.com/58503875/194700241-5f66961a-6b14-4db9-9928-aef9153c4e69.png)![width:350px height:200px](https://user-images.githubusercontent.com/58503875/194700300-cf0e9457-7460-42eb-b9c4-ec393c341783.png)


## `@ParameterizedTest`
- junit에서 아직 실험중인 기능
- junit5와 추가적으로 다음 종속성을 추가해줘야함

```
testImplementation("org.junit.jupiter:junit-jupiter-params:5.6.2")
testRuntimeOnly("org.junit.jupiter:junit-jupiter-params:5.6.2")
```

![](https://user-images.githubusercontent.com/58503875/194701221-6677014c-2103-4ee3-a530-7cd25dc5f989.png)
![](https://user-images.githubusercontent.com/58503875/194701263-9babc426-d7b8-4fc8-9d38-842638ab63ce.png)


### **`@ValueSource`**
- 하나의 배열로 값을 정의하며 하나의 인자만 받는 파라미터화 테스트에만 적용할 수 있음
- 지원하는 타입
  - **Short**
  - **byte**
  - **int**
  - **long**
  - **float**
  - **double**
  - **char**
  - **boolean**
  - **java.lang.String**


### Null and Empty Source
- `@NullSource` : @ParameterizedTest 메소드에 null을 제공
- `@EmptySource` : String, List, Set, Map, primitive 배열 인자에 빈 값을 제공
- `@NullAndEmptySource` : @NullSource + @EmptySource
  ![](https://user-images.githubusercontent.com/58503875/194701362-4926bb1d-1af7-4d91-9519-aa9803d210bb.png)


### `@CsvSource`

- 리스트를 콤마로 구분 해 줌

![](https://user-images.githubusercontent.com/58503875/194701425-7bfe0d3f-75d5-4a2a-ba6b-f5639b75e5fd.png)
