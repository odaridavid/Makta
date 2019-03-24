package com.android.blackoder.makta.model.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import org.parceler.Parcel;

/**
 * Created By blackcoder
 * On 25/01/19
 **/
@Parcel
@Entity(tableName = "my_books_table")
public final class Book implements IBook {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    long _id;

    @ColumnInfo(name = "title")
    String title;
    @ColumnInfo(name = "author")
    String author;
    @ColumnInfo(name = "description")
    String description;
    @ColumnInfo(name = "published")
    String published;
    @ColumnInfo(name = "edition")
    String edition;

    @Ignore
    public Book() {
    }


    public Book(@NonNull String author, @NonNull String title, @NonNull String description, @NonNull String published, String edition) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.published = published;
        this.edition = edition;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getPublished() {
        return published;
    }

    @Override
    public void setPublished(String published) {
        this.published = published;
    }

    @Override
    public String getEdition() {
        return edition;
    }

    @Override
    public void setEdition(String edition) {
        this.edition = edition;
    }

    @Override
    public String toString() {
        return "Book{" +
                "author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", datePublished='" + published + '\'' +
                ", edition='" + edition + '\'' +
                '}';
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }
}
