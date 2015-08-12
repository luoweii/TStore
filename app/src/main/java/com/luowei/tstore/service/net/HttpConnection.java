/**
 *
 */
package com.luowei.tstore.service.net;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;
import com.luowei.tstore.config.AppConfig;
import com.luowei.tstore.service.message.RequestMsg;
import com.luowei.tstore.utils.JSONUtil;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * @author 骆巍
 * @date 2015-2-1
 */
public class HttpConnection {
    private static HttpUtils httpUtils;

    static {
        httpUtils = new HttpUtils();
    }

    /**
     * 异步post请求
     *
     * @param requestMsg
     * @param httpCallBack
     */
    public static void post(RequestMsg requestMsg, HttpCallBack<?> httpCallBack) {
        try {
            String url = AppConfig.HTTP_SERVER + requestMsg.getApi();
            LogUtils.d("HttpConnection(post url): " + url);
            RequestParams params = initRequestParams(requestMsg);
            httpUtils.send(HttpRequest.HttpMethod.POST, url, params, httpCallBack);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 同步post请求
     *
     * @param requestMsg
     * @param clazz
     * @return
     * @throws Exception
     */
    public static <T> T postSync(RequestMsg requestMsg, Class<?> clazz) {
        try {
            String url = AppConfig.HTTP_SERVER + requestMsg.getApi();
            LogUtils.d("HttpConnection: " + url + "\n" + requestMsg.getParamString());
            RequestParams params = initRequestParams(requestMsg);
            ResponseStream responseStream = httpUtils.sendSync(HttpRequest.HttpMethod.POST, url, params);
            if (responseStream != null) {
                String result = responseStream.readString();
                LogUtils.d(result);
                return JSONUtil.getInstance().fromJson(result, clazz);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static RequestParams initRequestParams(RequestMsg requestMsg) throws JSONException, UnsupportedEncodingException {
        RequestParams params = new RequestParams();
        Iterator<Entry<String, String>> iter = requestMsg.getParams().entrySet().iterator();
        JSONObject json = new JSONObject();
        while (iter.hasNext()) {
            Entry<String, String> entry = iter.next();
            json.put(entry.getKey(), entry.getValue());
//			params.addBodyParameter(entry.getKey(),entry.getValue());
        }
        LogUtils.d("HttpConnection(post payload): " + json.toString());
        params.setBodyEntity(new StringEntity(json.toString()));
        return params;
    }

}
