package com.android.blackoder.makta.model.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created By blackcoder
 * On 25/01/19
 **/
@Entity(tableName = "my_books_table")
public final class Book {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private long _id;

    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "author")
    private String author;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "published")
    private String datePublished;
    @ColumnInfo(name = "edition")
    private String edition;

    @Ignore
    public Book() {
    }


    public Book(@NonNull String author, @NonNull String title, @NonNull String description, @NonNull String datePublished, String edition) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.datePublished = datePublished;
        this.edition = edition;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(String datePublished) {
        this.datePublished = datePublished;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    @Override
    public String toString() {
        return "Book{" +
                "author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", datePublished='" + datePublished + '\'' +
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
