package com.bilygine.analyzer.controller;

import com.bilygine.analyzer.analyze.Analyze;
import com.bilygine.analyzer.analyze.DefaultAnalyze;
import com.bilygine.analyzer.analyze.Step;
import com.bilygine.analyzer.analyze.steps.GoogleTranscriptionStep;
import com.bilygine.analyzer.entity.json.input.ExecuteAnalyzeInput;
import com.bilygine.analyzer.io.Json;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AnalyzeController implements Controller {

	//private final AnalyzeService

	@Override
	public void register() {
		Spark.post("/analyze/execute", (req, res) -> execute(req, res), Json::toJson);
		System.out.println("Yes registerer");
	}

	private String execute(Request request, Response response) {
		String body = request.body();
		ExecuteAnalyzeInput analyzeInput = Json.fromJson(body, ExecuteAnalyzeInput.class);
		String path = analyzeInput.getPath();

		/** Create steps list */
		List<Step> steps = new ArrayList<>();
		/** Step - 3 | Transcription with Google */
		steps.add(new GoogleTranscriptionStep(path));
		/** Step - 4 | Build Results */

		/** Analyze **/
		Analyze analyze = new DefaultAnalyze(steps);
		analyze.run();

		return "bonjour monsieurs";
	}


}
