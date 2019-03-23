package com.android.blackoder.makta.view;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.android.blackoder.makta.R;
import com.android.blackoder.makta.databinding.ActivityWishListBinding;
import com.android.blackoder.makta.model.books.BookViewModel;
import com.android.blackoder.makta.utils.AppUtils;
import com.android.blackoder.makta.view.adapters.SwipeToDeleteCallback;
import com.android.blackoder.makta.view.adapters.WishListAdapter;

public final class WishListActivity extends AppCompatActivity {

    private ActivityWishListBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_wish_list);
        RecyclerView lRecyclerView = mBinding.recyclerViewWishlist;
        BookViewModel lBookViewModel = ViewModelProviders.of(this).get(BookViewModel.class);
        WishListAdapter lWishListAdapter = new WishListAdapter();
        lBookViewModel.getAllWishBooks().observe(this, wishBooks -> {
            if (wishBooks != null) {
                AppUtils.handleVisibility(wishBooks, lRecyclerView, mBinding.textViewNoWishlist);
                lWishListAdapter.setBookList(wishBooks);
            }
        });
        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new SwipeToDeleteCallback(lWishListAdapter, lBookViewModel, WishListActivity.this));
        AppUtils.setupRecyclerView(itemTouchHelper, lRecyclerView);
        lRecyclerView.setAdapter(lWishListAdapter);
    }

}
