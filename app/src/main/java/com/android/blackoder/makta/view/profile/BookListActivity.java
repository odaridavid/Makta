package com.android.blackoder.makta.view.profile;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.widget.TextView;

import com.android.blackoder.makta.R;
import com.android.blackoder.makta.model.BookViewModel;
import com.android.blackoder.makta.model.entities.Book;
import com.android.blackoder.makta.utils.AppUtils;
import com.android.blackoder.makta.view.BookDetailActivity;
import com.android.blackoder.makta.view.adapters.MyBooksAdapter;
import com.android.blackoder.makta.view.adapters.SwipeToDeleteCallback;

import org.parceler.Parcels;

import static com.android.blackoder.makta.utils.Constants.BOOK_DETAIL_VIEW;
import static com.android.blackoder.makta.utils.Constants.MY_BOOK_DETAIL;

public class BookListActivity extends AppCompatActivity implements MyBooksAdapter.IBookClickHandler {


    private RecyclerView rvMyBooks;
    private BookViewModel mBookViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        rvMyBooks = findViewById(R.id.recycler_view_my_books);
        mBookViewModel = ViewModelProviders.of(this).get(BookViewModel.class);
        TextView tvNoBooks = findViewById(R.id.text_view_no_books);
        FloatingActionButton fabAddBook = findViewById(R.id.fab_add_book);
        MyBooksAdapter lMyBooksAdapter = new MyBooksAdapter(this);
        mBookViewModel.getAllBooks().observe(this, books -> {
            if (books != null) {
                AppUtils.handleVisibility(books, rvMyBooks, tvNoBooks);
                lMyBooksAdapter.setBookList(books);
                Log.d("BookList", books.toString());
            }
        });
        setupRecyclerView(lMyBooksAdapter);
        fabAddBook.setOnClickListener(v -> {
            Intent intent = new Intent(BookListActivity.this, BookEntryActivity.class);
            startActivity(intent);
        });

    }

    private void setupRecyclerView(MyBooksAdapter myBooksAdapter) {
        rvMyBooks.setAdapter(myBooksAdapter);
        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new SwipeToDeleteCallback(myBooksAdapter, mBookViewModel));
        itemTouchHelper.attachToRecyclerView(rvMyBooks);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvMyBooks.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvMyBooks.getContext(),
                linearLayoutManager.getOrientation());
        rvMyBooks.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void onClick(Book book) {
        Intent intent = new Intent(BookListActivity.this, BookDetailActivity.class);
        intent.putExtra(MY_BOOK_DETAIL, Parcels.wrap(book));
        intent.putExtra(BOOK_DETAIL_VIEW, "view");
        startActivity(intent);
    }
}
