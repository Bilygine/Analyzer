package com.bilygine.analyzer.steps;

import com.bilygine.analyzer.analyze.Step;
import com.bilygine.analyzer.analyze.result.ResultColumn;

import java.util.ArrayList;
import java.util.List;

public abstract class TranscriptionStep implements Step {

    private ResultColumn<String> words = new ResultColumn<>("words");
    private ResultColumn<Long> timestamps = new ResultColumn<>("timestamps");

    /**
     * Return columns as result
     * @return
     */
    @Override
    public List<ResultColumn> getResultColumns() {
        return new ArrayList<ResultColumn>(){{
            add(words);
            add(timestamps);
        }};
    }

    /**
     * Register an occurence
     * @param timestamp
     * @param word
     * @return status
     */
    public boolean registerOccurence(Long timestamp, String word) {
        this.words.add(word);
        this.timestamps.add(timestamp);
        return true;
    }

    /**
     * @return Return word column
     */
    public ResultColumn getWords() {
        return this.words;
    }

    /**
     * @return Return timestamp column
     */
    public ResultColumn getTimestamps() {
        return this.timestamps;
    }

}
