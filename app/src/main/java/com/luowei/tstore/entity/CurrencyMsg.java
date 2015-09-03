package com.luowei.tstore.entity;

/**
 * Created by luowei on 2015/8/12.
 */
public class CurrencyMsg extends BaseMsg {
    public int errNum;
    public String errMsg;
    public Currency retData;

    public class Currency extends BaseMsg {
        public String date;
        public String time;
        public String fromCurrency;
        public String amount;
        public String toCurrency;
        public String currency;
        public String convertedamount;
    }
}
