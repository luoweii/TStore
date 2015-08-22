package com.luowei.tstore.service.message;

import java.util.List;

/**
 * Created by luowei on 2015/8/12.
 */
public class DictionaryMsg extends BaseMsg {
    public int errNum;
    public String errMsg;
    public Dictionary retData;

    public class Dictionary extends BaseMsg {
        public String from;
        public String to;
        public DictResult dict_result;
    }

    public class DictResult extends BaseMsg {
        public String word_name;
        public List<Symbol> symbols;
    }

    public class Symbol extends BaseMsg {
        public String ph_am;
        public String ph_en;
        public String ph_zh;
        public List<Part> parts;
    }

    public class Part extends BaseMsg {
        public String part;
        public List<String> means;
    }
}
