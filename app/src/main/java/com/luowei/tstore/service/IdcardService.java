package com.luowei.tstore.service;

import com.lidroid.xutils.http.RequestParams;
import com.luowei.tstore.service.net.HttpCallBack;
import com.luowei.tstore.service.net.HttpConnection;

/**
 * Created by luowei on 2015/8/17.
 */
public class IdcardService {
    public static void idcardLookup(String api,String idcard, HttpCallBack<?> httpCallBack) {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("id",idcard);
        HttpConnection.get(api, params, httpCallBack);
    }
}
