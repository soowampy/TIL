## 스트림이란

### * 스트림이란 ? 

컬렉션(배열 포함)의 요소를 하나씩 참조해서 람다식으로 처리할 수 있는 반복자이다.

```java
List<String> list = Arrays.asList("1","2","3");
Stream<String> stream = list.stream();
stream.forEach(name -> System.out.println(name));
```

### * 스트림의 특징

- 람다식으로 요소 처리 코드를 제공한다.

  - 스트림이 제공하는 대부분의 요소 처리 메소드는 `함수적 인터페이스 매개타입`을 가진다.

  - 매개값으로 __람다식__ 또는 __메소드 참조__를 대입할 수 있다.

    ``` java
    public static void main(String[] args){
        List<Student> list = Arrays.asList(
        	new Student("홍길동", 90)
        );
        
        Stream<Student> stream = list.stream();
        stream.forEach(s -> {
            String name = s.getName();
            int score = s.getScore();
            System.out.println(name + "-" + score);
        })
            
    }
    ```

    

- 내부 반복자를 사용하므로 병렬처리가 쉽다.

  - 외부 반복자 : 개발자가 코드로 직접 컬렉션 요소를 반복해서 요청하고 가져오는 패턴

  - 내부 반복자 : 컬렉션 내부에서 요소를 반복시키고 개발자 

  - 병렬 처리 : 한가지 작업을 서브 작업으로 나누고, 서브 작업들을 분리된 스레드에서 병렬적으로 처리한 후 서브 작업들의 결과들을 최종 결합하는 방법

    ```java
    List<String> list = Arrays.asList("1","2","3","4","5");
    
    // 순차처리
    Stream<String> stream = list.stream();
    stream.forEach(ParallelExample :: print);
    // 1 : main
    // 2 : main
    // 3 : main
    // 4 : main
    // 5 : main
    
    // 병렬처리
    Stream<String> parallelStream = list.parallelStream();
    parallelStream.forEach(ParallelExample :: print);
    // 1 : mian
    // 2 : ForkJoinPool.commonPool-worker-2
    // 3 : main
    // 4 : ForkJoinPool.commonPool-worker-1
    // 5 : ForkJoinPool.commonPool-worker-3
    ```

    

### * 스트림은 중간 처리와 최종 처리를 할 수 있다.

- 중간 처리 : 요소들의 매핑, 필터링, 정렬
- 최종 처리 : 반복, 카운트, 평균, 총합

```java
List<Student> studentList = Arrays.asList(
	new Student("홍길동",10);
    new Student("신용권",20);
    new Student("유미선",30);
);

studentList.stream().mapToint(Student::getScore); // 중간처리
double avg = studentList.stream().
    mapToInt(Student::getScore).
    average().// 최종처리
    getAsDouble(); 
```



## 스트림의 종류

### * 스트림이 포함된 패키지

- 스트림이 포함된 패키지

  - BaseStream : 모든 스트림에서 사용할 수 있는 공통 메소드들이 정의되어 있을 뿐 코드에서 직접적으로 사용하지는 않음.
  - 하위 스트림들 : Stream (객체요소 처리), IntStream, LongStream, DobuleStream

- 스트림 구현 객체를 얻는 방법

  | 리턴타입                                                   | 메소드(매개변수)                                             | 소스      |
  | ---------------------------------------------------------- | :----------------------------------------------------------- | --------- |
  | Stream<T>                                                  | java.util.Collection.stream()<br />java.util.Collection.parallelStream() | 컬렉션    |
  | Stream<T><br />IntStream<br />LongStream<br />DoubleStream | Arrays.stream(T[]), Stream.of(T[])<br />Arrays.stream(int[]), IntStream.of(int[])<br />Arrays.stream.(long[]), LongStream.of(long[])<br />Arrays.stream(dobule[]), DobuleStream.of(dobule[]) | 배열      |
  | IntStream                                                  | IntStream.range(int, int)<br />IntStream.rangeClosed(int int) | int 범위  |
  | LongStream                                                 | LongStream.range(long, long)<br />LongStream.rangeCLosed(long, long) | long 범위 |
  | Stream<Path>                                               | Files.find(Path, int, BipRedicate, FIleVisitOption)<br />Files.list(Path) | 디렉토리  |
  | Stream<String>                                             | Files.lines(Path, Charset)<br />BufferedREader.lines()       | 파일      |
  | DoubleStream<br />IntStream<br />LOngStream                | Random.dobules(...)<br />Random.ints()<br />Random.longs()   | 랜덤수    |

- 컬렉션으로부터 스트림 얻기

  ```java
  List<Student> studentList = Arrays.asList(
  	new Student("홍길동", 10),
      new Student("신용권", 20),
      new Student("유미선", 30)
  );
  
  Stream<Student> stream = studentList.stream();
  stream.forEach(s -> System.out.println(s.getName()));
  ```



## 스트림 파이프라인

### * 중간 처리와 최종 처리

- 리덕션 

  - 대량의 데이터를 가공해서 축소하는 것을 말한다. 합계, 평균값, 카운팅, 최대값, 최소값 들을 집계하는 것. 
  - 요소가 리덕션의 결과물로 바로 집계할 수 없을 경우 __중간 처리__가 필요하다. (중간처리 : 필터링, 매핑, 정렬, 그룹핑)
  - 중간 처리한 요소를 최종 처리해서 리덕션 결과물을 산출한다.

- 스트림은 중간 처리와 최종 처리를 `파이프라인`으로 해결한다.

  - __파이프라인__ : 스트림을 파이프처럼 이어 놓은 것을 말한다. 중간 처리 메소드는 중간 처리 된 스트림을 리턴하고 이 스트림에서 다시 중간 처리 메소드를 호출해서 파이프라인을 형성하게 된다.
  - 최종 스트림의 집계 기능이 시작되기 전까지 중간 처리는 지연된다. 최종 스트림이 시작하면 비로소 컬렉션에서 요소가 하나씩 중간 스트림에서 처리되고 최종 스트림까지 오게된다.

  ```java
  Stream<Member> maleFemaleStream = list.stream();
  Stream<Member> maleStream = maleFemaleStream.filter(m -> m.getSex()==Member.MALE);
  IntStream ageStream = maleStream.mapToInt(Member::getAge);
  OptionalDObule optionalDOuble = ageStream.average();
  double ageAvg = optionalDouble.getAsDouble();
  
  double ageAvg = list.stream() 				// 오리지널 스트림 (남자 + 여자)
      .filter(m-> m.getSex()==Member.MALE)    // 중간처리 스트림 (남자 요소)
      .mapToInt(Member::getAge)				// 중간처리 스트림 (나이 요소)
      .average()								// 최종 처리
      .getAsDOuble();
  ```

  

### * 중간 처리 메소드와 최종 처리 메소드

리턴 타입을 보면 중간 처리 메소드인지, 최종 처리 메소드인지 구분할 수 있다.

- 중간 처리 메소드 : 리턴 타입이 스트림
- 최종 처리 메소드 : 리턴 타입이 기본 타입이거나 OptionalXXX
