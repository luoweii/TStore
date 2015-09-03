package com.luowei.tstore.service.net;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.util.LogUtils;
import com.luowei.tstore.utils.JSONUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class HttpCallBack<T> extends RequestCallBack<String> {

	@Override
	public void onStart() {
	}

	@Override
	public void onSuccess(ResponseInfo<String> responseInfo) {
		String result = responseInfo.result;
		LogUtils.d("--------------------response result------------------------\n"+result);
		try {
			T data = processResult(result);
			onSuccess(data);
		} catch (Exception e) {
			e.printStackTrace();
			onFailure(-1,result);
		}
	}

	private T processResult(String dataStr) {
		Type type = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		return JSONUtil.fromJson(dataStr, type);
	}

	@Override
	public void onFailure(HttpException error, String msg) {
		LogUtils.w(msg, error);
		onFailure(error.getExceptionCode(), msg);
	}

	public abstract void onSuccess(T data);

	public abstract void onFailure(int errCode, String msg);

}
