package com.android.blackoder.makta.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created By blackcoder
 * On 27/03/19
 **/
public final class WidgetUpdateService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public WidgetUpdateService() {
        super("WidgetUpdateService");
    }

    public static final String ACTION_GET_REQUESTS =
            "com.android.blackoder.makta.widget.action.book_requests";

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GET_REQUESTS.equals(action)) {
                handleActionGetRequests();
            }
        }
    }

    private void handleActionGetRequests() {
        FirebaseAuth lFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser lCurrentUser = lFirebaseAuth.getCurrentUser();
        if (lCurrentUser != null) {
            AppWidgetManager lAppWidgetManager = AppWidgetManager.getInstance(this);
            int[] appWidgetIds = lAppWidgetManager.getAppWidgetIds(new ComponentName(this, RequestWidget.class));
            for (int appWidgetId : appWidgetIds) {
                RequestWidget.updateAppWidgetList(this, lAppWidgetManager, appWidgetId);
            }
        }
    }

    public static void startActionGetRequests(Context context) {
        Intent intent = new Intent(context, WidgetUpdateService.class);
        intent.setAction(ACTION_GET_REQUESTS);
        context.startService(intent);
    }
}
