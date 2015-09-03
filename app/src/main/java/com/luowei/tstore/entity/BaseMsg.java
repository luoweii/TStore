package com.luowei.tstore.entity;

import java.io.Serializable;
import java.lang.reflect.Field;

public class BaseMsg implements Serializable {

    @Override
    public String toString() {
        String str = "";
        try {
            Field[] fields = getClass().getDeclaredFields();
            for (Field field : fields) {
                String name = field.getName();
                if (!name.startsWith("this")) {
                    str += field.getName() + "  :  " + field.get(this) + "\n";
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return str;
    }
}