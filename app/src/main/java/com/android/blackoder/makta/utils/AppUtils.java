package com.android.blackoder.makta.utils;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.blackoder.makta.R;
import com.android.blackoder.makta.model.entities.IBook;

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

    public static void recyclerViewDecoration(RecyclerView recyclerView, LinearLayoutManager layoutManager) {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    public static void setupRecyclerView(ItemTouchHelper itemTouchHelper, RecyclerView recyclerView) {
        itemTouchHelper.attachToRecyclerView(recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerViewDecoration(recyclerView, linearLayoutManager);
    }

    public static void bindBookView(IBook book, View view) {
        if (book != null) {
            TextView lTextViewTitle = view.findViewById(R.id.text_view_detail_title);
            TextView lTextViewAuthor = view.findViewById(R.id.text_view_detail_author);
            TextView lTextViewDescription = view.findViewById(R.id.text_view_detail_description);
            TextView lTextViewEdition = view.findViewById(R.id.text_view_detail_edition);
            TextView lTextViewPublished = view.findViewById(R.id.text_view_detail_published);
            if (book.getTitle().isEmpty()) {
                lTextViewTitle.setText("-");
            } else {
                lTextViewTitle.setText(book.getTitle());
            }
            if (book.getAuthor().isEmpty()) {
                lTextViewAuthor.setText("-");
            } else {
                lTextViewAuthor.setText(book.getAuthor());
            }
            if (book.getDescription().isEmpty()) {
                lTextViewDescription.setText("-");
            } else {
                lTextViewDescription.setText(book.getDescription());
            }

            if (book.getEdition().isEmpty()) {
                lTextViewEdition.setText("-");
            } else {
                lTextViewEdition.setText(book.getEdition());
            }
            if (book.getPublished().isEmpty()) {
                lTextViewPublished.setText("-");
            } else {
                lTextViewPublished.setText(book.getPublished());
            }
        }
    }
}



