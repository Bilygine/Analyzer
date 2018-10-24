package com.bilygine.analyzer.analyze;

import com.bilygine.analyzer.analyze.steps.GoogleTranscriptionStep;
import com.bilygine.analyzer.entity.model.AudioMetadata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class AnalyzeService {

	private static volatile AnalyzeService INSTANCE;

	private HashMap<String, Analyze> all = new HashMap<>();

	public AnalyzeService() {

	}

	public boolean executeAnalyze(String path, AudioMetadata audioMetadata) {
		/** Create steps list */
		List<Step> steps = new ArrayList<>();
		/** Step - 3 | Transcription with Google */
		steps.add(new GoogleTranscriptionStep(path));
		/** Step - 4 | Build Results */

		/** Analyze **/
		Analyze analyze = new DefaultAnalyze(steps);
		analyze
				.getMetadata()
				.setAudioMetadata(audioMetadata);
		analyze.run();
		this.all.put(analyze.getUniqueID(), analyze);
		return true;
	}

	public List<Analyze> getAnalyzes() {
		return new ArrayList(this.all.values());
	}

	public static AnalyzeService get() {
		synchronized (AnalyzeService.class) {
			if (INSTANCE == null) {
				INSTANCE = new AnalyzeService();
			}
			return INSTANCE;
		}
	}
}
