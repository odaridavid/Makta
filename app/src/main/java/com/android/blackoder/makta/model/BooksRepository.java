package com.android.blackoder.makta.model;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.android.blackoder.makta.model.db.BooksDatabase;
import com.android.blackoder.makta.model.db.MyBooksDao;
import com.android.blackoder.makta.model.entities.Book;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

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

    BooksRepository(Application application) {
        BooksDatabase lBooksDatabase = BooksDatabase.getDatabase(application);
        mMyBooksDao = lBooksDatabase.mMyBooksDao();
        mMyBooks = mMyBooksDao.getMyBooks();
        db = FirebaseFirestore.getInstance();
    }

    void addToFirestoreDb(Book book) {
        Map<String, String> bookData = new HashMap<>();
        bookData.put("author", book.getAuthor());
        bookData.put("title", book.getTitle());
        bookData.put("description", book.getDescription());
        bookData.put("date", book.getDatePublished());
        bookData.put("edition", book.getEdition());
        FirebaseUser lFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (lFirebaseUser != null) {
            db.collection("users").document(lFirebaseUser.getUid()).collection("books").document(book.getTitle())
                    .set(bookData)
                    .addOnSuccessListener(aVoid -> Log.d("Book Entry :", "SUCCESS"))
                    .addOnFailureListener(e -> Log.w(
                            "Book Entry", "Error adding document", e));
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
