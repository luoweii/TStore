package com.luowei.tstore.service.message;

import java.util.List;

/**
 * Created by luowei on 2015/8/12.
 */
public class StockMsg extends BaseMsg {
    public int errNum;
    public String errMsg;
    public Stock retData;

    public class Stock extends BaseMsg {
        public Stockinfo stockinfo;
        public Market market;
        public Klinegraph klinegraph;
    }


    public class Stockinfo extends BaseMsg {
        public String name;//: "科大讯飞", //股票名称
        public String code;//: "sz002230", //股票代码，SZ指在深圳交易的股票
        public String date;//: "2014-09-22", //当前显示股票信息的日期
        public String time;//: "09:26:11",   //具体时间
        public float OpenningPrice;//: 27.34, //今日开盘价
        public float closingPrice;//: 27.34,  //昨日收盘价
        public float currentPrice;//: 27.34,  //当前价格
        public float hPrice;//: 27.34,        //今日最高价
        public float lPrice;//: 27.34,       //今日最低价
        public float competitivePrice;//: 27.30, //买一报价
        public float auctionPrice;//: 27.34,   //卖一报价
        public int totalNumber;//: 47800,    //成交的股票数
        public float turnover;//: 1306852.00,  //成交额，以元为单位
        public int buyOne;//: 6100,      //买一
        public float buyOnePrice;//: 27.30, //买一价格
        public int buyTwo;//: 7500,  //买二
        public float buyTwoPrice;//: 27.29, //买二价格
        public int buyThree;//: 2000,   //买三
        public float buyThreePrice;//: 27.27,  //买三价格
        public int buyFour;//: 100,    //买四
        public float buyFourPrice;//: 27.25, //买四价格
        public int buyFive;//: 5700,     //买五
        public float buyFivePrice;//: 27.22,  //买五价格
        public int sellOne;//: 10150,       //卖一
        public float sellOnePrice;//: 27.34,  //卖一价格
        public int sellTwo;//: 15200,      //卖二
        public float sellTwoPrice;//: 27.35,  //卖二价格
        public int sellThree;//: 5914,     //卖三
        public float sellThreePrice;//: 27.36, //卖三价格
        public int sellFour;//: 400,        //卖四
        public float sellFourPrice;//: 27.37,  //卖四价格
        public int sellFive;//: 3000,       //卖五
        public float sellFivePrice;//: 27.38   //卖五价格
    }

    public class Market extends BaseMsg {    //大盘指数
        public MarketInfo shanghai;//上证指数
        public MarketInfo shenzhen;//深证成指
    }

    public class MarketInfo extends BaseMsg {
        public String name;
        public float curdot; //2323.554 当前价格
        public float curprice;//: -5.897,  //当前价格涨幅
        public float rate;//: -0.25,    // 涨幅率
        public float dealnumber;//: 11586,  //交易量，单位为手（一百股）
        public float turnover;//: 98322   //成交额，万元为单位
    }

    public class Klinegraph extends BaseMsg {  //K线图
        public String minurl; // "http://image.sinajs.cn/newchart/min/n/sz002230.gif", //分时K线图
        public String dayurl;// "http://image.sinajs.cn/newchart/daily/n/sz002230.gif", //日K线图
        public String weekurl;// "http://image.sinajs.cn/newchart/weekly/n/sz002230.gif", //周K线图
        public String monthurl;// "http://image.sinajs.cn/newchart/monthly/n/sz002230.gif" //月K线图
    }
}
