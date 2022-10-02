## 1. 프로세스와 스레드

- **프로세스 (process)** : 실행 중인 하나의 프로그램을 말한다.
- **멀티 태스킹(multi tasking)** : 두 가지 이상의 작업을 동시에 처리하는 것
  - 멀티 프로세스 : 독립적으로 프로그램들을 실행하고 여러 가지 작업 처리
  - 멀티 스레드 : 한 개의 프로그램을 실행하고 내부적으로 여러가지 작업 처리 (ex : 메신저 - 하나의 프로세스(메신저 프로세스) 내에서 문자채팅 + 파일 전송 스레드 동시에 실행)

- **메인 스레드**

  - 모든 자바 프로그램은 `메인 스레드`가 `main() 메소드`를 실행하면서 시작된다.

  - main() 메소드의 **첫 코드부터 아래로 순차적으로 실행**한다.

  - main() 메소드의 `마지막 코드를 실행`하거나 `return 문`을 만나면 **실행이 종료**된다.

    ```java
    // 코드의 실행 흐름 -> 스레드
    public static void main(String[] args) {
        String data = null;
        if (...) {
            
        }
        
        while (...) {
            
        }
        
        System.out.println("...");
    }
    ```

  - main 스레드는 작업 스레드들을 만들어서 병렬로 코드들을 실행할 수 있다. 즉 멀티 스레드를 생성해서 멀티 태스킹을 수행한다.

    ![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FWSvWN%2Fbtq52wU75bm%2FYJdyWLTFy4KcwJkdlbDvkk%2Fimg.png)

  - 프로세스의 종료

    - 싱글 스레드 : 메인 스레드가 종료하면 프로세스도 종료된다.
    - 멀티 스레드 : 실행 중인 스레드가 하나라도 있다면, 프로세스는 종료되지 않는다.



## 2. 작업 스레드 생성과 실행

- 몇 개의 작업을 병렬로 실행할지 결정

  ![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FdQyW1P%2Fbtq53YDw7fH%2FVQFlKeght9e4ECJk7YyBK1%2Fimg.png)

- 작업 스레드 생성 방법
  - Thread 클래스로부터 직접 생성
  - Thread 하위 클래스로부터 생성

- Thread 클래스로부터 직접 생성

  ```java
  // Runnable : 작업 스레드가 실행 할 수 있는 클래스를 만들겠다~
  class Task implements Runnable {
      // Runnable의 run 메소드를 재정의
      @Override
      public void run(){
          // 스레드가 실행할 코드
      }
  }
  ```

  - case 1

    ```java
    Runnable task = new Task();
    Thread thread = new Thread(task);
    ```

  - case 2

    ```java
    Thread thread = new Thread(()-> {
        // 스레드가 실행할 코드
    })
    ```

  - case 3

    ```java
    Thread thread = new Thread(new Runnable(){
        public void run() {
            // 스레드가 실행할 코드
        }
    })
    ```

  ```java
  thread.start(); // 스레드 실행 코드
  ```

  - 싱글 스레드로 구현

    ```java
    public class BeepPrintExample1{
    	public static void main(String[] args){
            // 비프음을 5번 반복해서 소리나게 하는 작업
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            for (int i= 0; i<5; i++) {
                toolkit.beep();
            	try {
                    Thread.sleep(500); // 0.5초간 머물렀다가 돌아가도록 (스레드 일시 정지 시키기)
                } catch(Exception e){}
            }
            
            // '띵' 문자열을 5번 출력하는 작업
            for (int i=0; i<5; i++){
                System.out.println("띵");
            	try {
                    Thread.sleep(500);
                } catch(Exception e){}
            }
            
            /**
            * 메인 메소드를 실행시켰을 때
            * 싱글 스레드로 구현되어있으므로
            * 1 ) 비프음이 5번 울리고
            * 2 ) '띵' 문자열이 콘솔에 출력된다
            * => 동시에 실행되지 않고 순차적으로 실행됨
            */
        }    
    }
    ```

  - 멀티 스레드로 구현

    ```java
    public class BeepPrintExample2 {
    	public static void main(String[] args){
    		// 작업 객체를 만든다
            Runnable beepTask = new BeepTask();
            
            // 스레드 객체를 만든다
            Thread thread = new Thread(beepTask);
            thread.start();
            
            // '띵' 문자열을 5번 출력하는 작업
            for (int i=0; i<5; i++){
                System.out.println("띵");
            	try {
                    Thread.sleep(500);
                } catch(Exception e){}
            }
        }    
    }
    
    // 작업 클래스
    public class BeepTask implements Runnable{
        
        @Override
        public void run() {
            // 비프음을 5번 반복해서 소리나게 하는 작업
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            for (int i= 0; i<5; i++) {
                toolkit.beep();
            	try {
                    Thread.sleep(500);
                } catch(Exception e){}
            }
        }
    }
    ```

