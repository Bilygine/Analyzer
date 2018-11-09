package com.bilygine.analyzer.entity.model;

public class AudioMetadata {

	private String source;

	public AudioMetadata() {}

	public String getSource() {
		return this.source;
	}

	@Override
	public String toString() {
		return "AudioMetadata{" +
				"source='" + source + '\'' +
				'}';
	}
}
