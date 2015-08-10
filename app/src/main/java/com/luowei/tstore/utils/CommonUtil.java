package com.luowei.tstore.utils;

import android.widget.Toast;

import com.luowei.tstore.App;

/**
 * Created by luowei on 2015/8/10.
 */
public class CommonUtil {
    public static void showToast(CharSequence cs) {
        Toast.makeText(App.context, cs, Toast.LENGTH_SHORT).show();
    }
}