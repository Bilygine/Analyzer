package com.bilygine.analyzer.entity.json.input;

import com.bilygine.analyzer.entity.model.AudioMetadata;

public class CreateAnalyzeInput {

	private String username;
	private AudioMetadata metadata;
	private String audio;
	public String getUsername() { return this.username; }
	public AudioMetadata getMetadata() {
		return this.metadata;
	}
	public String getAudioPath() { return this.audio; }
}
