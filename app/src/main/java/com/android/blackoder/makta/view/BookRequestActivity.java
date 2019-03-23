package com.android.blackoder.makta.view;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.blackoder.makta.R;
import com.android.blackoder.makta.model.books.FirestoreViewModel;
import com.android.blackoder.makta.model.entities.BookRequests;
import com.android.blackoder.makta.utils.AppUtils;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public final class BookRequestActivity extends AppCompatActivity {
    private FirestoreRecyclerAdapter adapter;
    private RecyclerView mRvBookRequests;
    private TextView mTvNoBookRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_request);
        mRvBookRequests = findViewById(R.id.recycler_view_book_requests);
        LinearLayoutManager lLinearLayoutManager = new LinearLayoutManager(this);
        mRvBookRequests.setLayoutManager(lLinearLayoutManager);
        mRvBookRequests.setHasFixedSize(true);
        mTvNoBookRequests = findViewById(R.id.text_view_no_book_request);
        setupAdapter();
        AppUtils.recyclerViewDecoration(mRvBookRequests, lLinearLayoutManager);
        acceptOrDeclineRequest(mRvBookRequests);
    }

    public void setupAdapter() {
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
                    mTvNoBookRequests.setVisibility(View.VISIBLE);
                    mTvNoBookRequests.setText(getString(R.string.no_book_requests));
                }
                mRvBookRequests.setAdapter(this);
                notifyDataSetChanged();
            }
        };
    }

    public void acceptOrDeclineRequest(RecyclerView recyclerView) {
//        Setup swipe to dismiss or accept request
        ItemTouchHelper lItemTouchHelperDismiss = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                // TODO delete from firestore requests
                Toast.makeText(BookRequestActivity.this, "Left", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                new RecyclerViewSwipeDecorator.Builder(BookRequestActivity.this, c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addBackgroundColor(ContextCompat.getColor(BookRequestActivity.this, android.R.color.holo_red_dark))
                        .addActionIcon(R.drawable.ic_clear)
                        .create()
                        .decorate();

            }
        });
        ItemTouchHelper lItemTouchHelperAccept = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                // TODO add to lent ,add to borrowed for user ,remove from recycler view refresh layout
                Toast.makeText(BookRequestActivity.this, "Right", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                new RecyclerViewSwipeDecorator.Builder(BookRequestActivity.this, c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addBackgroundColor(ContextCompat.getColor(BookRequestActivity.this, android.R.color.holo_green_dark))
                        .addActionIcon(R.drawable.ic_accept)
                        .create()
                        .decorate();

            }
        });
        lItemTouchHelperDismiss.attachToRecyclerView(recyclerView);
        lItemTouchHelperAccept.attachToRecyclerView(recyclerView);
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
