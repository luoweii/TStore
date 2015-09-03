package com.luowei.tstore.service;

import com.lidroid.xutils.http.RequestParams;
import com.luowei.tstore.service.net.HttpCallBack;
import com.luowei.tstore.service.net.HttpConnection;

/**
 * Created by luowei on 2015/9/3.
 */
public class QunaerService {
    public static void getTicketList(String api,int pageno, HttpCallBack<?> httpCallBack) {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("pageno",pageno+"");
        params.addQueryStringParameter("pagesize",20+"");
        HttpConnection.get(api,params,httpCallBack);
    }
}
