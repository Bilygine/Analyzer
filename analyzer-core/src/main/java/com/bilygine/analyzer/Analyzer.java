package com.bilygine.analyzer;

import com.bilygine.analyzer.analyze.Analyze;
import com.bilygine.analyzer.analyze.Step;
import com.bilygine.analyzer.analyze.DefaultAnalyze;
import com.bilygine.analyzer.steps.GoogleTranscriptionStep;

import java.util.ArrayList;
import java.util.List;

public class Analyzer {

    public static void main(String args[]) {

        /** Create steps list */
        List<Step> steps = new ArrayList<>();
        /** Step - 1 | Transcription with Google */
        steps.add(new GoogleTranscriptionStep("gs://pathToMyFile"));

        /** Analyze **/
        Analyze analyze = new DefaultAnalyze(steps);
        analyze.run();
    }
}
