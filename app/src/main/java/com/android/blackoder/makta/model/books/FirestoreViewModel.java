package com.android.blackoder.makta.model.books;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.android.blackoder.makta.model.entities.Book;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

/**
 * Created By blackcoder
 * On 20/03/19
 **/
public class FirestoreViewModel extends AndroidViewModel {
    private BooksRepository mBooksRepository;

    public FirestoreViewModel(@NonNull Application application) {
        super(application);
        mBooksRepository = new BooksRepository(application);
    }

    public FirestoreRecyclerOptions searchBook(String bookTitle) {
        return mBooksRepository.searchSharedCollection(bookTitle);
    }

    public void addBookFirestore(Book book) {
        mBooksRepository.addBookToUserCollection(book);
    }

    public void shareBookFirestore(Book book) {
        mBooksRepository.addBookToSharedCollection(book);
    }

    public void borrowBook(String owner, String title) {
        mBooksRepository.borrowBook(owner, title);
    }

    public FirestoreRecyclerOptions loadBookRequests() {
        return mBooksRepository.loadBookRequests();
    }
}
