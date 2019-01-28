package com.android.blackoder.makta.model;

/**
 * Created By blackcoder
 * On 25/01/19
 **/
public final class Book {
    private String author;
    private String description;
    private String datePublished;
    private String imagePath;
    private String edition;

    public Book() {
    }

    public Book(String author, String description, String datePublished, String imagePath, String edition) {
        this.author = author;
        this.description = description;
        this.datePublished = datePublished;
        this.imagePath = imagePath;
        this.edition = edition;
    }

    public Book(String author, String description, String datePublished, String edition) {
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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
                ", description='" + description + '\'' +
                ", datePublished='" + datePublished + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", edition='" + edition + '\'' +
                '}';
    }
}
