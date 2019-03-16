package com.android.blackoder.makta.utils;

import android.view.View;

import java.util.List;

/**
 * Created By blackcoder
 * On 16/03/19
 **/
public class AppUtils {

    public static void handleVisibility(List<?> list, View recyclerView, View textView) {
        if (list.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
        }
    }
}
