package com.luowei.tstore.service.message;

/**
 * Created by luowei on 2015/8/12.
 */
public class OCRWordMsg extends BaseMsg {
    public String errNum;
    public String errMsg;
    public OCRWord retData;

    class OCRWord {
        String word;
        OCRRect rect;
    }

    class OCRRect {
        String left;
        String top;
        String width;
        String height;
    }
}
