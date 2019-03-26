package com.android.blackoder.makta.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created By blackcoder
 * On 26/03/19
 **/
public final class RequestService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new RequestListProvider(this.getApplicationContext(), intent));
    }
}
