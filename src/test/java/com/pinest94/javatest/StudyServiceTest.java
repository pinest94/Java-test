package com.pinest94.javatest;

import com.pinest94.javatest.domain.Member;
import com.pinest94.javatest.member.MemberService;
import com.pinest94.javatest.study.StudyRepository;
import com.pinest94.javatest.study.StudyService;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@Testcontainers
public class StudyServiceTest {

    @Mock
    MemberService memberService;

    @Autowired
    StudyRepository studyRepository;

    @Test
    void createNewStudy() {
        Study study = new Study(10, "테스트");

        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = Member.builder()
                              .id(1L)
                              .email("hansol@gmail.com")
                              .build();

        // given
        given(memberService.findById(1L)).willReturn(Optional.of(member));
        given(studyRepository.save(study)).willReturn(study);

        // when
        studyService.createNewStudy(1L, study);

        // then
        then(memberService).should(times(1)).notify(study);
        then(memberService).should(times(1)).notify(member);
        then(memberService).shouldHaveNoMoreInteractions();
    }

    @Test
    @DisplayName("다른 사용자가 스터디를 볼 수 있도록 스터디를 공개한다.")
    void openStudy() {
        // Given
        StudyService studyService = new StudyService(memberService, studyRepository);
        Study study = new Study(10, "더 자바, 테스트");

        // TODO: studyRepository Mock객체 save메서드 호출 시 study 리턴하도록 실습하기
        given(studyRepository.save(study)).willReturn(study);

        // When
        Study newStudy = studyService.openStudy(study);

        // Then
        // TODO: study의 status가 OPENED로 바뀌었는지 확인.
        assertEquals(newStudy.getStatus(), StudyStatus.OPENED);

        // TODO: study의 openDateTime이 null이 아닌지 확인
        assertNotNull(newStudy.getOpenDateTime());

        // TODO: memberService의 notify(study) 메서드가 호출됐는지 확인
        then(memberService).should(times(1)).notify(study);

    }
}

