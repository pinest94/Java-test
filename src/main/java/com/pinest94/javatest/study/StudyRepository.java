package com.pinest94.javatest.study;

import com.pinest94.javatest.Study;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {
}
