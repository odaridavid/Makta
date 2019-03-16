package com.android.blackoder.makta.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.android.blackoder.makta.R;
import com.android.blackoder.makta.model.Book;
import com.android.blackoder.makta.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class WishListActivity extends AppCompatActivity {

    private List<Book> wishlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);
        RecyclerView rvWishList = findViewById(R.id.recycler_view_wishlist);
        TextView tvNoWishList = findViewById(R.id.text_view_no_wishlist);
        AppUtils.handleVisibility(wishlist, rvWishList, tvNoWishList);
    }
}
