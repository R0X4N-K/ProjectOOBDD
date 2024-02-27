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
    private final ArrayList<ArticleVersion> proposedChanges;

    public Article (String title, Author author) throws Exception {
        setTitle (title);
        setAuthor (author);
        this.creationDate = new Date();
        this.currentVersionArticle = new ArticleVersion();
        this.proposedChanges = new ArrayList<>();
        // TODO: dataCreazione deve riferirsi a orario Server
    }

    public Article (String title, Author author, ArticleVersion currentVersionArticle) throws Exception {
        setTitle (title);
        setAuthor (author);
        this.creationDate = new Date();
        setCurrentVersionArticle (currentVersionArticle);
        this.proposedChanges = new ArrayList<>();
        // TODO: dataCreazione deve riferirsi a orario Server
    }

    public Article (ResultSet resultSet) throws SQLException, RuntimeException {

        try {
            setTitle (resultSet.getString("title"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        setCreationDate (resultSet.getDate("creation_date"));
        setRevision (resultSet.getBoolean("revision"));
        try {
            this.author = new Author(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            this.currentVersionArticle = new ArticleVersion(resultSet);
        } catch (SQLException | RuntimeException e) {
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
