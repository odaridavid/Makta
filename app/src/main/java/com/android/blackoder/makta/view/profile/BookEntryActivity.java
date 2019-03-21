package com.android.blackoder.makta.view.profile;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.blackoder.makta.R;
import com.android.blackoder.makta.contract.AddBookContract;
import com.android.blackoder.makta.model.BookViewModel;
import com.android.blackoder.makta.model.FirestoreViewModel;
import com.android.blackoder.makta.model.entities.Book;
import com.android.blackoder.makta.presenter.AddBookPresenter;
import com.android.blackoder.makta.utils.AppExecutors;
import com.android.blackoder.makta.utils.AppUtils;
import com.android.blackoder.makta.utils.Validator;

import java.util.ArrayList;
import java.util.List;

public class BookEntryActivity extends AppCompatActivity implements AddBookContract.View {
    private TextInputLayout textInputAuthor, textInputDescription, textInputEdition, textInputTitle;
    private EditText etAuthor, etDescription, etEdition, etTitle;
    private Validator mValidator;
    private Button btnGetBookData;
    private DatePicker mDatePicker;
    private BookViewModel mBookViewModel;
    private FirestoreViewModel mFirestoreViewModel;
    private AddBookPresenter lAddBookPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_entry);
        setupViews();
        mValidator = new Validator();
        lAddBookPresenter = new AddBookPresenter(this);
        mBookViewModel = ViewModelProviders.of(this).get(BookViewModel.class);
        mFirestoreViewModel = ViewModelProviders.of(this).get(FirestoreViewModel.class);
        btnGetBookData.setOnClickListener(v -> addBookToDb());
    }

    public void addBookToDb() {
        List<String> bookDetails = retrieveData(etAuthor, etTitle, etDescription, etEdition);
        if (bookDetails.isEmpty() || bookDetails.size() < 4) {
            bookDetails.clear();
            Toast.makeText(BookEntryActivity.this, getString(R.string.prompt_valid_data), Toast.LENGTH_LONG).show();
        } else {
//                Append Date as String
            String date = mValidator.getDate(mDatePicker);
            if (!date.contains("Invalid")) {
                Book book = extractData(bookDetails, date);
                AppExecutors.getInstance().diskIO().execute(() -> {
                    mBookViewModel.insert(book);
                    mFirestoreViewModel.addBookFirestore(book);
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

    public void setupViews() {
        mDatePicker = findViewById(R.id.date_picker_publish_date);
        textInputAuthor = findViewById(R.id.text_input_author);
        textInputDescription = findViewById(R.id.text_input_description);
        textInputTitle = findViewById(R.id.text_input_title);
        textInputEdition = findViewById(R.id.text_input_edition);
        etAuthor = findViewById(R.id.edit_text_author);
        etDescription = findViewById(R.id.edit_text_description);
        etEdition = findViewById(R.id.edit_text_edition);
        etTitle = findViewById(R.id.edit_text_title);
        btnGetBookData = findViewById(R.id.button_add_book);
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
        return mValidator.insertBookData(author_, title_, description_, edition_);
    }

    private void validateInputsOnView(String author_, String title_, String description_, String edition_) {
        if (!mValidator.validAuthor(author_)) {
            textInputAuthor.setError(getString(R.string.author_validate_error));
        } else {
            textInputAuthor.setErrorEnabled(false);
        }
        if (!mValidator.validTitle(title_)) {
            textInputTitle.setError(getString(R.string.title_validate_error));
        } else {
            textInputTitle.setErrorEnabled(false);
        }
        if (!mValidator.validDescription(description_)) {
            textInputDescription.setError(getString(R.string.description_validate_error));
        } else {
            textInputDescription.setErrorEnabled(false);
        }
        if (!mValidator.validEdition(edition_)) {
            textInputEdition.setError(getString(R.string.edition_valiate_error));
        } else {
            textInputEdition.setErrorEnabled(false);
        }
    }


    @Override
    public void displaySuccess() {

    }

    @Override
    public void displayError() {

    }
}
