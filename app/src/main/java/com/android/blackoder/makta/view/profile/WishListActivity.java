package com.android.blackoder.makta.view.profile;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.widget.TextView;

import com.android.blackoder.makta.R;
import com.android.blackoder.makta.model.books.BookViewModel;
import com.android.blackoder.makta.utils.AppUtils;
import com.android.blackoder.makta.view.adapters.SwipeToDeleteCallback;
import com.android.blackoder.makta.view.adapters.WishListAdapter;

public class WishListActivity extends AppCompatActivity {

    private BookViewModel mBookViewModel;
    private RecyclerView mRvWishList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);
        mRvWishList = findViewById(R.id.recycler_view_wishlist);
        TextView tvNoWishList = findViewById(R.id.text_view_no_wishlist);
        mBookViewModel = ViewModelProviders.of(this).get(BookViewModel.class);
        WishListAdapter lWishListAdapter = new WishListAdapter();
        mBookViewModel.getAllWishBooks().observe(this, wishBooks -> {
            if (wishBooks != null) {
                Log.d("wishlist activity", wishBooks.toString());
                AppUtils.handleVisibility(wishBooks, mRvWishList, tvNoWishList);
                lWishListAdapter.setBookList(wishBooks);
            }
        });
        setupRecyclerView(lWishListAdapter);
    }

    private void setupRecyclerView(WishListAdapter wishListAdapter) {
        mRvWishList.setAdapter(wishListAdapter);
        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new SwipeToDeleteCallback(wishListAdapter, mBookViewModel));
        itemTouchHelper.attachToRecyclerView(mRvWishList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRvWishList.setLayoutManager(linearLayoutManager);
        AppUtils.recycelrViewDecoration(mRvWishList, linearLayoutManager);
    }
}
