package com.luowei.tstore.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

/**
 */
public class JSONUtil {
	private Gson gson = null;
	private static JSONUtil instance = null;

	private JSONUtil() {
		gson = new GsonBuilder().setDateFormat("yyyyMMddHHmmss").create();
	}

	public static JSONUtil getInstance() {
		if (null == instance) {
			instance = new JSONUtil();
		}

		return instance;
	}

	public String objectToJson(Object obj) {
		return gson.toJson(obj);
	}

	public <T> T fromJson(String str, Type type) {
		return gson.fromJson(str, type);
	}
}
