package com.luowei.tstore.service;

import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.luowei.tstore.service.net.HttpConnection;

/**
 * Created by luowei on 2015/8/17.
 */
public class DictionaryService {
    public static void dictionary(String api, String word, RequestCallBack<?> httpCallBack) {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("query", word);
        if (word.matches("[a-zA-Z]*")) {
            params.addQueryStringParameter("from", "en");
            params.addQueryStringParameter("to", "zh");
        } else {
            params.addQueryStringParameter("from", "zh");
            params.addQueryStringParameter("to", "en");
        }
        HttpConnection.get(api, params, httpCallBack);
    }
}
