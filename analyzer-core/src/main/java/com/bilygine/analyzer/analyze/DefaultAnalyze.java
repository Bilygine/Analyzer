package com.bilygine.analyzer.analyze;

import com.bilygine.analyzer.analyze.result.Result;
import com.bilygine.analyzer.analyze.result.ResultColumn;
import com.google.common.util.concurrent.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DefaultAnalyze implements Analyze {

    /** Logger */
    private static final Logger LOGGER = LogManager.getLogger(DefaultAnalyze.class);
    /** Steps queue */
    private List<Step> steps;
    /** Status */
    private Status status;
    /** Executor service */
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    /** Result */
    private Result result;

    /**
     * @param steps
     */
    public DefaultAnalyze(List<Step> steps) {
        this.steps = steps;
        this.status = Status.NOT_RUN;
        this.result = new Result();
    }

    @Override
    public String getName() {
        return "DefaultAnalyze";
    }

    @Override
    public Status getStatus() {
        return (this.status == null) ? Status.NOT_RUN : this.status;
    }

    @Override
    public List<Step> getStep() {
        return this.steps;
    }

    @Override
    public void addStep(Step step) {
        this.steps.add(step);
    }

    /**
     *
     * @return current steps in progress
     */
    public Step getCurrentStep() {
        // TODO: return real current steps
        return steps.stream().findFirst().get();
    }

    @Override
    public AnalyzeMetadata getMetadata() {
        return null;
    }

    @Override
    public Result getResult() {
        return this.result;
    }

    @Override
    public void run() {
        ListeningExecutorService listeningExecutor = MoreExecutors.listeningDecorator(executor);
        /** Execute steps */
        for (Step currentStep : this.steps) {
            LOGGER.debug("[STEP_START]", currentStep.getName());
            ListenableFuture<List<ResultColumn>> listenableFuture = listeningExecutor.submit(currentStep);
            Futures.addCallback(listenableFuture, new FutureCallback<List<ResultColumn>>() {
                @Override
                public void onSuccess(@Nullable List<ResultColumn> resultColumns) {
                    DefaultAnalyze.this.result.addColumns(resultColumns);
                }

                @Override
                public void onFailure(Throwable throwable) {
                    LOGGER.error(throwable);
                }
            }, executor);

        }
        /** Display concat results */   
        this.result.printResults();

        executor.shutdown();
    }
}
