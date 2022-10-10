package junit.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class HelloControllerTest {
    @Autowired
    private HomeController homeController;

    @DisplayName("hello 엔드포인트 테스트")
    @Test
    void 컨텍스트_로딩_테스트() {
        assertThat(homeController).isNotNull();
    }
}

