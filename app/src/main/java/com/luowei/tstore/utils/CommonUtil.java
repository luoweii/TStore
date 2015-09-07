package com.luowei.tstore.utils;

import android.content.res.Resources;
import android.util.TypedValue;
import android.widget.Toast;

import com.luowei.tstore.App;

/**
 * Created by luowei on 2015/8/10.
 */
public class CommonUtil {
    public static void showToast(CharSequence cs) {
        Toast.makeText(App.context, cs, Toast.LENGTH_SHORT).show();
    }

    public static float dpToPx(Resources resources, float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
    }
}