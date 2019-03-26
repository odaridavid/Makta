package com.android.blackoder.makta.model.books;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.android.blackoder.makta.model.entities.Book;
import com.android.blackoder.makta.model.entities.BookRequests;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created By blackcoder
 * On 20/03/19
 **/
public final class FirestoreViewModel extends AndroidViewModel {
    private BooksRepository mBooksRepository;
    private FirebaseUser lFirebaseUser;

    public FirestoreViewModel(@NonNull Application application) {
        super(application);
        mBooksRepository = new BooksRepository(application);
        lFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    public FirestoreRecyclerOptions searchBook(String bookTitle) {
        return mBooksRepository.searchSharedCollection(bookTitle);
    }

    public void deleteBookFirestore(Book book) {
      mBooksRepository.deleteBookFromSharedCollection(book,getFirebaseUser());
    }

    public void addBookFirestore(Book book) {
        mBooksRepository.addBookToUserCollection(book, getFirebaseUser());
    }

    public void shareBookFirestore(Book book) {
        mBooksRepository.addBookToSharedCollection(book, getFirebaseUser());
    }

    public void dismissRequest(BookRequests bookRequests) {
        mBooksRepository.removeBookRequest(bookRequests, getFirebaseUser());
    }

    public void borrowBook(String owner, String title) {
        mBooksRepository.borrowBook(owner, title, getFirebaseUser());
    }

    public FirebaseUser getFirebaseUser() {
        return lFirebaseUser;
    }

    public FirestoreRecyclerOptions loadBookRequests() {
        return mBooksRepository.loadBookRequests(getFirebaseUser());
    }
    public FirestoreRecyclerOptions loadBorrowedBooks() {
        return mBooksRepository.loadBorrowedBooks(getFirebaseUser());
    }
    public FirestoreRecyclerOptions loadLentOutBooks() {
        return mBooksRepository.loadLentOutBooks(getFirebaseUser());
    }

    public void addToLentOutBooks(BookRequests bookRequests) {
        mBooksRepository.addBookToLentOut(bookRequests, getFirebaseUser());
    }

    public void sendBorrowRequestAccepted(BookRequests bookRequests) {
        mBooksRepository.addBookToBorrowed(bookRequests, getFirebaseUser());
    }
}
