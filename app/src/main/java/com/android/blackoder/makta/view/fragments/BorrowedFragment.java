package com.android.blackoder.makta.view.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.blackoder.makta.R;
import com.android.blackoder.makta.model.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By blackcoder
 * On 30/01/19
 **/
public final class BorrowedFragment extends Fragment {
    public BorrowedFragment() {

    }

    private List<Book> borrowedBooksList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_borrowed, container, false);
        TextView tvNoBorrowedBooks = view.findViewById(R.id.text_view_no_borrowed_books);
        RecyclerView rvBorrowedBooks = view.findViewById(R.id.recycler_view_borrowed_books_list);
        if (borrowedBooksList.isEmpty()) {
            tvNoBorrowedBooks.setVisibility(View.VISIBLE);
            rvBorrowedBooks.setVisibility(View.GONE);
        } else {
            tvNoBorrowedBooks.setVisibility(View.GONE);
            rvBorrowedBooks.setVisibility(View.VISIBLE);
        }
        return view;
    }
}
