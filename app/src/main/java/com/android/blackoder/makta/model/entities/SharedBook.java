package com.android.blackoder.makta.model.entities;

import android.arch.persistence.room.Ignore;
import android.support.annotation.NonNull;

import org.parceler.Parcel;

/**
 * Created By blackcoder
 * On 16/03/19
 **/
@Parcel
public final class SharedBook {

    String title;
    String author;
    String description;
    String published;
    String edition;
    String user;

    @Ignore
    public SharedBook() {
    }


    public SharedBook(@NonNull String author, @NonNull String title, @NonNull String description, @NonNull String published, String edition, String user) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.published = published;
        this.edition = edition;
        this.user = user;
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

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getEdition() {
        return edition;
    }

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
