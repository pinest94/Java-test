package com.pinest94.javatest.study;

import com.pinest94.javatest.Study;
import com.pinest94.javatest.domain.Member;
import com.pinest94.javatest.member.MemberService;

import java.util.Optional;

public class StudyService {

    private final MemberService memberService;

    private final StudyRepository repository;

    public StudyService(MemberService memberService, StudyRepository repository) {
        assert memberService != null;
        assert repository != null;
        this.memberService = memberService;
        this.repository = repository;
    }

    public Study createNewStudy(Long memberId, Study study) {
        Optional<Member> member = memberService.findById(memberId);
        study.setOwner(member.orElseThrow(() ->
                                                  new IllegalArgumentException(
                                                          "Member doesn't exist for id: '" + memberId + "'")));
        Study newStudy = repository.save(study);
        memberService.notify();
        memberService.notify(member.get());
        return repository.save(study);
    }

    public Study openStudy(Study study) {
        study.open();
        Study openStudy = repository.save(study);
        memberService.notify(openStudy);
        return openStudy;
    }

}
