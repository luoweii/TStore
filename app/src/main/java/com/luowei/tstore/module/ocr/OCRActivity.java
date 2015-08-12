package com.luowei.tstore.module.ocr;

import android.os.Bundle;

import com.luowei.tstore.R;
import com.luowei.tstore.module.BaseActivity;

/**
 * Created by luowei on 2015/8/13.
 */
public class OCRActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr);
    }

    @Override
    public String getActivityName() {
        return "百度文字识别";
    }
}
