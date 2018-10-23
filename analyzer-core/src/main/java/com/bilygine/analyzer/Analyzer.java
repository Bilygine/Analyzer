package com.bilygine.analyzer;

import com.bilygine.analyzer.analyze.Analyze;
import com.bilygine.analyzer.analyze.Step;
import com.bilygine.analyzer.analyze.DefaultAnalyze;
import com.bilygine.analyzer.analyze.steps.GoogleTranscriptionStep;

import java.util.ArrayList;
import java.util.List;

public class Analyzer {

    public static void main(String args[]) {


        /** Create steps list */
        List<Step> steps = new ArrayList<>();
        /** Step - 3 | Transcription with Google */
        steps.add(new GoogleTranscriptionStep("gs://pathToMyFile"));
        /** Step - 4 | Build Results */

        /** Analyze **/
        Analyze analyze = new DefaultAnalyze(steps);
        analyze.run();
    }
}
