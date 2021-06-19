package com.pinest94.javatest;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumingThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudyTest {

    int value = 0;

    @Test
    void test1() {
        System.out.println(this);
        System.out.println(value++);
    }

    @Test
    void test2() {
        System.out.println(this);
        System.out.println(value++);
    }


    @Test
    @DisplayName("스터디 생성 테스트 😃")
    // @Tag("fast")
    @FastTest
    @Disabled
    void create_new_study() {
        Study study = new Study(10);

        assertAll(
                () -> assertNotNull(study),
                () -> assertEquals(StudyStatus.DRAFT, study.getStatus(), "스터디를 처음 만들면 상태값이 DRAFT여야 한다."),
                () -> assertTrue(study.getLimit() > 0, "스터디 참석 가능 인원은 0보다 커야 합니다.")
        );
    }

    @Order(5)
    @Test
    @DisplayName("스터디 생성 예외처리 테스트 😬")
    // @Tag("slow")
    @SlowTest
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

    @Order(1)
    @DisplayName("반복 테스트")
    @RepeatedTest(value = 10, name = "{displayName}, {currentRepetition}/{totalRepetitions}")
    void repeatTest(RepetitionInfo repetitionInfo) {
        System.out.println("test " + repetitionInfo.getCurrentRepetition() + "/" + repetitionInfo.getTotalRepetitions());
    }

    @Order(2)
    @ParameterizedTest(name = "{index}, message = {0}")
    @ValueSource(ints = {10, 20, 40, 80, 100})
    @NullAndEmptySource
    @Disabled
    void parameterizedTest(@ConvertWith(StudyConverter.class) Study study) {
        System.out.println(study.getLimit());
    }

    static class StudyConverter extends SimpleArgumentConverter {

        @Override
        protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
            assertEquals(Study.class, targetType, "Can only convert to Study");
            return new Study(Integer.parseInt(source.toString()));
        }
    }

    @Order(3)
    @ParameterizedTest(name = "{index}, message = {0}")
    @CsvSource({"10, 'Java Study'", "15, 'JPA Study'", "5, 'Algo Study'"})
    void parameterizedTest2(ArgumentsAccessor argumentsAccessor) {
        Study study = new Study(argumentsAccessor.getInteger(0), argumentsAccessor.getString(1));
        System.out.println(study.getLimit() + ", " + study.getName());
    }

    @ParameterizedTest(name = "{index}, message = {0}")
    @CsvSource({"10, 'Java Study'", "15, 'JPA Study'", "5, 'Algo Study'"})
    void parameterizedTestWithAggregator(@AggregateWith(StudyAggregator.class) Study study) {
        System.out.println(study.getLimit() + ", " + study.getName());
    }

    static class StudyAggregator implements ArgumentsAggregator {

        @Override
        public Object aggregateArguments(ArgumentsAccessor argumentsAccessor, ParameterContext parameterContext) throws ArgumentsAggregationException {
            return new Study(argumentsAccessor.getInteger(0), argumentsAccessor.getString(1));
        }
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