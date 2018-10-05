package com.bilygine.analyzer.analyze;

import com.bilygine.analyzer.analyze.result.ResultColumn;

import java.util.List;

public interface Step extends Runnable {

    /**
     * @return the name of the steps
     */
    String getName();

    /**
     * @return columns result
     */
    List<ResultColumn> getResultColumns();
    /**
     * launch steps
     */
    void run();
}
