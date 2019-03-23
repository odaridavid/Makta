package com.android.blackoder.makta.presenter;

import com.android.blackoder.makta.contract.AddBookContract;
import com.android.blackoder.makta.model.books.BookViewModel;
import com.android.blackoder.makta.model.books.FirestoreViewModel;
import com.android.blackoder.makta.model.entities.Book;

/**
 * Created By blackcoder
 * On 19/03/19
 **/
public class AddBookPresenter implements AddBookContract.Presenter {


    @Override
    public Book passBookData(String author, String title, String description, String date, String edition) {
        return new Book(author, title, description, date, edition);
    }

    @Override
    public void addBooksToModels(BookViewModel bookViewModel, FirestoreViewModel firestoreViewModel, Book book) {
        bookViewModel.insert(book);
        firestoreViewModel.addBookFirestore(book);
        firestoreViewModel.shareBookFirestore(book);
    }

}
