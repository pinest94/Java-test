package com.pinest94.javatest;

import com.pinest94.javatest.domain.Member;
import com.pinest94.javatest.member.MemberService;
import com.pinest94.javatest.study.StudyRepository;
import com.pinest94.javatest.study.StudyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudyServiceTest {

    @Mock
    MemberService memberService;

    @Mock
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

        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        when(studyRepository.save(study)).thenReturn(study);

        study = studyService.createNewStudy(1L, study);

        assertNotNull(study.getOwner());
        assertEquals(member, study.getOwner());

        // 횟수 호출 검증
        verify(memberService, times(1)).notify(study);
        verify(memberService, times(1)).notify(member);
        verify(memberService, never()).validate(any());

        // 더 이상 호출되는 메서드가 없어야할때 검증하는 메서드
        verifyNoMoreInteractions(memberService);

        // 순서 호출 검증
        InOrder inOrder = inOrder(memberService);
        inOrder.verify(memberService).notify(study);
        inOrder.verify(memberService).notify(member);

    }
}
