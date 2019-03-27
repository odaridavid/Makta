package com.android.blackoder.makta.widget;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.android.blackoder.makta.R;
import com.android.blackoder.makta.model.entities.BookRequests;
import com.android.blackoder.makta.view.BookRequestActivity;
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

    RequestListProvider(Context context) {
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
                        if (e != null) Log.e("Snapshot Error:", e.getMessage());
                        else if (snapshots.size() > 0) {
                            if (mBookRequestsList.size() > 0) mBookRequestsList.clear();
                            Log.d("Snapshots", String.valueOf(snapshots.size()));
                            for (DocumentSnapshot doc : snapshots.getDocuments()) {
                                BookRequests lBookRequests = doc.toObject(BookRequests.class);
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
        RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.widget_item_request);
        Log.d("List Size", String.valueOf(mBookRequestsList.size()));
        BookRequests lBookRequests = mBookRequestsList.get(position);
        Log.d("Get View:", lBookRequests.toString());
        remoteView.setTextViewText(R.id.text_view_widget_requester, lBookRequests.getRequester());
        remoteView.setTextViewText(R.id.text_view_widget_request_body, lBookRequests.getBody());
        remoteView.setOnClickPendingIntent(R.id.text_view_widget_request_body, PendingIntent.getActivity(context, 0, new Intent(context, BookRequestActivity.class), 0));
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
