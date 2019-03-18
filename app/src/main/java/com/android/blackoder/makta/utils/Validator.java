package com.android.blackoder.makta.utils;

import android.widget.DatePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created By blackcoder
 * On 17/03/19
 **/
public final class Validator {


    public boolean validAuthor(String bookAuthor) {
        return !bookAuthor.isEmpty() && bookAuthor.matches("[a-zA-Z]*(?:\\s[a-zA-Z.+'-]+)*");
    }

    public boolean validDescription(String bookDescription) {
        return !bookDescription.isEmpty() && bookDescription.matches("[a-zA-Z0-9.+'-]*(?:\\s[a-zA-Z0-9.+'-]+)*");
    }


    public boolean validEdition(String bookEdition) {
        return !bookEdition.isEmpty() && bookEdition.matches("[a-zA-Z0-9.+'-]*(?:\\s[a-zA-Z0-9.+'-]+)*");
    }

    public boolean validTitle(String bookTitle) {
        return !bookTitle.isEmpty() && bookTitle.matches("[a-zA-Z0-9.+'-]*(?:\\s[a-zA-Z0-9.+'-]+)*");
    }

    public String getDate(DatePicker datePicker) {
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        int currentYear = cal.get(Calendar.YEAR);
        int currentMonth = cal.get(Calendar.MONTH) + 1;
        int datePickerYear = datePicker.getYear();
        int datePickerMonth = (datePicker.getMonth() + 1);
        if (datePickerYear > currentYear || (datePickerYear == currentYear && datePickerMonth > currentMonth)) {
            return "Invalid";
        }
        return datePickerMonth + "/" + datePicker.getDayOfMonth() + "/" + datePickerYear;
    }

    public List<String> insertBookData(String author, String title, String description, String edition) {
        if (validAuthor(author) && validTitle(title) && validDescription(description) && validEdition(edition)) {
            return new ArrayList<String>() {{
                add(author);
                add(title);
                add(description);
                add(edition);
            }};
        }
        return new ArrayList<>();
    }
}
