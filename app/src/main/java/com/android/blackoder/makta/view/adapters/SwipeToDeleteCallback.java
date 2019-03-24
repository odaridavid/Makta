package com.android.blackoder.makta.view.adapters;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.android.blackoder.makta.R;
import com.android.blackoder.makta.model.books.BookViewModel;
import com.android.blackoder.makta.model.books.FirestoreViewModel;
import com.android.blackoder.makta.model.entities.Book;
import com.android.blackoder.makta.model.entities.WishBook;
import com.android.blackoder.makta.utils.AppExecutors;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


/**
 * Created By blackcoder
 * On 19/03/19
 **/
public final class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
    private MyBooksAdapter mMyBooksAdapter;
    private BookViewModel mBookViewModel;
    private WishListAdapter mWishListAdapter;
    private Context mContext;
    private FirestoreViewModel mFirestoreViewModel;

    public SwipeToDeleteCallback(MyBooksAdapter adapter, BookViewModel bookViewModel, FirestoreViewModel firestoreViewModel, Context context) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.mMyBooksAdapter = adapter;
        this.mBookViewModel = bookViewModel;
        mContext = context;
        mFirestoreViewModel = firestoreViewModel;
    }

    public SwipeToDeleteCallback(WishListAdapter adapter, BookViewModel bookViewModel, Context context) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.mWishListAdapter = adapter;
        this.mBookViewModel = bookViewModel;
        mContext = context;
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
            AppExecutors.getInstance().diskIO().execute(() -> {
                mBookViewModel.remove(lBook);
                mFirestoreViewModel.deleteBookFirestore(lBook);
            });
            mMyBooksAdapter.notifyDataSetChanged();
        } else {
            int position = viewHolder.getAdapterPosition();
            WishBook lBook = mWishListAdapter.getItemFromList(position);
            AppExecutors.getInstance().diskIO().execute(() -> mBookViewModel.deleteFromWishList(lBook));
            mWishListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        new RecyclerViewSwipeDecorator.Builder(mContext, c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                .addBackgroundColor(ContextCompat.getColor(mContext, android.R.color.holo_red_dark))
                .addActionIcon(R.drawable.ic_delete)
                .create()
                .decorate();
    }
}
