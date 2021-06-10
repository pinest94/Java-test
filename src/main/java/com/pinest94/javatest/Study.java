package com.pinest94.javatest;

public class Study {

    private StudyStatus studyStatus;

    private int limit;

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
}
