package com.android.blackoder.makta.view.profile;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.android.blackoder.makta.R;
import com.android.blackoder.makta.databinding.ActivityBookEntryBinding;
import com.android.blackoder.makta.model.books.BookViewModel;
import com.android.blackoder.makta.model.books.FirestoreViewModel;
import com.android.blackoder.makta.model.entities.Book;
import com.android.blackoder.makta.presenter.AddBookPresenter;
import com.android.blackoder.makta.utils.AppExecutors;
import com.android.blackoder.makta.utils.AppUtils;
import com.android.blackoder.makta.utils.Validator;

import java.util.ArrayList;
import java.util.List;

public class BookEntryActivity extends AppCompatActivity {

    private Validator mValidator;
    private BookViewModel mBookViewModel;
    private FirestoreViewModel mFirestoreViewModel;
    private AddBookPresenter lAddBookPresenter;
    private ActivityBookEntryBinding mBinding;
    private EditText etAuthor, etTitle, etDescription, etEdition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_book_entry);
        mValidator = new Validator();
        lAddBookPresenter = new AddBookPresenter();
        mBookViewModel = ViewModelProviders.of(this).get(BookViewModel.class);
        mFirestoreViewModel = ViewModelProviders.of(this).get(FirestoreViewModel.class);
        mBinding.buttonAddBook.setOnClickListener(v -> addBookToDb());

    }

    public void addBookToDb() {
        etAuthor = mBinding.editTextAuthor;
        etTitle = mBinding.editTextTitle;
        etDescription = mBinding.editTextDescription;
        etEdition = mBinding.editTextEdition;
        List<String> bookDetails = retrieveData(etAuthor, etTitle, etDescription, etEdition);
        if (bookDetails.isEmpty() || bookDetails.size() < 4) {
            bookDetails.clear();
            Toast.makeText(BookEntryActivity.this, getString(R.string.prompt_valid_data), Toast.LENGTH_LONG).show();
        } else {
//                Append Date as String
            String date = mValidator.getDate(mBinding.datePickerPublishDate);
            if (!date.contains("Invalid")) {
                Book book = extractData(bookDetails, date);
                AppExecutors.getInstance().diskIO().execute(() -> {
                    lAddBookPresenter.addBooksToModels(mBookViewModel, mFirestoreViewModel, book);
                    runOnUiThread(() -> Toast.makeText(BookEntryActivity.this, getString(R.string.success_book_added), Toast.LENGTH_LONG).show());
                });
                AppUtils.clearEditText(new ArrayList<EditText>() {
                    {
                        add(etAuthor);
                        add(etTitle);
                        add(etDescription);
                        add(etEdition);
                    }
                });
            } else {
                bookDetails.clear();
                Toast.makeText(BookEntryActivity.this, getString(R.string.date_validate_error), Toast.LENGTH_LONG).show();
            }
        }
    }

    public Book extractData(List<String> bookDetails, String date) {
        String author = bookDetails.get(0);
        String title = bookDetails.get(1);
        String description = bookDetails.get(2);
        String edition = bookDetails.get(3);
        return lAddBookPresenter.passBookData(author, title, description, date, edition);
    }


    /**
     * @param author      retrieve author name from edit text
     * @param description retrieve description of book from edit text
     * @param edition     retrieve book edition from edit text
     * @param title       retrieve book title from edit text
     * @return List of data once validated
     */
    public List<String> retrieveData(EditText author, EditText title, EditText description, EditText edition) {
        String author_ = author.getText().toString();
        String description_ = description.getText().toString();
        String edition_ = edition.getText().toString();
        String title_ = title.getText().toString();

        validateInputsOnView(author_, title_, description_, edition_);
        return mValidator.buildBookInfoAsList(author_, title_, description_, edition_);
    }

    private void validateInputsOnView(String author_, String title_, String description_, String edition_) {
        if (!mValidator.validAuthor(author_)) {
            mBinding.textInputAuthor.setError(getString(R.string.author_validate_error));
        } else {
            mBinding.textInputAuthor.setErrorEnabled(false);
        }
        if (!mValidator.validTitle(title_)) {
            mBinding.textInputTitle.setError(getString(R.string.title_validate_error));
        } else {
            mBinding.textInputTitle.setErrorEnabled(false);
        }
        if (!mValidator.validDescription(description_)) {
            mBinding.textInputDescription.setError(getString(R.string.description_validate_error));
        } else {
            mBinding.textInputDescription.setErrorEnabled(false);
        }
        if (!mValidator.validEdition(edition_)) {
            mBinding.textInputEdition.setError(getString(R.string.edition_valiate_error));
        } else {
            mBinding.textInputEdition.setErrorEnabled(false);
        }
    }

}
