package com.luowei.tstore.entity;

import com.luowei.tstore.config.AppConfig;

import java.io.Serializable;

/**功能模块类
 * Created by luowei on 2015/8/10.
 */
public class Function implements Serializable {
    private String name;
    private String imgId;
    private String apikey= AppConfig.API_KEY;//默认是cofig里面的key
    private String server;
    private String intent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }
}
