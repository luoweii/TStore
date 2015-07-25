package com.luowei.tstore.utils;

import android.app.Activity;
import android.app.Dialog;
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
}
