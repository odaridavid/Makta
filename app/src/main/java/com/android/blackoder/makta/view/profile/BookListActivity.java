package com.android.blackoder.makta.view.profile;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.android.blackoder.makta.R;
import com.android.blackoder.makta.model.entities.Book;
import com.android.blackoder.makta.utils.AppUtils;
import com.android.blackoder.makta.view.adapters.MyBooksAdapter;

import java.util.ArrayList;
import java.util.List;

public class BookListActivity extends AppCompatActivity {

    private List<Book> myBooks = new ArrayList<>();
    private RecyclerView rvMyBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        rvMyBooks = findViewById(R.id.recycler_view_my_books);
        TextView tvNoBooks = findViewById(R.id.text_view_no_books);
        FloatingActionButton fabAddBook = findViewById(R.id.fab_add_book);
        setupRecyclerView();
        fabAddBook.setOnClickListener(v -> {
            Intent intent = new Intent(BookListActivity.this, BookEntryActivity.class);
            startActivity(intent);
        });
        AppUtils.handleVisibility(myBooks, rvMyBooks, tvNoBooks);
    }

    private void setupRecyclerView() {
        MyBooksAdapter lMyBooksAdapter = new MyBooksAdapter();
        rvMyBooks.setAdapter(lMyBooksAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvMyBooks.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvMyBooks.getContext(),
                linearLayoutManager.getOrientation());
        rvMyBooks.addItemDecoration(dividerItemDecoration);
    }
}
