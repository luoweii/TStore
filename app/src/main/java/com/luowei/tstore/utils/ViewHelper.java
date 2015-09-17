package com.luowei.tstore.utils;

import android.app.Activity;
import android.app.Dialog;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by luowei on 2015/7/25.
 */
public class ViewHelper {
    /** Simpler version of {@link View#findViewById(int)} which infers the target type. */
    @SuppressWarnings({ "unchecked", "UnusedDeclaration" }) // Checked by runtime cast. Public API.
    public static <T extends View> T findById(View view, int id) {
        return (T) view.findViewById(id);
    }

    /** Simpler version of {@link Activity#findViewById(int)} which infers the target type. */
    @SuppressWarnings({ "unchecked", "UnusedDeclaration" }) // Checked by runtime cast. Public API.
    public static <T extends View> T findById(Activity activity, int id) {
        return (T) activity.findViewById(id);
    }

    /** Simpler version of {@link Dialog#findViewById(int)} which infers the target type. */
    @SuppressWarnings({ "unchecked", "UnusedDeclaration" }) // Checked by runtime cast. Public API.
    public static <T extends View> T findById(Dialog dialog, int id) {
        return (T) dialog.findViewById(id);
    }

    public static <T extends View> T get(View view, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }
}
