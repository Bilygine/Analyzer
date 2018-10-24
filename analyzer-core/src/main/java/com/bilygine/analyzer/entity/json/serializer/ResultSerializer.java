package com.bilygine.analyzer.entity.json.serializer;

import com.bilygine.analyzer.analyze.Analyze;
import com.bilygine.analyzer.analyze.result.Result;
import com.bilygine.analyzer.analyze.result.ResultColumn;
import com.bilygine.analyzer.io.Json;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ResultSerializer implements JsonSerializer<Result> {

	@Override
	public JsonElement serialize(Result result, Type type, JsonSerializationContext context) {
		JsonArray array = new JsonArray();
		for (int lineIndex = 0; lineIndex < result.getMaxColumnSize(); lineIndex++) {
			JsonObject line = new JsonObject();
			for (ResultColumn column : result.getResultColumns()) {
				line.addProperty("name", column.getName());
				line.addProperty("type", column.getTypeName());
				line.addProperty("value", column.valueAt(lineIndex).toString());
			}
		}
		return array;
	}

}