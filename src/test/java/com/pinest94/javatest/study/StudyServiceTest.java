package com.pinest94.javatest.study;

import com.pinest94.javatest.domain.Member;
import com.pinest94.javatest.domain.Study;
import com.pinest94.javatest.member.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

    @Mock
    MemberService memberService;

    @Mock
    StudyRepository studyRepository;

    @Test
    void createNewStudy() {
        StudyService studyService = new StudyService(memberService, studyRepository);

        Member member = Member.builder()
                .id(1L)
                .email("doingnow94@gmail.com")
                .build();

        when(memberService.findById(any())).thenReturn(Optional.of(member));

        Study study = Study.builder()
                .limitCount(10)
                .name("java")
                .build();

        Optional<Member> optional = memberService.findById(3L);

        assertEquals("doingnow94@gmail.com", optional.get().getEmail());

        // when demo
        when(memberService.findById(any()))
                .thenReturn(Optional.of(member))      // 1회 호출
                .thenThrow(new RuntimeException())    // 2회 호출
                .thenReturn(Optional.empty());        // 3회 호출

        // 1회 호출
        optional = memberService.findById(1L);
        assertEquals("doingnow94@gmail.com", optional.get().getEmail());

        // 2회 호출
        assertThrows(RuntimeException.class, () -> {
            memberService.findById(2L);
        });

        // 3회 호출
        optional = memberService.findById(2L);
        assertEquals(Optional.empty(), optional);
    }

}