package com.luowei.tstore.service;

import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.luowei.tstore.service.net.HttpConnection;

/**
 * Created by luowei on 2015/8/17.
 */
public class CurrencyService {
    public static void type(String api, RequestCallBack<?> httpCallBack) {
        RequestParams params = new RequestParams();
        HttpConnection.get(api, params, httpCallBack);
    }

    public static void currency(String api,String fromCurrency,String toCurrency,String amount, RequestCallBack<?> httpCallBack) {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("fromCurrency",fromCurrency);
        params.addQueryStringParameter("toCurrency",toCurrency);
        params.addQueryStringParameter("amount",amount);
        HttpConnection.get(api,params,httpCallBack);
    }
}
