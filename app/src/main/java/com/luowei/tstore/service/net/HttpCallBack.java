package com.luowei.tstore.service.net;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.util.LogUtils;
import com.luowei.tstore.utils.JSONUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class HttpCallBack<T> extends RequestCallBack<String> {
	private JSONUtil gsonUtil = JSONUtil.getInstance();

	@Override
	public void onStart() {
	}

	@Override
	public void onSuccess(ResponseInfo<String> responseInfo) {
		LogUtils.d(responseInfo.result);
		T data = processResult(responseInfo.result);
		onSuccess(data);
	}

	private T processResult(String dataStr) {
		Type type = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		return gsonUtil.fromJson(dataStr, type);
	}

	@Override
	public void onFailure(HttpException error, String msg) {
		LogUtils.w(msg, error);
		onFailure(error.getExceptionCode(), msg);
	}

	public abstract void onSuccess(T data);

	public abstract void onFailure(int errCode, String msg);

}
