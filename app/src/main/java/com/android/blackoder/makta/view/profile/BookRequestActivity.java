package com.android.blackoder.makta.view.profile;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.blackoder.makta.R;
import com.android.blackoder.makta.model.books.FirestoreViewModel;
import com.android.blackoder.makta.model.entities.BookRequests;
import com.android.blackoder.makta.utils.AppUtils;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;

public class BookRequestActivity extends AppCompatActivity {
    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_request);
        RecyclerView rvBookRequests = findViewById(R.id.recycler_view_book_requests);
        LinearLayoutManager lLinearLayoutManager = new LinearLayoutManager(this);
        rvBookRequests.setLayoutManager(lLinearLayoutManager);
        rvBookRequests.setHasFixedSize(true);
        TextView tvNoBookRequests = findViewById(R.id.text_view_no_book_request);
        FirestoreViewModel lFirestoreViewModel = ViewModelProviders.of(this).get(FirestoreViewModel.class);
        adapter = new FirestoreRecyclerAdapter<BookRequests, BookRequestsViewHolder>(lFirestoreViewModel.loadBookRequests()) {

            @NonNull
            @Override
            public BookRequestsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.list_item_request, viewGroup, false);
                return new BookRequestsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull BookRequestsViewHolder holder, int position, @NonNull BookRequests model) {
                holder.tvRequester.setText(model.getRequester());
                holder.tvRequestBody.setText(model.getBody());
            }

            @Override
            public void onDataChanged() {
                super.onDataChanged();
                if (!(super.getItemCount() > 0)) {
                    tvNoBookRequests.setText(getString(R.string.no_book_requests));
                }
                rvBookRequests.setAdapter(this);
                notifyDataSetChanged();
            }
        };
        AppUtils.recycelrViewDecoration(rvBookRequests, lLinearLayoutManager);
    }

    class BookRequestsViewHolder extends RecyclerView.ViewHolder {
        TextView tvRequester, tvRequestBody;

        BookRequestsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRequester = itemView.findViewById(R.id.text_view_requester);
            tvRequestBody = itemView.findViewById(R.id.text_view_request_body);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }
}
