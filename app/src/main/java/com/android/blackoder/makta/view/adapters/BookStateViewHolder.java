package com.android.blackoder.makta.view.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.blackoder.makta.R;

/**
 * Created By blackcoder
 * On 24/03/19
 **/
public class BookStateViewHolder extends RecyclerView.ViewHolder {
    public TextView tvTitle, tvSub;

    public BookStateViewHolder(@NonNull View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.text_view_book_title);
        tvSub = itemView.findViewById(R.id.text_view_subtitle);
    }
}
