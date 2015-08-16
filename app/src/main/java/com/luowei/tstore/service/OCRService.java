package com.luowei.tstore.service;

import com.lidroid.xutils.http.RequestParams;
import com.luowei.tstore.service.net.HttpCallBack;
import com.luowei.tstore.service.net.HttpConnection;

/**
 * Created by luowei on 2015/8/14.
 */
public class OCRService {
    /**
     * 文字识别
     */
    public static void ocr( String api,String fromdevice, String detecttype, String imagetype,String image,HttpCallBack<?> httpCallback) {
            RequestParams params = new RequestParams();
            params.addBodyParameter("fromdevice", fromdevice);
            params.addBodyParameter("clientip", "10.10.10.0");
            params.addBodyParameter("detecttype", detecttype);
            params.addBodyParameter("imagetype", imagetype);
            params.addBodyParameter("image", image);
        HttpConnection.post(api, params, httpCallback);
    }
}
