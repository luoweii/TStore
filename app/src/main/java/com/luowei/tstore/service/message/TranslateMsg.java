package com.luowei.tstore.service.message;

import java.util.List;

/**
 * Created by luowei on 2015/8/23.
 */
public class TranslateMsg extends BaseMsg {
    public int errNum;
    public String errMsg;
    public Translate retData;

    public class Translate {
        public String from;
        public String to;
        public List<TransResult> trans_result;
    }

    public class TransResult {
        public String src;
        public String dst;
    }
}
