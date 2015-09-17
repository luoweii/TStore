package com.luowei.tstore.service;

import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.luowei.tstore.service.net.HttpConnection;

/**
 * Created by luowei on 2015/9/17.
 */
public class BelleService {
    public static void getBelle(String api,int num,int page, RequestCallBack<?> requestCallBack) {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("num",num+"");
        params.addQueryStringParameter("page",page+"");
        HttpConnection.get(api, params, requestCallBack);
    }
}
