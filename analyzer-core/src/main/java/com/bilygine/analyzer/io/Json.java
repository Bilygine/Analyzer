package com.bilygine.analyzer.io;

import com.google.gson.Gson;

public class Json {

	private static final Gson gson = new Gson();

	public static String toJson(Object object) {
		return gson.toJson(object);
	}

	public static <T> T fromJson(String content, Class<T> clazz) {
		return gson.fromJson(content, clazz);
	}
}
