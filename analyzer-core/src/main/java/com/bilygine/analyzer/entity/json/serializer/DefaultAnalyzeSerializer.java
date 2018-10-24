package com.bilygine.analyzer.entity.json.serializer;

import com.bilygine.analyzer.analyze.Analyze;
import com.bilygine.analyzer.analyze.AnalyzeMetadata;
import com.bilygine.analyzer.io.Json;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class DefaultAnalyzeSerializer implements JsonSerializer<Analyze> {

	@Override
	public JsonElement serialize(Analyze analyze, Type type, JsonSerializationContext context) {
		JsonObject root = new JsonObject();
		root.addProperty("id", analyze.getUniqueID());
		root.addProperty("status", analyze.getStatus().name());
		root.addProperty("elapsed", analyze.getMetadata().getElapsed());
		root.add("metadata", Json.toJsonTree(analyze.getMetadata(), AnalyzeMetadata.class));
		return root;
	}

}