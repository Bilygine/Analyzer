package com.bilygine.analyzer.controller;

import com.bilygine.analyzer.analyze.Analyze;
import com.bilygine.analyzer.analyze.AnalyzeService;
import com.bilygine.analyzer.analyze.result.Result;
import com.bilygine.analyzer.entity.json.input.ExecuteAnalyzeInput;
import com.bilygine.analyzer.entity.model.AudioMetadata;
import com.bilygine.analyzer.io.Json;
import spark.Request;
import spark.Spark;

import javax.naming.directory.SearchResult;
import java.util.List;


public class AnalyzeController implements Controller {

	//private final AnalyzeService
	// TODO: Here an analyze service and not Singleton ?

	@Override
	public void register() {
		Spark.post("/analyze/execute", (req, res) -> execute(req), Json::toJson);
		Spark.get("/analyze", (req, res) -> getAnalyzes(), Json::toJson);
		Spark.get("/analyze/result/:analyzeId", (req, res) -> getResult(req.params("analyzeId")), Json::toJson); //
	}

	private Result getResult(String analyzeId) {
		return AnalyzeService.get().findAnalyzeById(analyzeId).getResult();
	}

	private String execute(Request request) {
		String body = request.body();
		ExecuteAnalyzeInput analyzeInput = Json.fromJson(body, ExecuteAnalyzeInput.class);
		AudioMetadata audioMetadata = analyzeInput.getAudioMetadata();
		String path = analyzeInput.getPath();

		AnalyzeService.get().executeAnalyze(path, audioMetadata);

		return "OK";
	}

	public List<Analyze> getAnalyzes() {
		return AnalyzeService.get().getAnalyzes();
	}

}
