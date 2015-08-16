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
import com.luowei.tstore.config.Constant;
import com.luowei.tstore.utils.JSONUtil;

import java.io.InputStream;

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
     * @param httpCallBack
     */
    public static void post(String api, RequestParams params, HttpCallBack<?> httpCallBack) {
        LogUtils.d("-------------HttpConnection(post url)--------------- \n" + api);
        try {
            InputStream stream = params.getEntity().getContent();
            byte[] bytes = new byte[stream.available()];
            stream.read(bytes);
            String str = new String(bytes);
            LogUtils.d("--------------------request params------------------------\n"+str);
        } catch (Exception e) {
            e.printStackTrace();
        }

        params.addHeader(Constant.API_KEY, AppConfig.API_KEY);
        httpUtils.send(HttpRequest.HttpMethod.POST, api, params, httpCallBack);
    }

    /**
     * 同步post请求
     *
     * @param clazz
     * @return
     * @throws Exception
     */
    public static <T> T postSync(String api, RequestParams params, Class<?> clazz) {
        LogUtils.d("-------------HttpConnection(postSync url)--------------- \n" + api);
        try {
            InputStream stream = params.getEntity().getContent();
            byte[] bytes = new byte[stream.available()];
            stream.read(bytes);
            String str = new String(bytes);
            LogUtils.d("--------------------request params------------------------\n"+str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ResponseStream responseStream = httpUtils.sendSync(HttpRequest.HttpMethod.POST, api, params);
            if (responseStream != null) {
                String result = responseStream.readString();
                LogUtils.d("--------------------response result------------------------\n"+result);
                return JSONUtil.getInstance().fromJson(result, clazz);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
