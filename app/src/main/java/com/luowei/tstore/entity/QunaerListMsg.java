package com.luowei.tstore.entity;

import java.util.List;

/**
 * 去那儿景点列表
 * Created by luowei on 2015/8/12.
 */
public class QunaerListMsg extends BaseMsg {
    public int errNum;
    public String errMsg;
    public PageMsg retData;

    public class PageMsg extends BaseMsg {
        public int pageNo;//当前页数
        public int pageSize;//单页数量
        public int totalRecord;//总数量
        public List<Ticket> ticketList;
    }

    public class Ticket extends BaseMsg {
        public String productId;//门票产品编号
        public String spotName; //景点名称
        public List<String> spotAliasName;//景点别名
    }

}
