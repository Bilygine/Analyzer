package com.bilygine.analyzer.analyze.steps;

import com.bilygine.analyzer.analyze.Status;
import com.google.api.gax.longrunning.OperationFuture;
import com.google.cloud.speech.v1.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
    public void transcript() {
        // TODO: Run Google Cloud analysis
        try (SpeechClient speech = SpeechClient.create()) {

            // Configure remote file request for Linear16
            RecognitionConfig config =
                    RecognitionConfig.newBuilder()
                            .setEncoding(RecognitionConfig.AudioEncoding.FLAC)
                            .setLanguageCode("fr-FR")
                            .setSampleRateHertz(48000)
                            .setEnableWordTimeOffsets(true)
                            .build();
            RecognitionAudio audio = RecognitionAudio.newBuilder().setUri(this.gsFilePath).build();

            // Use non-blocking call for getting file transcription
            OperationFuture<LongRunningRecognizeResponse, LongRunningRecognizeMetadata> response =
                    speech.longRunningRecognizeAsync(config, audio);
            while (!response.isDone()) {
                System.out.println("Waiting for response...");
                Thread.sleep(10000);
            }

            List<SpeechRecognitionResult> results = response.get().getResultsList();

            for (SpeechRecognitionResult result : results) {
                // There can be several alternative transcripts for a given chunk of speech. Just use the
                // first (most likely) one here.
                SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
                System.out.printf("Transcription: %s\n", alternative.getTranscript());
                for (WordInfo wordInfo : alternative.getWordsList()) {
                    System.out.println(wordInfo.getWord());
                    System.out.printf(
                            "\t%s.%s sec - %s.%s sec\n",
                            wordInfo.getStartTime().getSeconds(),
                            wordInfo.getStartTime().getNanos() / 100000000,
                            wordInfo.getEndTime().getSeconds(),
                            wordInfo.getEndTime().getNanos() / 100000000);
                    this.registerOccurence(wordInfo.getStartTime().getSeconds(), wordInfo.getWord());
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
