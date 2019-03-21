package com.android.blackoder.makta.model;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.android.blackoder.makta.model.db.BooksDatabase;
import com.android.blackoder.makta.model.db.MyBooksDao;
import com.android.blackoder.makta.model.entities.Book;
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
    private LiveData<List<Book>> mMyBooks;
    private FirebaseFirestore db;
    private FirebaseUser lFirebaseUser;

    BooksRepository(Application application) {
        BooksDatabase lBooksDatabase = BooksDatabase.getDatabase(application);
        mMyBooksDao = lBooksDatabase.mMyBooksDao();
        mMyBooks = mMyBooksDao.getMyBooks();
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

    FirestoreRecyclerOptions searchSharedCollection(String bookTitle) {
        CollectionReference booksRef = db.collection("books");
        Query query = booksRef
                .whereEqualTo("title", bookTitle)
                .limit(20);
        return new FirestoreRecyclerOptions.Builder<Book>()
                .setQuery(query, Book.class)
                .build();
    }

    void addBookToSharedCollection(Book book) {
//        TODO Add Username to share collection
        if (lFirebaseUser != null) {
            AppExecutors.getInstance().diskIO().execute(() -> db.collection("books").document(book.getTitle())
                    .set(parseBookData(book))
                    .addOnSuccessListener(aVoid -> Log.d("Book Entry :", "SUCCESS"))
                    .addOnFailureListener(e -> Log.w(
                            "Book Entry", "Error adding document", e)));
        }
    }


    LiveData<List<Book>> getMyBooks() {
        return mMyBooks;
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
