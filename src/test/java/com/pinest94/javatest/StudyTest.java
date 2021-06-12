package com.pinest94.javatest;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.springframework.boot.autoconfigure.web.servlet.ConditionalOnMissingFilterBean;

import javax.xml.ws.WebEndpoint;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumingThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

    @Test
    @DisplayName("스터디 생성 테스트 😃")
    void create_new_study() {
        Study study = new Study(10);

        assertAll(
                () -> assertNotNull(study),
                () -> assertEquals(StudyStatus.DRAFT, study.getStatus(), "스터디를 처음 만들면 상태값이 DRAFT여야 한다."),
                () -> assertTrue(study.getLimit() > 0, "스터디 참석 가능 인원은 0보다 커야 합니다.")
        );
    }

    @Test
    @Disabled
    @DisplayName("스터디 생성 예외처리 테스트 😬")
    void create_study_exception() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new Study(-10));

        String exceptionMessage = "limit은 0보다 커야합니다.";
        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    @Disabled
    @DisplayName("스터디 생성 시간 테스트 😬")
    void create_study_duration() {
        assertTimeout(Duration.ofMillis(500), () -> {
            new Study(1);
            Thread.sleep(300);
        });

        assertTimeoutPreemptively(Duration.ofMillis(3000), () -> {
            new Study(1);
            Thread.sleep(10000);
        });
    }

    @Test
    @DisplayName("조건에 따른 테스트")
    void assumeTest() {
        String env = System.getenv("TEST_ENV");

        assumingThat("LOCAL".equalsIgnoreCase(env), () -> {

            Study study = new Study(100);
            assertThat(study.getLimit()).isGreaterThan(100);
        });

        assumingThat("HANSOL".equalsIgnoreCase(env), () -> {
            Study study = new Study(20);
            assertThat(study.getLimit()).isGreaterThan(10);
        });
    }

    @Test
    @DisplayName("운영체제에 따른 테스트 - MAC, LINUX")
    @EnabledOnOs({OS.MAC, OS.LINUX})
    void testOnLinuxOrMac() {
        System.out.println("MAC or LINUX입니다");
    }

    @Test
    @DisplayName("운영체제에 따른 테스트 - Windows")
    @EnabledOnOs({OS.WINDOWS})
    void testOnOSWindows() {
        System.out.println("Windows");
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("before all");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("after all");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("Before each");
    }

    @AfterEach
    void afterEach() {
        System.out.println("After each");
    }
}