package com.android.blackoder.makta.view.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.android.blackoder.makta.model.books.BookViewModel;
import com.android.blackoder.makta.model.entities.Book;
import com.android.blackoder.makta.model.entities.WishBook;
import com.android.blackoder.makta.utils.AppExecutors;


/**
 * Created By blackcoder
 * On 19/03/19
 **/
public final class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
    private MyBooksAdapter mMyBooksAdapter;
    private BookViewModel mBookViewModel;
    private WishListAdapter mWishListAdapter;

    public SwipeToDeleteCallback(MyBooksAdapter adapter, BookViewModel bookViewModel) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.mMyBooksAdapter = adapter;
        this.mBookViewModel = bookViewModel;
    }

    public SwipeToDeleteCallback(WishListAdapter adapter, BookViewModel bookViewModel) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.mWishListAdapter = adapter;
        this.mBookViewModel = bookViewModel;
    }


    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (mMyBooksAdapter != null) {
            int position = viewHolder.getAdapterPosition();
            Book lBook = mMyBooksAdapter.getItemFromList(position);
            AppExecutors.getInstance().diskIO().execute(() -> mBookViewModel.remove(lBook));
            mMyBooksAdapter.notifyDataSetChanged();
        } else {
            int position = viewHolder.getAdapterPosition();
            WishBook lBook = mWishListAdapter.getItemFromList(position);
            AppExecutors.getInstance().diskIO().execute(() -> mBookViewModel.deleteFromWishList(lBook));
            mWishListAdapter.notifyDataSetChanged();
        }
    }
}
