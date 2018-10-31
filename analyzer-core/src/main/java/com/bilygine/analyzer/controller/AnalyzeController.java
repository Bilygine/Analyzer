package com.bilygine.analyzer.controller;

import com.bilygine.analyzer.analyze.Analyze;
import com.bilygine.analyzer.service.AnalyzeService;
import com.bilygine.analyzer.analyze.result.Result;
import com.bilygine.analyzer.entity.json.input.ExecuteAnalyzeInput;
import com.bilygine.analyzer.entity.model.AudioMetadata;
import com.bilygine.analyzer.io.Json;
import spark.Request;
import spark.Spark;

import java.util.List;


public class AnalyzeController implements Controller {

	private final AnalyzeService analyzeService = new AnalyzeService();

	@Override
	public void register() {
		Spark.post("/analyze/execute", (req, res) -> execute(req), Json::toJson);
		Spark.get("/analyze", (req, res) -> getAnalyzes(), Json::toJson);
		Spark.get("/analyze/result/:id", (req, res) -> getResult(req.params("id")), Json::toJson); // temp
	}

	private Result getResult(String analyzeId) {
		return analyzeService.findAnalyzeById(analyzeId).getResult();
	}

	private String execute(Request request) {
		String body = request.body();
		ExecuteAnalyzeInput analyzeInput = Json.fromJson(body, ExecuteAnalyzeInput.class);
		AudioMetadata audioMetadata = analyzeInput.getAudioMetadata();
		String path = analyzeInput.getPath();

		return analyzeService.execute(path, audioMetadata).getUniqueID();
	}

	public List<Analyze> getAnalyzes() {
		return analyzeService.getAnalyzes();
	}

}
