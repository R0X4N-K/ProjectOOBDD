package model;

import java.util.Date;


public class Article {
    int id;
    String title;
    Date creationDate;
    private Author author;
    private int views;

    public Article(int id, String title, Author author, Date creationDate) throws IllegalArgumentException {
        setTitle(title);
        setAuthor(author);
        setId(id);
        setCreationDate(creationDate);
        this.creationDate = new Date();

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) throws IllegalArgumentException {

        if (!title.isEmpty() && !title.isBlank()) {
            this.title = title;
        } else {
            throw new IllegalArgumentException("Titolo Vuoto");
        }
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

}
