# ✔️JVM

## JVM 이란

[https://coding-factory.tistory.com/827](https://coding-factory.tistory.com/827)

- 자바 가상 머신 JVM (Java Virtual Machine) : 자바 프로그램 실행환경을 만들어주는 소프트웨어
- 동작 원리
    - 자바 코드를 컴파일하여 .class 바이트 코드로 만들면 코드가 자바 가상 머신 환경에서 시행됨
    - JVM은 자바 실행 환경 JRE(Java Runtime Environment)에 포함되어 있음
    - 현재 사용하는 컴퓨터의 운영체제에 맞는 자바 실행환경 (JRE)가 설치되어 있다면 자바 가상 머신이 설치되어 있다는 뜻
- JVM의 이점
    - 하나의 바이트 코드(.class)로 모든 플랫폼에서 동작하도록 할 수 있음
- Java은 플랫폼에 종속적이지 않지만 JVM은 플랫폼에 종속적임
- JIT (Just In Time) 컴파일러
    - Interpreter 방식 → 바이트 코드를 한 줄씩 해석, 실행하는 방식
    - 인터프리터 방식은 속도가 느리다는 단점이 있음 이것을 보완하기 위해 나온 것이 JIT
    - 같은 코드를 매번 해석하지 않고, 실행할 때 컴파일을 하면서 해당 코드를 캐싱
    - 이후에는 바뀐 부분만 컴파일하고 나머지는 캐싱된 코드를 사용
    - JIT 컴파일러는 운영체제에 맞게 바이트 실행 코드로 한 번에 변환하여 실행하기 때문에 이전의 자바 해석기 방식보다 성능이 10배는 좋음

## **JVM의 구성 요소**

- JVM : 자바 프로그램을 실행하는 역할
    - 컴파일러를 통해 바이트 코드로 변환된 파일을 JVM에 로딩하여 실행
- 클래스 로더 (Class Loder) : JVM 내 (Runtime Data Area)로 Class 파일을 로드하고 링크
- 실행 엔진 (Excution Engine) : 메모리 (Runtime Data Area)에 적재된 클래스들을 기계어로 변경해 실행
- 가비지 컬렉터 (Garbage Collector) : 힙 메모리에서 참조되지 않는 개체들 제거
- 런타임 데이터 영역 (Runtime Data Area) : 자바 프로그램을 실행할 때, 데이터를 저장

## **JVM 실행 과정**

1. JVM은 OS로부터 메모리를 할당 받음
2. 컴파일러(javac)가 소스코드(.java)를 읽어 바이트 코드(.class)로 변환
3. Class Loader를 통해 Class 파일을 JVM내 Runtime Data Area로 로딩
4. 로딩된 Class 파일을 Excution Engine을 통해 해석 및 실행
5. 해석된 바이트 코드는 Runtime Data Area의 각 영역에 배치되어 수행하며 이 과정에서 Execution Engine에 의해 GC의 작동과 스레드 동기화가 이루어짐

## **JVM 메모리 구조 (런타임 데이터 영역)**

[https://coding-factory.tistory.com/828](https://coding-factory.tistory.com/828)

![img](https://camo.githubusercontent.com/dadd3ef9acaea392b9c478c5ffc3d8006a0617af6b2eb332adf6d63094e556d9/68747470733a2f2f6261636b746f6e792e6769746875622e696f2f6173736574732f696d672f706f73742f696e746572766965772f67632d352e504e47)
- JVM의 메모리 영역으로 자바 애플리케이션을 실행할 때 사용되는 데이터를 적재하는 영역
- 모든 스레드가 공유해서 사용 (GC의 대상)
    - 힙 영역 (Heap Area)
        - 런타임 시 동적으로 할당하여 사용하는 영역
        - new 연산자로 생성된 객체와 배열 저장
        - 참조가 없는 객체는 GC(가비지 컬렉터)의 대상
    - 메서드 영역 (Method Area)
        - 클래스가 사용되면 해당 클래스의 파일(.class)을 읽어들여 클래스에 대한 정보를 메서드 영역에 저장
        - 클래스와 인터페이스, 메서드, 필드, static 변수, final 변수 등이 저장되는 영역
- 스레드(Thread) 마다 하나씩 생성
    - 스택 영역 (Stack Area)
        - 스레드마다 존재하여 스레드가 시작할 때마다 할당
        - 지역변수, 매개변수, 연산 중 발생하는 임시 데이터 저장
        - 메서드 호출 시마다 개별젹 스택 생성
    - PC 레지스터 (PC Register)
        - 쓰레드가 현재 실행할 스택 프레임의 주소를 저장
    - 네이티브 메서드 스택 (Native Method Stack)
        - C/C++ 등의 Low Level 코드를 실행하는 스택
