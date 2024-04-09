package model;
import controller.Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.lang.Exception;


public class Article {
    int id;
    String title;
    Date creationDate;
    boolean revision = false;
    private Author author;


    private int views;

    public Article (String title, Author author) throws Exception {
        setTitle (title);
        setAuthor (author);
        this.creationDate = new Date();

        // TODO: dataCreazione deve riferirsi a orario Server
    }

    public Article (ResultSet resultSet) throws SQLException, RuntimeException {

        try {
            setId(resultSet.getInt("id"));
            setTitle (resultSet.getString("title"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        setCreationDate (resultSet.getDate("creation_date"));
        setRevision (resultSet.getBoolean("revision"));
        setViews(resultSet.getInt("views"));
        try {
            this.author = (Controller.getAuthorById(resultSet.getInt("author")));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public boolean isRevision() {
        return revision;
    }

    public void setRevision(boolean revision) {
        this.revision = revision;
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

    public void setTitle(String title) throws Exception {

        if(!title.isEmpty() && !title.isBlank()) {
            this.title = title;
        }
        else {
            throw new Exception("Titolo Vuoto"); // TODO: Creare eccezione ad hoc
        }
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getViews() {
        return views;
    }
    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

}
