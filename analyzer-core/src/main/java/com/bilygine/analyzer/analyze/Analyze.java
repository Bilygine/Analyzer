package com.bilygine.analyzer.analyze;

import com.bilygine.analyzer.analyze.result.Result;

import java.util.List;

public interface Analyze extends Runnable {

    /**
     * @return the name of the analyze
     */
    String getName();

    /**
     * @return the status of the analyze
     */
    Status getStatus();

    /**
     * @return steps of the analyze
     */
    List<Step> getStep();

    /**
     * add steps to analyze
     */
    void addStep(Step step);

    /**
     * @return current analyze steps
     */
    Step getCurrentStep();

    /**
     * @return metadata
     */
    AnalyzeMetadata getMetadata();

    /**
     * @return result
     */
    Result getResult();

    /**
     *  Launch analyze
     */
    void run();

    /**
     * UniqueID
     */
    String getUniqueID();

}
