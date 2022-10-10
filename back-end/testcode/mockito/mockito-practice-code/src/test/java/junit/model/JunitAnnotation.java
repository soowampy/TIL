package junit.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JunitAnnotation {
    @Test
    @DisplayName("Custom test name containing spaces")
    void testWithDisplayNameContainingSpaces() {
    }
}
