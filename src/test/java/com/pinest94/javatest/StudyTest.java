package com.pinest94.javatest;

import org.junit.jupiter.api.*;
import org.springframework.boot.autoconfigure.web.servlet.ConditionalOnMissingFilterBean;

import javax.xml.ws.WebEndpoint;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

    @Test
    @DisplayName("스터디 생성 테스트 😃")
    void create_new_study() {
        Study study = new Study();
        assertNotNull(study);
        System.out.println("create1");
    }

    @Test
    @Disabled
    @DisplayName("스터디 재생성 테스트 😬")
    void create_new_study_again() {
        System.out.println("create2");
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