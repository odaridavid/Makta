package com.android.blackoder.makta.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.blackoder.makta.R;
import com.android.blackoder.makta.model.books.BookViewModel;
import com.android.blackoder.makta.model.entities.Book;
import com.android.blackoder.makta.model.entities.SharedBook;
import com.android.blackoder.makta.model.entities.WishBook;
import com.android.blackoder.makta.view.fragments.MyBookDetailFragment;
import com.android.blackoder.makta.view.fragments.RequestDetailFragment;

import org.parceler.Parcels;

import static com.android.blackoder.makta.utils.Constants.BOOK_DETAIL;
import static com.android.blackoder.makta.utils.Constants.BOOK_DETAIL_REQUEST;
import static com.android.blackoder.makta.utils.Constants.BOOK_DETAIL_VIEW;

public final class BookDetailActivity extends AppCompatActivity {

    private WishBook mWishBook;
    private boolean isRequest = false;
    private BookViewModel mBookViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        Intent intent = getIntent();
        mBookViewModel = ViewModelProviders.of(this).get(BookViewModel.class);
        if (intent.getExtras() != null) {
            Bundle bundle = new Bundle();
            if (intent.hasExtra(BOOK_DETAIL_VIEW)) {
                Book book = Parcels.unwrap(intent.getParcelableExtra(BOOK_DETAIL));
                bundle.putParcelable("book", Parcels.wrap(book));
                MyBookDetailFragment lMyBookDetailFragment = new MyBookDetailFragment();
                lMyBookDetailFragment.setArguments(bundle);
                setupFragment(lMyBookDetailFragment, "Book Detail");
            } else if (intent.hasExtra(BOOK_DETAIL_REQUEST)) {
                isRequest = true;
                SharedBook book = Parcels.unwrap(intent.getParcelableExtra(BOOK_DETAIL));
                mWishBook = new WishBook(book.getTitle(), book.getAuthor());
                bundle.putParcelable("book", Parcels.wrap(book));
                RequestDetailFragment lRequestDetailFragment = new RequestDetailFragment();
                lRequestDetailFragment.setArguments(bundle);
                setupFragment(lRequestDetailFragment, "Book Request");
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
        if (isRequest) getMenuInflater().inflate(R.menu.detail_request_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_wishlist) {
            new CheckBookExistsAsyncTask(mBookViewModel, bookExists -> {
                if (bookExists) {
//                    TODO Not deleting fix using detail view
                    mBookViewModel.deleteFromWishList(mWishBook);
                    item.setIcon(R.drawable.ic_fav_unfilled);
                    Toast.makeText(getBaseContext(), getString(R.string.remove_from_wishlist), Toast.LENGTH_SHORT).show();
                } else {
                    mBookViewModel.addToWishList(mWishBook);
                    item.setIcon(R.drawable.ic_fav);
                    Toast.makeText(getBaseContext(), getString(R.string.book_added_wishlist), Toast.LENGTH_SHORT).show();
                }
            }).execute(mWishBook);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        /* Called before menu is created */
        if (isRequest) {
            MenuItem lMenuItem = menu.findItem(R.id.action_wishlist);

            new CheckBookExistsAsyncTask(mBookViewModel, bookExists -> {
                if (!bookExists) {
                    lMenuItem.setIcon(R.drawable.ic_fav_unfilled);
                }
            }).execute(mWishBook);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private static class CheckBookExistsAsyncTask extends AsyncTask<WishBook, Void, Boolean> {

        private BookViewModel mBookViewModel;
        private UpdateMenuCallBack mUpdateMenuCallBack;

        CheckBookExistsAsyncTask(BookViewModel bookViewModel, UpdateMenuCallBack callback) {
            mBookViewModel = bookViewModel;
            mUpdateMenuCallBack = callback;
        }

        @Override
        protected Boolean doInBackground(WishBook... wishBooks) {
            return mBookViewModel.checkIfExists(wishBooks[0]);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            mUpdateMenuCallBack.setResult(aBoolean);
        }
    }

    public interface UpdateMenuCallBack {
        void setResult(boolean bookExists);
    }
}
