package com.luowei.tstore.service;

import com.lidroid.xutils.http.RequestParams;
import com.luowei.tstore.service.net.HttpCallBack;
import com.luowei.tstore.service.net.HttpConnection;

/**
 * Created by luowei on 2015/8/17.
 */
public class IplookupService {
    public static void iplookup(String api,String ip,HttpCallBack<?> httpCallBack) {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("ip",ip);
        HttpConnection.get(api,params,httpCallBack);
    }
}
