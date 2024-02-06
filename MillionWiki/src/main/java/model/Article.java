package model;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.lang.Exception;


public class Article {

    String title;
    Date creationDate;
    boolean revision = false;
    private Author author;
    private ArticleVersion currentVersionArticle;
    private ArrayList<ArticleVersion> proposedChanges;

    public Article(String title, Author author) throws Exception {
        setTitle(title);
        this.author = author;
        this.creationDate = new Date();
        this.currentVersionArticle = new ArticleVersion();
        this.proposedChanges = new ArrayList<>();
        // TODO: dataCreazione deve riferirsi a orario Server
    }

    public Article(String title, Author author, ArticleVersion currentVersionArticle) throws Exception {
        setTitle(title);
        this.author = author;
        this.creationDate = new Date();
        this.currentVersionArticle = currentVersionArticle;
        this.proposedChanges = new ArrayList<>();
        // TODO: dataCreazione deve riferirsi a orario Server
    }

    public Article(ResultSet resultSet) throws SQLException, RuntimeException {
        this.title = resultSet.getString("title");
        this.creationDate = resultSet.getDate("creation_date");
        this.revision = resultSet.getBoolean("revision");
        this.author = new Author(resultSet);
        try {
            this.currentVersionArticle = new ArticleVersion(resultSet);
        } catch (SQLException e) {
              e.printStackTrace();
        }
        this.proposedChanges = new ArrayList<>(); // Questo campo potrebbe non essere inizializzato dal ResultSet
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

    public ArrayList<ArticleVersion> getProposedChanges() {
        return proposedChanges;
    }


    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public ArticleVersion getCurrentVersionArticle() {
        return currentVersionArticle;
    }
    public void setCurrentVersionArticle(ArticleVersion currentVersionArticle) {
        this.currentVersionArticle = currentVersionArticle;
    }
}