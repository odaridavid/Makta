package com.android.blackoder.makta.model;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.android.blackoder.makta.model.db.BooksDatabase;
import com.android.blackoder.makta.model.db.MyBooksDao;
import com.android.blackoder.makta.model.entities.Book;

import java.util.List;

/**
 * Created By blackcoder
 * On 18/03/19
 **/
public class BooksRepository {
    private MyBooksDao mMyBooksDao;
    private LiveData<List<Book>> mMyBooks;

    BooksRepository(Application application) {
        BooksDatabase lBooksDatabase = BooksDatabase.getDatabase(application);
        mMyBooksDao = lBooksDatabase.mMyBooksDao();
        mMyBooks = mMyBooksDao.getMyBooks();
    }

    LiveData<List<Book>> getMyBooks() {
        return mMyBooks;
    }

    public void insert(Book book) {
        new insertAsyncTask(mMyBooksDao).execute(book);
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
}
