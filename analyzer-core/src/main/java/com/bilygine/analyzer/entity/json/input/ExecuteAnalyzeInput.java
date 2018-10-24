package com.bilygine.analyzer.entity.json.input;

import com.bilygine.analyzer.entity.model.AudioMetadata;

public class ExecuteAnalyzeInput {

	private String path;
	private AudioMetadata audioMetadata;

	public ExecuteAnalyzeInput(String path) {
		this.path = path;
	}

	public String getPath() {
		return this.path;
	}

	public AudioMetadata getAudioMetadata() {
		return this.audioMetadata;
	}
}
