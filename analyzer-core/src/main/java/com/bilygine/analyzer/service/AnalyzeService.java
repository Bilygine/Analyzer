package com.bilygine.analyzer.service;

import com.bilygine.analyzer.analyze.Analyze;
import com.bilygine.analyzer.analyze.DefaultAnalyze;
import com.bilygine.analyzer.analyze.Step;
import com.bilygine.analyzer.analyze.steps.GoogleTranscriptionStep;
import com.bilygine.analyzer.entity.error.AnalyzerError;
import com.bilygine.analyzer.entity.model.AudioMetadata;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalyzeService {

	private static final Logger LOGGER = LogManager.getLogger(AnalyzeService.class);
	private static final Map<String, Analyze> all = new HashMap<>();


	public Analyze execute(String path, AudioMetadata audioMetadata) {
		/** Create steps list */
		List<Step> steps = new ArrayList<>();
		/** Step - 3 | Transcription with Google */
		steps.add(new GoogleTranscriptionStep(path));
		/** Step - 4 | Build Results */

		/** Analyze **/
		Analyze analyze = new DefaultAnalyze(steps);
		analyze.getMetadata().setAudioMetadata(audioMetadata);
		analyze.run();
		this.all.put(analyze.getUniqueID(), analyze);
		return analyze;
	}

	public List<Analyze> getAnalyzes() {
		return new ArrayList(this.all.values());
	}

	public Analyze findAnalyzeById(String id) {
		if (!this.all.containsKey(id)) {
			throw new AnalyzerError("Analyze with id " + id + " does'nt exist");
		}
		return all.get(id);
	}

}
