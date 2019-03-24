package com.android.blackoder.makta.view.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.blackoder.makta.R;
import com.android.blackoder.makta.model.books.FirestoreViewModel;
import com.android.blackoder.makta.model.entities.Borrowed;
import com.android.blackoder.makta.utils.AppUtils;
import com.android.blackoder.makta.view.adapters.BookStateViewHolder;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

/**
 * Created By blackcoder
 * On 30/01/19
 **/
public final class BorrowedFragment extends Fragment {

    private TextView mTvNoBorrowedBooks;
    private RecyclerView mRvBorrowedBooks;

    public BorrowedFragment() {

    }

    private FirestoreRecyclerAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_borrowed, container, false);
        FirestoreViewModel mFirestoreViewModel = ViewModelProviders.of(this).get(FirestoreViewModel.class);
        setupAdapter(mFirestoreViewModel.loadBorrowedBooks());
        mTvNoBorrowedBooks = view.findViewById(R.id.text_view_no_borrowed_books);
        mRvBorrowedBooks = view.findViewById(R.id.recycler_view_borrowed_books_list);
        LinearLayoutManager lLinearLayoutManager = new LinearLayoutManager(getContext());
        mRvBorrowedBooks.setLayoutManager(lLinearLayoutManager);
        AppUtils.recyclerViewDecoration(mRvBorrowedBooks, lLinearLayoutManager);
        return view;
    }

    private void setupAdapter(FirestoreRecyclerOptions loadBorrowedBooks) {
        adapter = new FirestoreRecyclerAdapter<Borrowed, BookStateViewHolder>(loadBorrowedBooks) {
            @NonNull
            @Override
            public BookStateViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_book_state, viewGroup, false);
                return new BookStateViewHolder(v);
            }

            @Override
            protected void onBindViewHolder(@NonNull BookStateViewHolder holder, int position, @NonNull Borrowed model) {
                holder.tvTitle.setText(model.getBorrowed());
                String owner = "Owner: ";
                holder.tvSub.setText(owner.concat(model.getFrom()));
            }

            @Override
            public void onDataChanged() {
                super.onDataChanged();
                if (!(super.getItemCount() > 0)) {
                    mTvNoBorrowedBooks.setVisibility(View.VISIBLE);
                    mTvNoBorrowedBooks.setText(getString(R.string.no_borrowed_books));
                } else {
                    mTvNoBorrowedBooks.setVisibility(View.GONE);
                    mRvBorrowedBooks.setAdapter(this);
                    notifyDataSetChanged();
                }
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
