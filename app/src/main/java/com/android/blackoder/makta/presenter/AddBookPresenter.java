package com.android.blackoder.makta.presenter;

import com.android.blackoder.makta.contract.AddBookContract;
import com.android.blackoder.makta.model.entities.Book;

/**
 * Created By blackcoder
 * On 19/03/19
 **/
public class AddBookPresenter implements AddBookContract.Presenter {

    private AddBookContract.View view;

    public AddBookPresenter(AddBookContract.View view) {
        this.view = view;
    }

    @Override
    public Book passBookData(String author, String title, String description, String date, String edition) {
        return new Book(author, title, description, date, edition);
    }

}
