package com.android.blackoder.makta.model.books;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
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
final class BooksRepository {
    private MyBooksDao mMyBooksDao;
    private WishListDao mWishListDao;
    private FirebaseFirestore db;


    BooksRepository(Application application) {
        BooksDatabase lBooksDatabase = BooksDatabase.getDatabase(application);
        mMyBooksDao = lBooksDatabase.mMyBooksDao();
        mWishListDao = lBooksDatabase.mWishListDao();
        db = FirebaseFirestore.getInstance();
    }

    void addBookToUserCollection(Book book, FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            AppExecutors.getInstance().diskIO().execute(() ->
                    db.collection("users").document(firebaseUser.getUid()).collection("books").document(book.getTitle())
                            .set(parseBookData(book))
                            .addOnSuccessListener(aVoid -> Log.d("Book Entry :", "SUCCESS"))
                            .addOnFailureListener(e ->
                                    Log.w("Book Entry", e.getMessage())));
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

    boolean checkBookExistsInWishlist(WishBook wishBook) {
        List<WishBook> lExistingWishBooks = mWishListDao.getExistingBooks(wishBook.getAuthor(), wishBook.getTitle());
        return lExistingWishBooks.size() > 0;
    }

    FirestoreRecyclerOptions loadBookRequests(@NonNull FirebaseUser firebaseUser) {
        Query lQuery = db
                .collection("requests")
                .document(firebaseUser.getUid())
                .collection("books");

        return new FirestoreRecyclerOptions.Builder<BookRequests>()
                .setQuery(lQuery, BookRequests.class)
                .build();
    }

    void removeBookRequest(BookRequests bookRequests, @NonNull FirebaseUser firebaseUser) {
        String title = getBookTitleFromRequest(bookRequests);
        AppExecutors.getInstance().diskIO().execute(() ->
                db.collection("requests")
                        .document(firebaseUser.getUid())
                        .collection("books")
                        .document(title + "-" + bookRequests.getRequester())
                        .delete()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful())
                                Log.d("Clear Request", "DocumentSnapshot successfully deleted!");
                        })
                        .addOnFailureListener(e -> {
                            Log.e("Clear Request", e.getMessage());
                        }));

    }

    private String getBookTitleFromRequest(BookRequests bookRequests) {
        return bookRequests
                .getBody()
                .replace("I would love to borrow", " ")
                .replace("from your book collection", " ")
                .trim();
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


    void borrowBook(String owner, String title, @NonNull FirebaseUser firebaseUser) {
        Map<String, String> borrowPayload = new HashMap<String, String>() {
            {
                put("body", "I would love to borrow " + title + " from your book collection");
                put("requester", firebaseUser.getDisplayName());
                put("user", firebaseUser.getUid());
            }
        };
        AppExecutors.getInstance().diskIO().execute(() ->
                db.collection("requests")
                        .document(owner)
                        .collection("books")
                        .document(title + "-" + firebaseUser.getDisplayName())
                        .set(borrowPayload)
                        .addOnSuccessListener(aVoid -> Log.d("Book Request :", "SUCCESS"))
                        .addOnFailureListener(e -> Log.w("Book Entry", e.getMessage())));
    }

    void addBookToSharedCollection(Book book, @NonNull FirebaseUser firebaseUser) {
        String userId = firebaseUser.getUid();
        Map<String, String> sharedBookPayload = parseBookData(book);
        sharedBookPayload.put("user", userId);
        AppExecutors.getInstance().diskIO().execute(() -> db.collection("books").document(book.getTitle())
                .set(sharedBookPayload)
                .addOnSuccessListener(aVoid -> Log.d("Book Entry :", "SUCCESS"))
                .addOnFailureListener(e -> Log.w("Book Entry", e.getMessage())));
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


    void addBookToLentOut(BookRequests bookRequests, @NonNull FirebaseUser firebaseUser) {
        Map<String, String> lentOutBook = new HashMap<>();
        lentOutBook.put("title", getBookTitleFromRequest(bookRequests));
        lentOutBook.put("to", bookRequests.getRequester());
        db.collection("users")
                .document(firebaseUser.getUid())
                .collection("lent")
                .document(getBookTitleFromRequest(bookRequests))
                .set(lentOutBook)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("Lend Out", "Book Lend Out Was Successful");
                    }
                })
                .addOnFailureListener(e -> Log.e("Lend Out", e.getMessage()));
    }

    void addBookToBorrowed(BookRequests bookRequests, @NonNull FirebaseUser firebaseUser) {
        Map<String, String> borrowPayload = new HashMap<>();
        borrowPayload.put("borrowed", getBookTitleFromRequest(bookRequests));
        borrowPayload.put("from", firebaseUser.getDisplayName());
        db.collection("users")
                .document(bookRequests.getUser())
                .collection("borrowed")
                .document(getBookTitleFromRequest(bookRequests))
                .set(borrowPayload)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("Borrowing", "SUCCESS");
                    }
                })
                .addOnFailureListener(e -> Log.d("Borrow Error", e.getMessage()));
    }

    public void deleteBookFromSharedCollection(Book book, @NonNull FirebaseUser firebaseUser) {
//TODO Delete book from firestore
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
