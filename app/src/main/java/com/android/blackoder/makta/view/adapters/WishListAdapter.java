package com.android.blackoder.makta.view.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.blackoder.makta.R;
import com.android.blackoder.makta.model.entities.WishBook;

import java.util.List;

/**
 * Created By blackcoder
 * On 22/03/19
 **/
public final class WishListAdapter extends RecyclerView.Adapter<BookViewHolder> {

    private List<WishBook> mBookList;

    public WishListAdapter() {

    }

    public void setBookList(List<WishBook> bookList) {
        mBookList = bookList;
        notifyDataSetChanged();
    }

    WishBook getItemFromList(int position) {
        return mBookList.get(position);
    }


    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        int layout_id = R.layout.list_item_my_books;
        View view = inflater.inflate(layout_id, viewGroup, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder booksViewHolder, int i) {
        if (mBookList != null) {
            WishBook currentBook = mBookList.get(i);
            booksViewHolder.tvBookTitle.setText(currentBook.getTitle());
            booksViewHolder.tvBookAuthor.setText(currentBook.getAuthor());
        }
    }

    @Override
    public int getItemCount() {
        return mBookList != null ? mBookList.size() : 0;
    }

}
