package com.android.blackoder.makta.model.books;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.android.blackoder.makta.model.entities.Book;
import com.android.blackoder.makta.model.entities.WishBook;

import java.util.List;

/**
 * Created By blackcoder
 * On 18/03/19
 **/
public final class BookViewModel extends AndroidViewModel {
    private BooksRepository mBooksRepository;

    public BookViewModel(@NonNull Application application) {
        super(application);
//        Connect ViewModel to repo
        mBooksRepository = new BooksRepository(application);
    }

    public LiveData<List<Book>> getAllBooks() {
        return mBooksRepository.getMyBooks();
    }

    public LiveData<List<WishBook>> getAllWishBooks() {
        return mBooksRepository.getWishListBooks();
    }

    public void insert(Book book) {
        mBooksRepository.insert(book);
    }

    public void remove(Book book) {
        mBooksRepository.remove(book);
    }

    public void addToWishList(WishBook wishBook) {
        mBooksRepository.addBookToWishList(wishBook);
    }

    public void deleteFromWishList(WishBook wishBook) {
        mBooksRepository.removeBookFromWishList(wishBook);
    }

    public boolean checkIfExists(WishBook wishBook) {
        return mBooksRepository.checkBookExists(wishBook);
    }
}
