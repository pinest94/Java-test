package com.pinest94.javatest;

public class Study {

    private StudyStatus studyStatus = StudyStatus.DRAFT;

    private int limit;

    private String name;

    public Study(int limit, String name) {
        this.limit = limit;
        this.name = name;
    }

    public Study(int limit) {
        if(limit < 0) { throw new IllegalArgumentException("limit은 0보다 커야합니다."); }
        this.limit = limit;
    }

    public StudyStatus getStatus() {
        return this.studyStatus;
    }

    public int getLimit() {
        return this.limit;
    }

    public String getName() {
        return name;
    }
}
