package com.luowei.tstore.service.net;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.util.LogUtils;
import com.luowei.tstore.utils.JSONUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class HttpCallBack<T> extends RequestCallBack<String> {

	private JSONUtil gsonHelper = JSONUtil.getInstance();
	// 消息
	private String msg;
	// 错误码
	private int code;
	// JSONObject
	private JSONObject jo;

	private T cacheData;

	@Override
	public void onStart() {
	}

	@Override
	public void onSuccess(ResponseInfo<String> responseInfo) {
		T data = null;
		LogUtils.d(responseInfo.result);
		String result = responseInfo.result;
		try {
			jo = new JSONObject(result);
			if (jo.has(JSONUtil.response_code)) {
				code = JSONUtil.getInt(jo, JSONUtil.response_code);
			}
			switch (code) {
			case 200:
				data = processResult(JSONUtil.getString(jo, JSONUtil.response_detail));
				onSuccess(data);
				break;
			default:
				LogUtils.w(code + " " + JSONUtil.getString(jo, JSONUtil.response_detail));
				onFailure(code,JSONUtil.getString(jo, JSONUtil.response_detail));
				break;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onFailure(HttpException error, String msg) {
		LogUtils.w(msg, error);
		onFailure(error.getExceptionCode(), msg);
	}

	public abstract void onSuccess(T data);

	public abstract void onFailure(int errCode, String msg);

	/**
	 * @param cacheData
	 *            缓存数据
	 */
	public void onStart(T cacheData) {
	};

	private T processResult(String dataStr) {
		Type type = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		return gsonHelper.fromJson(dataStr, type);
	}

	/**
	 * @return 缓存数据
	 */
	public T getCacheData() {
		return cacheData;
	}

}
