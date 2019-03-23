package com.android.blackoder.makta.model.entities;

import android.support.annotation.NonNull;

import org.parceler.Parcel;

/**
 * Created By blackcoder
 * On 22/03/19
 **/
@Parcel
public class BookRequests {
    String body;
    String requester;

    BookRequests() {
    }

    public BookRequests(String body, String requester) {
        this.body = body;
        this.requester = requester;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    @NonNull
    @Override
    public String toString() {
        return "BookRequests{" +
                "body='" + body + '\'' +
                ", requester='" + requester + '\'' +
                '}';
    }
}
