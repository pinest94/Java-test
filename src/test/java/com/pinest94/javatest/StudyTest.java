package com.pinest94.javatest;

import org.junit.jupiter.api.*;
import org.springframework.boot.autoconfigure.web.servlet.ConditionalOnMissingFilterBean;

import javax.xml.ws.WebEndpoint;

import static org.junit.jupiter.api.Assertions.*;

class StudyTest {

    @Test
    void create1() {
        Study study = new Study();
        assertNotNull(study);
        System.out.println("create1");
    }

    @Test
    @Disabled
    void create2() {
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