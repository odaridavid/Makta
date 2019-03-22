package com.android.blackoder.makta.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.blackoder.makta.R;
import com.android.blackoder.makta.model.books.FirestoreViewModel;
import com.android.blackoder.makta.model.entities.Book;
import com.android.blackoder.makta.view.fragments.DetailFragment;
import com.android.blackoder.makta.view.fragments.RequestFragment;
import com.android.blackoder.makta.view.profile.BookListActivity;

import org.parceler.Parcels;

import static com.android.blackoder.makta.utils.Constants.BOOK_DETAIL_REQUEST;
import static com.android.blackoder.makta.utils.Constants.BOOK_DETAIL_VIEW;
import static com.android.blackoder.makta.utils.Constants.BOOK_DETAIL;

public class BookDetailActivity extends AppCompatActivity {

    private Book mBook;
    private boolean isRequest = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            Book book = Parcels.unwrap(intent.getParcelableExtra(BOOK_DETAIL));
            Bundle bundle = new Bundle();
            bundle.putParcelable("book", Parcels.wrap(book));
            if (intent.hasExtra(BOOK_DETAIL_VIEW)) {
                mBook = book;
                DetailFragment lDetailFragment = new DetailFragment();
                lDetailFragment.setArguments(bundle);
                setupFragment(lDetailFragment, "Book Detail");
            } else if (intent.hasExtra(BOOK_DETAIL_REQUEST)) {
                isRequest = true;
                RequestFragment lRequestFragment = new RequestFragment();
                lRequestFragment.setArguments(bundle);
                setupFragment(lRequestFragment, "Book Request");
            }
        } else {
            Toast.makeText(BookDetailActivity.this, "Intent Error", Toast.LENGTH_LONG).show();
            Intent lIntent = new Intent(BookDetailActivity.this, BookListActivity.class);
            startActivity(lIntent);
        }
    }

    public void setupFragment(Fragment fragment, String Tag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_detail_container, fragment, Tag);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!isRequest) {
            getMenuInflater().inflate(R.menu.detail_menu, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.ic_share) {
            shareBook(mBook);
            Toast.makeText(BookDetailActivity.this, "Book Shared", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void shareBook(Book book) {
        ViewModelProviders.of(this).get(FirestoreViewModel.class).shareBookFirestore(book);
    }

}
