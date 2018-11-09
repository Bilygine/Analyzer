package com.bilygine.analyzer.analyze.steps;

import com.bilygine.analyzer.analyze.Step;
import com.bilygine.analyzer.analyze.result.ResultColumn;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class TranscriptionStep implements Step {

    private ResultColumn<String> words = new ResultColumn<>("words");
    private ResultColumn<Long> timestamps = new ResultColumn<>("timestamps");


    /** Entry point for all transcript implementation */
    public abstract void transcript();

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

    /**
     *
     * @return List of ResultColumns
     */
    @Override
    public List<ResultColumn> call() {
        /** Execute transcription **/
        this.transcript();
        /** Return columns */
        return new ArrayList<ResultColumn>(){{
            add(words);
            add(timestamps);
        }};
    }
}
