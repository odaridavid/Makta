package com.android.blackoder.makta.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.blackoder.makta.R;
import com.android.blackoder.makta.model.FirestoreViewModel;
import com.android.blackoder.makta.model.entities.Book;
import com.android.blackoder.makta.view.profile.BookListActivity;

import org.parceler.Parcels;

import static com.android.blackoder.makta.utils.Constants.BOOK_DETAIL_REQUEST;
import static com.android.blackoder.makta.utils.Constants.BOOK_DETAIL_VIEW;
import static com.android.blackoder.makta.utils.Constants.MY_BOOK_DETAIL;

public class BookDetailActivity extends AppCompatActivity {

    private Book mBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            if (intent.hasExtra(BOOK_DETAIL_VIEW)) {
                Book book = Parcels.unwrap(intent.getParcelableExtra(MY_BOOK_DETAIL));
                mBook = book;
                setupBookView(book);
            } else if (intent.hasExtra(BOOK_DETAIL_REQUEST)) {
                //TODO Add Code for book request on search
            }
        } else {
            Toast.makeText(BookDetailActivity.this, "Intent Error", Toast.LENGTH_LONG).show();
            Intent lIntent = new Intent(BookDetailActivity.this, BookListActivity.class);
            startActivity(lIntent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.ic_share) {
            shareBook(mBook);
            Toast.makeText(BookDetailActivity.this, "Book Shared", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void shareBook(Book book) {
        ViewModelProviders.of(this).get(FirestoreViewModel.class).shareBookFirestore(book);
    }

    private void setupBookView(Book book) {
        if (book != null) {
            TextView lTextViewTitle = findViewById(R.id.text_view_detail_title);
            TextView lTextViewAuthor = findViewById(R.id.text_view_detail_author);
            TextView lTextViewDescription = findViewById(R.id.text_view_detail_description);
            TextView lTextViewEdition = findViewById(R.id.text_view_detail_edition);
            TextView lTextViewPublished = findViewById(R.id.text_view_detail_published);
            if (book.getTitle().isEmpty()) {
                lTextViewTitle.setText("-");
            } else {
                lTextViewTitle.setText(book.getTitle());
            }
            if (book.getAuthor().isEmpty()) {
                lTextViewAuthor.setText("-");
            } else {
                lTextViewAuthor.setText(book.getAuthor());
            }
            if (book.getDescription().isEmpty()) {
                lTextViewDescription.setText("-");
            } else {
                lTextViewDescription.setText(book.getDescription());
            }

            if (book.getEdition().isEmpty()) {
                lTextViewEdition.setText("-");
            } else {
                lTextViewEdition.setText(book.getEdition());
            }
            if (book.getPublished().isEmpty()) {
                lTextViewPublished.setText("-");
            } else {
                lTextViewPublished.setText(book.getPublished());
            }

        } else {
            Toast.makeText(BookDetailActivity.this, "Np Book To Display", Toast.LENGTH_LONG).show();
        }
    }
}
