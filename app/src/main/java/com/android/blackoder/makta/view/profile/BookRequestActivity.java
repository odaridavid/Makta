package com.android.blackoder.makta.view.profile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.android.blackoder.makta.R;
import com.android.blackoder.makta.model.entities.SharedBook;
import com.android.blackoder.makta.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class BookRequestActivity extends AppCompatActivity {
    private List<SharedBook> mSharedBooks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_request);
        RecyclerView rvBookRequests = findViewById(R.id.recycler_view_book_requests);
        TextView tvNoBookRequests = findViewById(R.id.text_view_no_book_request);
        AppUtils.handleVisibility(mSharedBooks, rvBookRequests, tvNoBookRequests);
    }
}
