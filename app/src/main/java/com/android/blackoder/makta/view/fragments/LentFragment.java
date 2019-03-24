package com.android.blackoder.makta.view.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.blackoder.makta.R;
import com.android.blackoder.makta.model.books.FirestoreViewModel;
import com.android.blackoder.makta.model.entities.Book;
import com.android.blackoder.makta.model.entities.Lent;
import com.android.blackoder.makta.utils.AppUtils;
import com.android.blackoder.makta.view.adapters.BookStateViewHolder;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By blackcoder
 * On 30/01/19
 **/
public final class LentFragment extends Fragment {
    public LentFragment() {

    }

    private List<Book> lentBooksList = new ArrayList<>();
    private FirestoreRecyclerAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lent, container, false);
        FirestoreViewModel mFirestoreViewModel = ViewModelProviders.of(this).get(FirestoreViewModel.class);
        setupAdapter(mFirestoreViewModel.loadLentOutBooks());
        TextView tvNoLentBooks = view.findViewById(R.id.text_view_no_lent_books);
        RecyclerView rvLentBooks = view.findViewById(R.id.recycler_view_lent_books_list);
        AppUtils.handleVisibility(lentBooksList, rvLentBooks, tvNoLentBooks);
        return view;
    }

    private void setupAdapter(FirestoreRecyclerOptions loadLentOutBooks) {
        adapter = new FirestoreRecyclerAdapter<Lent, BookStateViewHolder>(loadLentOutBooks) {

            @NonNull
            @Override
            public BookStateViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return null;
            }

            @Override
            protected void onBindViewHolder(@NonNull BookStateViewHolder holder, int position, @NonNull Lent model) {

            }
        };
    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
