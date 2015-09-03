package com.luowei.tstore.entity;

/**
 * Created by luowei on 2015/8/12.
 */
public class IdcardMsg extends BaseMsg {
    public int errNum;
    public String retMsg;
    public Idcard retData;

    public class Idcard extends BaseMsg {
        public String sex;
        public String birthday;
        public String address;
    }
}
