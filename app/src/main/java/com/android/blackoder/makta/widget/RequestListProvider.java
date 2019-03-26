package com.android.blackoder.makta.widget;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.android.blackoder.makta.R;
import com.android.blackoder.makta.model.entities.BookRequests;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import static com.android.blackoder.makta.utils.Constants.COLLECTION_BOOKS;
import static com.android.blackoder.makta.utils.Constants.COLLECTION_REQUESTS;

/**
 * Created By blackcoder
 * On 26/03/19
 **/
public final class RequestListProvider implements RemoteViewsService.RemoteViewsFactory {
    private List<BookRequests> mBookRequestsList;
    private Context context = null;

    RequestListProvider(Context context, Intent intent) {
        this.context = context;
        mBookRequestsList = new ArrayList<>();
    }

    private void initDataFirestore() {
        FirebaseAuth lFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser lCurrentUser = lFirebaseAuth.getCurrentUser();
        if (lCurrentUser != null) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db
                    .collection(COLLECTION_REQUESTS)
                    .document(lCurrentUser.getUid())
                    .collection(COLLECTION_BOOKS)
                    .addSnapshotListener((snapshots, e) -> {
                        if (e != null) Log.e("Firestore Widget Error", e.getMessage());
                        else {
                            for (final DocumentSnapshot doc : snapshots.getDocuments()) {
                                BookRequests lBookRequests = doc.toObject(BookRequests.class);
                                Log.d("Requests", lBookRequests.toString());
                                mBookRequestsList.add(lBookRequests);
                            }
                        }
                    });
        }
    }

    @Override
    public void onCreate() {
        initDataFirestore();
    }

    @Override
    public void onDataSetChanged() {
        initDataFirestore();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mBookRequestsList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(
                context.getPackageName(), R.layout.list_item_request);
        BookRequests lBookRequests = mBookRequestsList.get(position);
        Log.d("Get View", lBookRequests.toString());
        remoteView.setTextViewText(R.id.text_view_requester, lBookRequests.getRequester());
        remoteView.setTextViewText(R.id.text_view_request_body, lBookRequests.getBody());
        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
