package com.android.blackoder.makta.utils;

import android.view.View;
import android.widget.EditText;

import java.util.List;

/**
 * Created By blackcoder
 * On 16/03/19
 **/
public class AppUtils {

    /**
     * @param list         List to be checked if empty or not
     * @param recyclerView Recycler view holding list items
     * @param textView     Text view displayed when list is empty
     */
    public static void handleVisibility(List<?> list, View recyclerView, View textView) {
        if (list.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
        }
    }

    /**
     * @param editTexts a list of edit texts that get cleared once operation is complete
     */
    public static void clearEditText(List<EditText> editTexts) {
        for (EditText x : editTexts) {
            x.getText().clear();
        }
    }
}
