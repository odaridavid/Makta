package com.android.blackoder.makta.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created By blackcoder
 * On 18/03/19
 **/
@Entity(tableName = "wish_list_table")
public class WishBook {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private long _id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "author")
    private String author;

    public WishBook(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @NonNull
    @Override
    public String toString() {
        return "WishBook{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
