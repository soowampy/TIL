import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

import static java.time.Duration.ofMillis;
import static java.time.Duration.ofMinutes;
import static org.junit.jupiter.api.Assertions.*;

class AssertionsTest {

    private final Calculator calculator = new Calculator();
    private final Person person = new Person("suwan", "yu");

    @Test
    void standardAssertions() {
        assertEquals(2, calculator.add(1, 1));
        assertEquals(4, calculator.multiply(2, 2),
                "실패 메세지는 마지막 파라미터에 넣는다.");
        assertTrue('a' < 'b', () -> "새롭게 자바 8 람다 표현식으로 추가된 메소드");
    }

    @Test
    void groupedAssertions() {
        assertAll("person",
                () -> assertEquals("Jane", person.getFirstName()),
                () -> assertEquals("Doe", person.getLastName()));
    }

    @Test
    void exceptionTesting() {
        Exception exception = assertThrows(ArithmeticException.class, () ->
                calculator.divide(1, 0));
        assertEquals("/ by zero", exception.getMessage());
    }

    @Test
    void timeoutNotExceeded() {
        // 아래의 assertion은 성공.
        assertTimeout(ofMinutes(2), () -> {
            // 2분 미만으로 실행되는 동작 실행
        });
    }

    @Test
    void timeoutNotExceededWithResult() {
        //아래의 assertion은 성공하면서 supplied 객체를 반환한다.
        String actualResult = assertTimeout(ofMinutes(2), () -> {
            return "a result";
        });
        assertEquals("a result", actualResult);
    }

    @Test
    void timeoutExceeded() {
        //이 테스트는 fail이 난다.
        assertTimeout(ofMillis(10), () -> {
            // 10ms 이상 걸리는 작업
            Thread.sleep(100);
        });
    }

    @Test
    void timeoutExceededWithPreemptiveTermination() {
        //이 테스트는 fail이 난다.
        assertTimeoutPreemptively(ofMillis(10), () -> {
            // 10ms 이상 걸리는 작업
            new CountDownLatch(1).await();
        });
    }
}