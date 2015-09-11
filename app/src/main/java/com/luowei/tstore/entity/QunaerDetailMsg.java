package com.luowei.tstore.entity;

/**
 * 去那儿景点列表
 * Created by luowei on 2015/8/12.
 */
public class QunaerDetailMsg extends BaseMsg {
    public int errNum;
    public String errMsg;
    public Result retData;

    public class Result {
        public String id;
        public TicketDetail ticketDetail;
    }

    public class TicketDetail {
        public String loc;
        public String lastmod;
        public String changefreq;
        public String priority;
        public Data data;
    }

    public class Data {
        public Display display;
    }

    public class Display {
        public Ticket ticket;
        public String category;
        public String subcate;
        public String source;
    }

    public class Ticket {
        public String spotName;
        public String alias;
        public String spotID;
        public String description;
        public String detailUrl;
        public String address;
        public String province;
        public String city;
        public String coordinates;
        public String imageUrl;
        public String comments;
        public PriceList priceList;
    }

    public class PriceList {
        public String ticketTitle;
        public String ticketID;
        public String price;
        public String normalPrice;
        public String num;
        public String type;
        public String bookUrl;
    }
}
