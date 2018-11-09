package com.bilygine.analyzer.io;

import com.bilygine.analyzer.analyze.Analyze;
import com.bilygine.analyzer.analyze.DefaultAnalyze;
import com.bilygine.analyzer.analyze.result.Result;
import com.bilygine.analyzer.entity.json.serializer.DefaultAnalyzeSerializer;
import com.bilygine.analyzer.analyze.AnalyzeMetadata;
import com.bilygine.analyzer.entity.json.serializer.ResultSerializer;
import com.google.gson.GsonBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class Json {

	private static final GsonBuilder gson = new GsonBuilder();
	private static Gson parser;

	public static void init() {
		registerTypeAdapters();
		parser = gson.create();
	}

	public static void registerTypeAdapters() {
		gson.registerTypeAdapter(DefaultAnalyze.class, new DefaultAnalyzeSerializer());
		gson.registerTypeAdapter(Result.class, new ResultSerializer());
	}

	public static String toJson(Object object) {
		return parser.toJson(object);
	}

	public static <T> T fromJson(String content, Class<T> clazz) {
		return parser.fromJson(content, clazz);
	}


	public static JsonElement toJsonTree(AnalyzeMetadata metadata, Class<AnalyzeMetadata> analyzeMetadataClass) {
		return parser.toJsonTree(metadata, analyzeMetadataClass);
	}
}
