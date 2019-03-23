package com.android.blackoder.makta.model.books;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.android.blackoder.makta.model.db.BooksDatabase;
import com.android.blackoder.makta.model.db.MyBooksDao;
import com.android.blackoder.makta.model.db.WishListDao;
import com.android.blackoder.makta.model.entities.Book;
import com.android.blackoder.makta.model.entities.BookRequests;
import com.android.blackoder.makta.model.entities.SharedBook;
import com.android.blackoder.makta.model.entities.WishBook;
import com.android.blackoder.makta.utils.AppExecutors;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created By blackcoder
 * On 18/03/19
 **/
class BooksRepository {
    private MyBooksDao mMyBooksDao;
    private WishListDao mWishListDao;
    private FirebaseFirestore db;
    private FirebaseUser lFirebaseUser;


    BooksRepository(Application application) {
        BooksDatabase lBooksDatabase = BooksDatabase.getDatabase(application);
        mMyBooksDao = lBooksDatabase.mMyBooksDao();
        mWishListDao = lBooksDatabase.mWishListDao();
        db = FirebaseFirestore.getInstance();
        lFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    void addBookToUserCollection(Book book) {
        if (lFirebaseUser != null) {
            AppExecutors.getInstance().diskIO().execute(() ->
                    db.collection("users").document(lFirebaseUser.getUid()).collection("books").document(book.getTitle())
                            .set(parseBookData(book))
                            .addOnSuccessListener(aVoid -> Log.d("Book Entry :", "SUCCESS"))
                            .addOnFailureListener(e -> Log.w(
                                    "Book Entry", "Error adding document", e)));
        }
    }

    private Map<String, String> parseBookData(Book book) {
        return new HashMap<String, String>() {
            {
                put("author", book.getAuthor());
                put("title", book.getTitle());
                put("description", book.getDescription());
                put("published", book.getPublished());
                put("edition", book.getEdition());
            }
        };
    }

    boolean checkBookExists(WishBook wishBook) {
        List<WishBook> lExistingWishBooks = mWishListDao.getExistingBooks(wishBook.getAuthor(), wishBook.getTitle());
        Log.d("Wish Book", lExistingWishBooks.toString());
        return lExistingWishBooks.size() > 0;
    }

    FirestoreRecyclerOptions loadBookRequests() {
        Log.d("REQUEST", "INIT");
        Query lQuery = db
                .collection("requests")
                .document(lFirebaseUser.getUid())
                .collection("books");

        return new FirestoreRecyclerOptions.Builder<BookRequests>()
                .setQuery(lQuery, BookRequests.class)
                .build();
    }


    FirestoreRecyclerOptions searchSharedCollection(String bookTitle) {
        CollectionReference booksRef = db.collection("books");
        Query query = booksRef
                .whereEqualTo("title", bookTitle)
                .limit(20);
        return new FirestoreRecyclerOptions.Builder<SharedBook>()
                .setQuery(query, SharedBook.class)
                .build();
    }

    void borrowBook(String owner, String title) {
        Map<String, String> borrowPayload = new HashMap<String, String>() {
            {
                put("body", "I would love to borrow " + title + " from your book collection");
                put("requester", lFirebaseUser.getDisplayName());
            }
        };
        if (lFirebaseUser != null) {
            AppExecutors.getInstance().diskIO().execute(() ->
                    db.collection("requests").document(owner).collection("books").document(title + "-" + lFirebaseUser.getDisplayName())
                            .set(borrowPayload)
                            .addOnSuccessListener(aVoid -> Log.d("Book Request :", "SUCCESS"))
                            .addOnFailureListener(e -> Log.w(
                                    "Book Entry", "Error adding document", e)));
        }
    }

    void addBookToSharedCollection(Book book) {
        String userId = lFirebaseUser.getUid();
        Map<String, String> sharedBook = parseBookData(book);
        sharedBook.put("user", userId);
        if (lFirebaseUser != null) {
            AppExecutors.getInstance().diskIO().execute(() -> db.collection("books").document(book.getTitle())
                    .set(sharedBook)
                    .addOnSuccessListener(aVoid -> Log.d("Book Entry :", "SUCCESS"))
                    .addOnFailureListener(e -> Log.w(
                            "Book Entry", "Error adding document", e)));
        }
    }

    void addBookToWishList(WishBook book) {
        new insertWishlistAsyncTask(mWishListDao).execute(book);
    }

    void removeBookFromWishList(WishBook book) {
        new deleteWishlistAsyncTask(mWishListDao).execute(book);
    }


    LiveData<List<Book>> getMyBooks() {
        return mMyBooksDao.getMyBooks();
    }

    LiveData<List<WishBook>> getWishListBooks() {
        return mWishListDao.getWishList();
    }

    void insert(Book book) {
        new insertAsyncTask(mMyBooksDao).execute(book);
    }

    void remove(Book book) {
        new removeAsyncTask(mMyBooksDao).execute(book);
    }

    private static class insertAsyncTask extends AsyncTask<Book, Void, Void> {

        private MyBooksDao mAsyncTaskDao;

        insertAsyncTask(MyBooksDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Book... params) {
            mAsyncTaskDao.insertBook(params[0]);
            return null;
        }
    }

    private static class insertWishlistAsyncTask extends AsyncTask<WishBook, Void, Void> {

        private WishListDao mAsyncTaskDao;

        insertWishlistAsyncTask(WishListDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(WishBook... params) {
            mAsyncTaskDao.insertBook(params[0]);
            return null;
        }
    }

    private static class deleteWishlistAsyncTask extends AsyncTask<WishBook, Void, Void> {

        private WishListDao mAsyncTaskDao;

        deleteWishlistAsyncTask(WishListDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(WishBook... params) {
            mAsyncTaskDao.deleteBook(params[0]);
            return null;
        }
    }


    private static class removeAsyncTask extends AsyncTask<Book, Void, Void> {

        private MyBooksDao mAsyncTaskDao;

        removeAsyncTask(MyBooksDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Book... params) {
            mAsyncTaskDao.deleteBook(params[0]);
            return null;
        }
    }

}
