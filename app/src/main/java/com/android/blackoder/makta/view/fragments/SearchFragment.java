package com.android.blackoder.makta.view.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.blackoder.makta.R;
import com.android.blackoder.makta.model.books.FirestoreViewModel;
import com.android.blackoder.makta.model.entities.SharedBook;
import com.android.blackoder.makta.utils.AppUtils;
import com.android.blackoder.makta.view.BookDetailActivity;
import com.android.blackoder.makta.view.adapters.BookViewHolder;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.parceler.Parcels;

import static com.android.blackoder.makta.utils.Constants.BOOK_DETAIL;
import static com.android.blackoder.makta.utils.Constants.BOOK_DETAIL_REQUEST;

/**
 * Created By blackcoder
 * On 30/01/19
 **/
public final class SearchFragment extends Fragment {

    private ImageButton mSearch;
    private EditText mSearchView;
    private FirestoreRecyclerAdapter adapter;
    private RecyclerView rvSearchResults;
    private ProgressBar lProgressBar;

    public SearchFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        setupViews(rootView);
        LinearLayoutManager lLinearLayoutManager = new LinearLayoutManager(getActivity());
        rvSearchResults.setLayoutManager(lLinearLayoutManager);
        rvSearchResults.setHasFixedSize(true);
        FirestoreViewModel lFirestoreViewModel = ViewModelProviders.of(this).get(FirestoreViewModel.class);
        mSearch.setOnClickListener(v -> {
            lProgressBar.setVisibility(View.VISIBLE);
            String bookTitle = mSearchView.getText().toString();
            FirestoreRecyclerOptions firestoreRecyclerOptions = lFirestoreViewModel.searchBook(bookTitle.trim());
            setUpAdapter(firestoreRecyclerOptions);
        });

        AppUtils.recyclerViewDecoration(rvSearchResults, lLinearLayoutManager);
        return rootView;
    }

    private void setupViews(View rootView) {
        mSearchView = rootView.findViewById(R.id.search_books);
        mSearch = rootView.findViewById(R.id.btn_search);
        lProgressBar = rootView.findViewById(R.id.progress_bar_search);
        rvSearchResults = rootView.findViewById(R.id.recycler_view_search_results);
    }

    private void setUpAdapter(FirestoreRecyclerOptions firestoreRecyclerOptions) {
        adapter = new FirestoreRecyclerAdapter<SharedBook, BookViewHolder>(firestoreRecyclerOptions) {

            @NonNull
            @Override
            public BookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater
                        .from(viewGroup.getContext())
                        .inflate(R.layout.list_item_my_books, viewGroup, false);
                return new BookViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull BookViewHolder booksViewHolder, int position, @NonNull SharedBook model) {
                booksViewHolder.tvBookTitle.setText(model.getTitle());
                booksViewHolder.tvBookAuthor.setText(model.getAuthor());
                booksViewHolder.itemView.setOnClickListener(v -> {
                    Intent intent = new Intent(getActivity(), BookDetailActivity.class);
                    intent.putExtra(BOOK_DETAIL, Parcels.wrap(model));
                    intent.putExtra(BOOK_DETAIL_REQUEST, "request");
                    startActivity(intent);
                });

            }

            @Override
            public void onError(@NonNull FirebaseFirestoreException e) {
                super.onError(e);
                Log.e("error", e.getMessage());
            }

            @Override
            public void onDataChanged() {
                super.onDataChanged();
                lProgressBar.setVisibility(View.GONE);
                if (!(super.getItemCount() > 0)) {
                    displayError();
                }
                rvSearchResults.setAdapter(this);
                notifyDataSetChanged();
            }
        };
        adapter.startListening();
    }


    public void displayError() {
        Toast.makeText(getActivity(), "No Book Found", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }
}
