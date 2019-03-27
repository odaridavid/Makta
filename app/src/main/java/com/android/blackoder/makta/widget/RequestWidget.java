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
        WidgetUpdateService.startActionGetRequests(context);
    }

    public static void updateAppWidgetList(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.request_widget);
        Intent lIntent = new Intent(context, RequestService.class);
        remoteViews.setRemoteAdapter(R.id.list_widget_items, lIntent);
        remoteViews.setEmptyView(R.id.list_widget_items, R.id.text_view_empty_list_widget);
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
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

