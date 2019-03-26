package com.android.blackoder.makta.view.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.blackoder.makta.R;
import com.android.blackoder.makta.model.entities.Book;
import com.android.blackoder.makta.model.entities.SharedBook;

import java.util.List;

/**
 * Created By blackcoder
 * On 18/03/19
 **/
public final class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.BooksViewHolder> {

    private List<SharedBook> mBookList;
    private static IBookClickHandler sBookClickHandler;
    private SharedBook book;

    public SearchAdapter(IBookClickHandler iBookClickHandler) {
        sBookClickHandler = iBookClickHandler;
    }

    public void setBookList(List<SharedBook> bookList) {
        mBookList = bookList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BooksViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        int layout_id = R.layout.list_item_my_books;
        View view = inflater.inflate(layout_id, viewGroup, false);
        return new BooksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksViewHolder booksViewHolder, int i) {
        if (mBookList != null) {
            SharedBook currentBook = mBookList.get(i);

            booksViewHolder.tvBookTitle.setText(currentBook.getTitle());
            booksViewHolder.tvBookAuthor.setText(currentBook.getAuthor());
        }
    }


    SharedBook getItemFromList(int position) {
        return mBookList.get(position);
    }

    @Override
    public int getItemCount() {
        return mBookList != null ? mBookList.size() : 0;
    }

    class BooksViewHolder extends RecyclerView.ViewHolder {

        TextView tvBookTitle, tvBookAuthor;

        BooksViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBookTitle = itemView.findViewById(R.id.text_view_book_title);
            tvBookAuthor = itemView.findViewById(R.id.text_view_book_author);
            itemView.setOnClickListener(v -> {
                book = mBookList.get(getPosition());
                sBookClickHandler.onClick(book);
            });
        }
    }

    public interface IBookClickHandler {
        void onClick(SharedBook book);
    }
}
