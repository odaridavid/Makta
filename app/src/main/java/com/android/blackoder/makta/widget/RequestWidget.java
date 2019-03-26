package com.android.blackoder.makta.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.android.blackoder.makta.R;
import com.android.blackoder.makta.view.BookRequestActivity;

/**
 * Implementation of App Widget functionality.
 */
public class RequestWidget extends AppWidgetProvider {


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            Intent intent = new Intent(context, BookRequestActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            RemoteViews remoteViews = updateAppWidgetList(context,
                    appWidgetId);
            remoteViews.setPendingIntentTemplate(R.id.text_view_request_body, pendingIntent);
            appWidgetManager.updateAppWidget(appWidgetId,
                    remoteViews);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private RemoteViews updateAppWidgetList(Context context, int appWidgetId) {
        RemoteViews remoteViews = new RemoteViews(
                context.getPackageName(), R.layout.request_widget);
        remoteViews.setRemoteAdapter(appWidgetId, R.id.list_widget_items,
                new Intent(context, RequestService.class));
        remoteViews.setEmptyView(R.id.list_widget_items, R.id.text_view_empty_list_widget);
        return remoteViews;
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

}

