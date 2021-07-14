package com.pinest94.javatest.member;

import com.pinest94.javatest.domain.Member;

import java.util.Optional;

public interface MemberService {

    Optional<Member> findById(Long memberId);
}
