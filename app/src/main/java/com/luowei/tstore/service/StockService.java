package com.luowei.tstore.service;

import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.luowei.tstore.service.net.HttpCallBack;
import com.luowei.tstore.service.net.HttpConnection;

/**
 * Created by luowei on 2015/8/17.
 */
public class StockService {
    public static void stockLookup(String api, String stockId, RequestCallBack<?> httpCallBack) {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("stockid",stockId);
        params.addQueryStringParameter("list","0");
        HttpConnection.get(api,params,httpCallBack);
    }
}
