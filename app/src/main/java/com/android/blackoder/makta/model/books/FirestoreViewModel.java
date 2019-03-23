package com.android.blackoder.makta.model.books;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.android.blackoder.makta.model.entities.Book;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created By blackcoder
 * On 20/03/19
 **/
public class FirestoreViewModel extends AndroidViewModel {
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

    public void addBookFirestore(Book book) {
        mBooksRepository.addBookToUserCollection(book, getlFirebaseUser());
    }

    public void shareBookFirestore(Book book) {
        mBooksRepository.addBookToSharedCollection(book, getlFirebaseUser());
    }

    public void borrowBook(String owner, String title) {
        mBooksRepository.borrowBook(owner, title, getlFirebaseUser());
    }

    public FirebaseUser getlFirebaseUser() {
        return lFirebaseUser;
    }

    public FirestoreRecyclerOptions loadBookRequests() {
        return mBooksRepository.loadBookRequests(getlFirebaseUser());
    }
}
