package com.android.blackoder.makta.model.entities;

import android.support.annotation.NonNull;

import org.parceler.Parcel;

/**
 * Created By blackcoder
 * On 24/03/19
 **/
@Parcel
public final class Borrowed {
    String borrowed;
    String from;

    public String getBorrowed() {
        return borrowed;
    }

    public void setBorrowed(String borrowed) {
        this.borrowed = borrowed;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @NonNull
    @Override
    public String toString() {
        return "Borrowed{" +
                "borrowed='" + borrowed + '\'' +
                ", from='" + from + '\'' +
                '}';
    }
}
