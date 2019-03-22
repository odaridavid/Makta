package com.android.blackoder.makta.view.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.android.blackoder.makta.model.books.BookViewModel;
import com.android.blackoder.makta.model.entities.Book;
import com.android.blackoder.makta.utils.AppExecutors;

/**
 * Created By blackcoder
 * On 19/03/19
 **/
public final class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
    private MyBooksAdapter adapter;
    private BookViewModel mBookViewModel;


    public SwipeToDeleteCallback(MyBooksAdapter adapter, BookViewModel bookViewModel) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adapter = adapter;
        this.mBookViewModel = bookViewModel;
    }


    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
//        Remove Book From Room Database
        int position = viewHolder.getAdapterPosition();
        Book lBook = adapter.getItemFromList(position);
        AppExecutors.getInstance().diskIO().execute(() -> mBookViewModel.remove(lBook));
        adapter.notifyDataSetChanged();
    }
}
