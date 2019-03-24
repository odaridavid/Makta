package com.android.blackoder.makta.model.entities;

import android.support.annotation.NonNull;

import org.parceler.Parcel;

/**
 * Created By blackcoder
 * On 16/03/19
 **/
@Parcel
public final class SharedBook implements IBook {

    String title;
    String author;
    String description;
    String published;
    String edition;
    String user;


    SharedBook() {
    }


    public SharedBook(@NonNull String author, @NonNull String title, @NonNull String description, @NonNull String published, String edition, String user) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.published = published;
        this.edition = edition;
        this.user = user;
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @NonNull
    @Override
    public String toString() {
        return "Book{" +
                "author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", datePublished='" + published + '\'' +
                ", edition='" + edition + '\'' +
                ", user='" + user + '\'' +
                '}';
    }
}
