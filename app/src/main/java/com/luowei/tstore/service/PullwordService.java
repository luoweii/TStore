package com.luowei.tstore.service;

import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.luowei.tstore.service.net.HttpCallBack;
import com.luowei.tstore.service.net.HttpConnection;

/**
 * Created by luowei on 2015/8/17.
 */
public class PullwordService {
    public static void pullword(String api,String word, RequestCallBack<?> httpCallBack) {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("source",word);
        params.addQueryStringParameter("param1","0");
        params.addQueryStringParameter("param2","1");
        HttpConnection.get(api, params, httpCallBack);
    }
}
