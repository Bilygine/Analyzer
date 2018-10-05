package com.bilygine.analyzer.steps;

import com.bilygine.analyzer.analyze.Status;

public class GoogleTranscriptionStep extends TranscriptionStep {

    /** File path on Google Storage */
    private String gsFilePath;
    private Status status;

    /**
     *
     * @param pathGoogleStorage is path to file on Google Storage with gs:// prefix
     */
    public GoogleTranscriptionStep(String pathGoogleStorage) {
        this.gsFilePath = pathGoogleStorage;
    }

    public String getName() {
        return "Google Storage Transcription";
    }

    /**
     * Run analyze on Google cloud and register all occurence
     */
    @Override
    public void run() {
        // TODO: Run Google Cloud analysis
        /** Example with hard-coded values */
        long[] timestamps = new long[] {23, 43, 56, 70};
        String[] words = new String[] {"Foo", "Bar", "Hello", "World"};
        /** Register all occurences **/
        for (int i = 0; i < 4; i++) {
            this.registerOccurence(Long.valueOf(timestamps[i]), words[i]);
        }
    }
}
