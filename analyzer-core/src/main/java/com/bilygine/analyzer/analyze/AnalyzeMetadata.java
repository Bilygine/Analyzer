package com.bilygine.analyzer.analyze;

import java.util.Map;

public class AnalyzeMetadata {

    /* Info */
    private String version = "1.0";

    /* Time */
    private long start = -1;
    private long end = -1;

    /* History */
    private Map<Long, String> history;

    public void setStart(long start) {
        this.start = start;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getElapsed() {
        return this.start == -1 ? 0  // Not started
                : this.end == -1 ? System.currentTimeMillis() - this.start // In progress
                : this.end - this.start; // Finished
    }
}
