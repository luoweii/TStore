package com.luowei.tstore.utils;

import android.content.Context;

import java.lang.reflect.Field;

/**
 * 利用反射来获取资源文件
 * Created by luowei on 2015/8/10.
 */
public class ResUtil {
    public static int getId(Context context, String typeName, String name) {
        try {
            Class type = Class.forName(context.getPackageName() + ".R$" + typeName);
            Field idField = type.getField(name);
            return idField.getInt(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
