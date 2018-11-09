package com.bilygine.analyzer.entity.json.model;

import com.bilygine.analyzer.analyze.Status;
import com.bilygine.analyzer.entity.model.AudioMetadata;

public class AnalyzeModel {
	public String id;
	public String audio;
	public AudioMetadata metadata;
	public String username;
	public long submitted;
	public String version;
	public String status = Status.WAITING.formatted();

	@Override
	public String toString() {
		return "AnalyzeModel{" +
				"id='" + id + '\'' +
				", metadata=" + metadata +
				", username='" + username + '\'' +
				", submitted=" + submitted +
				", version='" + version + '\'' +
				'}';
	}
}
