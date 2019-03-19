package com.android.blackoder.makta.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.android.blackoder.makta.model.entities.Book;

import java.util.List;

/**
 * Created By blackcoder
 * On 18/03/19
 **/
public final class BookViewModel extends AndroidViewModel {
    private BooksRepository mBooksRepository;
    private LiveData<List<Book>> mMyBooks;

    public BookViewModel(@NonNull Application application) {
        super(application);
//        Connect ViewModel to repo
        mBooksRepository = new BooksRepository(application);
        mMyBooks = mBooksRepository.getMyBooks();
    }

    public LiveData<List<Book>> getAllBooks() {
        return mMyBooks;
    }

    public void insertFirestore(Book book) {
        mBooksRepository.addToFirestoreDb(book);
    }

    public void insert(Book book) {
        mBooksRepository.insert(book);
    }

    public void remove(Book book) {
        mBooksRepository.remove(book);
    }

}