- Thread 하위 클래스로부터 생성

  ```java
  public class WorkerThread extends Thread {
      @Override
      public void run() {
          // 스레드가 실행할 코드
      }
  }
  
  Thread thread = new WorkerThread();
  ```

  ```java
  Thread thread = new Thread() {
      public void run() {
          // 스레드가 실행할 코드
      }
  }
  ```

  ```java
  thread.start();
  ```

- 스레드의 이름
  - 메인 스레드 이름 : **main**
  - 작업 스레드 이름 : **Thread -n**





## 3. 스레드 우선순위

- 동시성과 병렬성
  - **동시성** : 멀티 작업을 위해 하나의 코어에서 멀티 스레드가 번갈아 가며 실행하는 성질
  - **병렬성** : 멀티 작업을 위해 멀티 코어에서 개별 스레드를 동시에 실행하는 성질
- 스레드 스케쥴링
  - 스레드의 개수가 코어의 수보다 많을 경우
    - 스레드를 **어떤 순서로 동시에 실행할 것인가**를 결정 -> **스레드 스케쥴링**
    - 스레드 스케쥴링에 의해 스레드들은 번갈아가며 그들의 run() 메소드를 조금씩 실행
  - 자바의 스레드 스케쥴링
    - **우선순위(Priority) 방식** (코드로 제어 가능) : 우선순위가 높은 스레드가 실행 상태를 더 많이 가지도록 스케줄링 하는 방식
    - **순환 할당 방식** (코드로 제어할 수 없음) : 시간 할당량(Time Slice)을 정해서 하나의 스레드를 정해진 시간만큼 실행하는 방식

- 스레드 우선 순위
   - 스레드들이 동시성을 가질 경우 우선적으로 실행할 수 있는 순위
   - 우선순위 1(낮음) 에서부터 10(높음) 까지 부여 (모든 스레드들은 기본적으로 5의 우선 순위를 할당)
   - 우선 순위 효과
      - 싱글 코어인 경우 : 우선 순위가 높은 스레드가 실행 기회를 더 많이 가지기 때문에 우선 순위가 낮은 스레드보다 계산 작업을 빨리 끝낸다.
      - 멀티 코어인 경우 : 쿼드 코어 경우에는 4개의 스레드가 병렬성으로 실행될 수 있기 때문에 4개 이하의 스레드를 실행할 경우에는 우선 순위 방식에는 크게 영향을 미치지 못한다. 최소한 5개 이상의 스레드가 실행되어야 우선 순위의 영향을 받는다.



## 4. 동기화 메소드와 동기화 블록

- 공유 객체를 사용할 때, 멀티 스레드가 하나의 객체를 공유해서 생기는 오류 발생

- 동기화 메소드 및 동기화 블록 - synchronized

  - 단 하나의 스레드만 실행할 수 있는 메소드 또는 블록

  - 다른 스레드는 메소드나 블록이 실행이 끝날 떄까지 대기해야 함

    ```java
    // 동기화 메소드
    public synchronized void method(){
        // 단 하나의 스레드만 실행
    }
    
    // 동기화 블록
    public void method (){
        // 여러 스레드가 실행 가능한 영역
     	
        synchronized(공유객체) {
            // 임계 영역 : 단 하나의 스레드만 실행
        }
        
        // 여러 스레더가 실행 가능한 영역
    }
    ```

    ![img](https://blog.kakaocdn.net/dn/rVRoe/btq54UACWw0/PfKZueCAhiS6Vh3YnDg1kK/img.png)
