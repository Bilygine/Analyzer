package com.bilygine.analyzer.analyze;

public enum AnalyzeStatus {
    WAITING, PROGRESS, SUCCEED, FAILURE;

    public String formatted() {
        return this.name().toLowerCase();
    }
}
