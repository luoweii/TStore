package com.luowei.tstore.service.message;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class RequestMsg extends BaseMsg {
	private String api;
	private HashMap<String, String> params = new HashMap<String, String>();

	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}

	public void put(String name, String value) {
		this.params.put(name, value);
	}

	public HashMap<String, String> getParams() {
		return params;
	}

	public String getParamString() {
		Iterator<Entry<String, String>> i = params.entrySet().iterator();
		if (!i.hasNext())
			return "";

		StringBuilder sb = new StringBuilder();
		for (;;) {
			Entry<String, String> e = i.next();
			String key = e.getKey();
			String value = e.getValue();
			sb.append(key);
			sb.append('=');
			sb.append(value);
			if (!i.hasNext())
				return sb.toString();
			sb.append("&");
		}
	}
}
