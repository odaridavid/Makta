package com.android.blackoder.makta.model.entities;

/**
 * Created By blackcoder
 * On 24/03/19
 **/
public interface IBook {
    void setAuthor(String author);

    void setDescription(String description);

    void setPublished(String published);

    void setEdition(String edition);

    void setTitle(String title);

    String getAuthor();

    String getDescription();

    String getPublished();

    String getEdition();

    String getTitle();
}
