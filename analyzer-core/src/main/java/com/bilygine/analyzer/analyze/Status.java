package com.bilygine.analyzer.analyze;

public enum Status {
    WAITING, PROGRESS, SUCCEED, FAILURE;

    public String formatted() {
        return this.name().toLowerCase();
    }
}
