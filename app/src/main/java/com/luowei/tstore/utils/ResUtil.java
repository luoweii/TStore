package com.luowei.tstore.utils;

import android.content.Context;

import com.luowei.tstore.R;

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
            try {
                Class type = Class.forName(R.class.getName() + "$" + typeName);
                Field idField = type.getField(name);
                return idField.getInt(name);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return 0;
    }
}
