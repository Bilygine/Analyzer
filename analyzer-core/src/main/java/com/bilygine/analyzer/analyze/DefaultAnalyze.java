package com.bilygine.analyzer.analyze;

import com.bilygine.analyzer.analyze.result.Result;
import com.bilygine.analyzer.analyze.result.ResultColumn;
import com.google.common.util.concurrent.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DefaultAnalyze implements Analyze {

    /** Logger */
    private transient static final Logger LOGGER = LogManager.getLogger(DefaultAnalyze.class);
    /** Executor service */
    private transient ExecutorService executor = Executors.newSingleThreadExecutor();
	/** UUID */
	private String uniqueID;
    /** Steps status */
    private Map<Step, Status> steps = new HashMap<>();
    /** Result */
    private Result result;
	/** Metadata */
	private AnalyzeMetadata metadata = new AnalyzeMetadata();

	private Status currentStatus;
    /**
     * @param steps
     */
    public DefaultAnalyze(List<Step> steps) {
        steps.stream().forEach(step -> { this.steps.put(step, Status.WAITING); });
        this.result = new Result();
        this.uniqueID = RandomStringUtils.randomAlphabetic(8);
    }

    @Override
    public String getName() {
        return "DefaultAnalyze";
    }

    @Override
    public Status getStatus() {
		return currentStatus;
    }

    @Override
    public List<Step> getStep() {
        return new LinkedList(this.steps.values());
    }

    @Override
    public void addStep(Step step) {
        this.steps.put(step, Status.WAITING);
    }

    /**
     *
     * @return current steps in progress
     */
    public Step getCurrentStep() {
        // TODO: return real current steps
        return steps.keySet().stream().findFirst().get();
    }

    @Override
    public AnalyzeMetadata getMetadata() {
        return this.metadata;
    }

    @Override
    public Result getResult() {
        return this.result;
    }

    @Override
    public void run() {
        metadata.setStart(System.currentTimeMillis());
        ListeningExecutorService listeningExecutor = MoreExecutors.listeningDecorator(executor);
        /** Execute steps */
        for (Map.Entry<Step, Status> entry : this.steps.entrySet()) {
        	Step currentStep = entry.getKey();
            LOGGER.info("[STEP_START]", currentStep.getName());

            ListenableFuture<List<ResultColumn>> listenableFuture = listeningExecutor.submit(currentStep);
			this.steps.put(currentStep, Status.PROGRESS);

            Futures.addCallback(listenableFuture, new FutureCallback<List<ResultColumn>>() {
                @Override
                public void onSuccess(@Nullable List<ResultColumn> resultColumns) {
                    DefaultAnalyze.this.result.addColumns(resultColumns);
					LOGGER.info("[STEP_END] ", currentStep.getName());
					steps.put(currentStep, Status.SUCCEED);
					if (isDone()) {
						metadata.setEnd(System.currentTimeMillis());
						executor.shutdown();
					}
                }

                @Override
                public void onFailure(Throwable throwable) {
					steps.put(currentStep, Status.FAILURE);
                    LOGGER.error(throwable);
					metadata.setEnd(System.currentTimeMillis());
					if (isDone()) {
						metadata.setEnd(System.currentTimeMillis());
						executor.shutdown();
					}
                }
            }, executor);
        }

        /** Display concat results */
        DefaultAnalyze.this.result.printResults();

       //
    }

    public boolean isDone() {
    	return this.steps.values().stream()
				.filter(step -> step.equals(Status.SUCCEED) || step.equals(Status.FAILURE))
				.count() == this.steps.size();
	}

	public boolean hasError() {
		return this.steps.values().stream()
				.filter(step -> step.equals(Status.FAILURE))
				.count() > 0;
	}

    public String getUniqueID () {
    	return this.uniqueID;
	}
}
