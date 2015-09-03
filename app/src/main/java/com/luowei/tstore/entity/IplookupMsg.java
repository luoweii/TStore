package com.luowei.tstore.entity;

/**
 * Created by luowei on 2015/8/12.
 */
public class IplookupMsg extends BaseMsg {
    public int errNum;
    public String errMsg;
    public Ip retData;

    public class Ip extends BaseMsg {
        public String ip;
        public String country;
        public String province;
        public String city;
        public String district;
        public String carrier;
    }
}
