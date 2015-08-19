package com.luowei.tstore.service;

import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.luowei.tstore.service.net.HttpConnection;

/**
 * Created by luowei on 2015/8/17.
 */
public class PhoneService {
    /**
     * 手机号码归属地查询
     */
    public static void phoneLocal(String api,String num, RequestCallBack<?> httpCallBack) {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("tel",num);
        HttpConnection.get(api, params, httpCallBack);
    }
}
