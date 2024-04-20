package model;
import java.util.Date;
import java.lang.Exception;


public class Article {
    int id;
    String title;
    Date creationDate;
    private Author author;
    private int views;

    public Article (int id, String title, Author author, Date creationDate) throws Exception {
        setTitle (title);
        setAuthor (author);
        setId(id);
        setCreationDate(creationDate);
        this.creationDate = new Date();

        // TODO: dataCreazione deve riferirsi a orario Server
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
