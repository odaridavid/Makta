package com.android.blackoder.makta.model.entities;

import android.support.annotation.NonNull;

import org.parceler.Parcel;

/**
 * Created By blackcoder
 * On 24/03/19
 **/
@Parcel
public final class Lent {
    String to;
    String title;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NonNull
    @Override
    public String toString() {
        return "Lent{" +
                "to='" + to + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
