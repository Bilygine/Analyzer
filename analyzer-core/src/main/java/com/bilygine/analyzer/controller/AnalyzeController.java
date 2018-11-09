package com.bilygine.analyzer.controller;

import com.bilygine.analyzer.analyze.Analyze;
import com.bilygine.analyzer.analyze.Status;
import com.bilygine.analyzer.entity.error.AnalyzerError;
import com.bilygine.analyzer.entity.json.input.CreateAnalyzeInput;
import com.bilygine.analyzer.entity.json.input.UpdateAnalyzeStatusInput;
import com.bilygine.analyzer.entity.json.model.AnalyzeModel;
import com.bilygine.analyzer.service.AnalyzeService;
import com.bilygine.analyzer.analyze.result.Result;
import com.bilygine.analyzer.io.Json;
import com.bilygine.analyzer.service.DatabaseService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import spark.Request;
import spark.Spark;

import java.util.List;


public class AnalyzeController implements Controller {

	private final AnalyzeService analyzeService = new AnalyzeService();
	private final DatabaseService databaseService = new DatabaseService();

	public AnalyzeController() {
		/** Firebase */
		databaseService.connect();
	}

	@Override
	public void register() {
		Spark.get("/analyze", (req, res) -> list(), Json::toJson);
		Spark.post("/analyze", (req, res) -> create(req), Json::toJson);
		Spark.post("/analyze/status/:id", (req, res) -> status(req, req.params("id")));
		Spark.post("/analyze/execute/:id", (req, res) -> execute(req.params("id")), Json::toJson);
		Spark.get("/analyze/result/:id", (req, res) -> getResult(req.params("id")), Json::toJson); // temp
	}

	private Result getResult(String analyzeId) {
		return analyzeService.findAnalyzeById(analyzeId).getResult();
	}

	private String create(Request request) {
		String body = request.body();
		CreateAnalyzeInput input = Json.fromJson(body, CreateAnalyzeInput.class);
		AnalyzeModel output = new AnalyzeModel();
		output.id = RandomStringUtils.randomAlphabetic(8);
		output.metadata = input.getMetadata();
		output.submitted = System.currentTimeMillis();
		output.username = input.getUsername();
		output.version = "0.1";
		output.audio = input.getAudioPath();
		return databaseService.createAnalyze(output);
	}

	private String execute(String id) {
		AnalyzeModel model = databaseService.findAnalyzeById(id);
		if (model == null) {
			throw new AnalyzerError("Unknown analyze id");
		}
		if (StringUtils.isBlank(model.audio) || !model.audio.startsWith("gs://")) {
			//TODO: Check if path exist on specified bucket.
			throw new AnalyzerError("Google Storage path is invalid: " + model.audio);
		}
		return analyzeService.execute(model.audio, model.metadata).getUniqueID();
	}

	public List<Analyze> list() {
		return analyzeService.getAnalyzes();
	}

	private String status(Request request, String id) {
		String body = request.body();
		UpdateAnalyzeStatusInput input = Json.fromJson(body, UpdateAnalyzeStatusInput.class);
		databaseService.updateStatus(id, Status.valueOf(input.status));
		return  "{}";
	}
}
