package com.android.blackoder.makta.contract;

import com.android.blackoder.makta.model.books.BookViewModel;
import com.android.blackoder.makta.model.books.FirestoreViewModel;
import com.android.blackoder.makta.model.entities.Book;

/**
 * Created By blackcoder
 * On 19/03/19
 **/
public interface AddBookContract {
    interface Presenter {
        Book passBookData(String author, String title, String description, String date, String edition);

        void addBooksToModels(BookViewModel bookViewModel, FirestoreViewModel firestoreViewModel, Book book);
    }

}
