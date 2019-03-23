package com.android.blackoder.makta.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.widget.TextView;

import com.android.blackoder.makta.R;
import com.android.blackoder.makta.model.books.BookViewModel;
import com.android.blackoder.makta.model.entities.Book;
import com.android.blackoder.makta.utils.AppUtils;
import com.android.blackoder.makta.view.adapters.MyBooksAdapter;
import com.android.blackoder.makta.view.adapters.SwipeToDeleteCallback;

import org.parceler.Parcels;

import static com.android.blackoder.makta.utils.Constants.BOOK_DETAIL;
import static com.android.blackoder.makta.utils.Constants.BOOK_DETAIL_VIEW;

public final class BookListActivity extends AppCompatActivity implements MyBooksAdapter.IBookClickHandler {

    private RecyclerView rvMyBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        rvMyBooks = findViewById(R.id.recycler_view_my_books);
        BookViewModel lBookViewModel = ViewModelProviders.of(this).get(BookViewModel.class);
        TextView tvNoBooks = findViewById(R.id.text_view_no_books);
        FloatingActionButton fabAddBook = findViewById(R.id.fab_add_book);
        MyBooksAdapter lMyBooksAdapter = new MyBooksAdapter(this);
        lBookViewModel.getAllBooks().observe(this, books -> {
            if (books != null) {
                AppUtils.handleVisibility(books, rvMyBooks, tvNoBooks);
                lMyBooksAdapter.setBookList(books);
                Log.d("BookList", books.toString());
            }
        });
        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new SwipeToDeleteCallback(lMyBooksAdapter, lBookViewModel, BookListActivity.this));
        AppUtils.setupRecyclerView(itemTouchHelper, rvMyBooks);
        rvMyBooks.setAdapter(lMyBooksAdapter);
        fabAddBook.setOnClickListener(v -> {
            Intent intent = new Intent(BookListActivity.this, BookEntryActivity.class);
            startActivity(intent);
        });

    }


    @Override
    public void onClick(Book book) {
        Intent intent = new Intent(BookListActivity.this, BookDetailActivity.class);
        intent.putExtra(BOOK_DETAIL, Parcels.wrap(book));
        intent.putExtra(BOOK_DETAIL_VIEW, "view");
        startActivity(intent);
    }
}
