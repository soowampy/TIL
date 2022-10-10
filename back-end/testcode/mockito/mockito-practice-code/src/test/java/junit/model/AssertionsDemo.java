package junit.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AssertionsDemo {

    private final Dog fido = new Dog("Fido", 5.25F);
    private final Dog fidosClone = new Dog("Fido", 5.25F);

    @Test
    void naming_test() {
        Assertions.assertThat(fido).isEqualTo(fidosClone);
    }

    @Test
    void boolean_assertions() {
        assertThat("".isEmpty()).isTrue();
    }

    @Test
    void iterable_array_assertions() {
        List<String> list = Arrays.asList("1", "2", "3");

    }
}