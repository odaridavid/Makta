package com.android.blackoder.makta.view.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.blackoder.makta.R;

/**
 * Created By blackcoder
 * On 23/03/19
 **/

public final class BookViewHolder extends RecyclerView.ViewHolder {

    public TextView tvBookTitle, tvBookAuthor;

    public BookViewHolder(@NonNull View itemView) {
        super(itemView);
        tvBookTitle = itemView.findViewById(R.id.text_view_book_title);
        tvBookAuthor = itemView.findViewById(R.id.text_view_book_author);

    }
}

