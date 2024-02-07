package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ArticleVersion {
    public enum Status {
        WAITING,
        ACCEPTED,
        REJECTED
    }

    private Article parentArticle;
    private Status status = Status.WAITING;
    private int id;
    private String text;
    private Date versionDate;
    private Date revisionDate = null;
    private Author authorProposal;

    public ArticleVersion() {

    }
    public ArticleVersion(int id, Article parentArticle, String text) {

    }
    public ArticleVersion(int id, Article parentArticle, String text, Author authorProposal) {

    }

    public ArticleVersion(ResultSet resultSet) throws SQLException, RuntimeException{
        String statusString = resultSet.getString("status");
        try {
            this.status = Status.valueOf(statusString);
        } catch (IllegalArgumentException e) {
            System.out.println("La stringa fornita non corrisponde a nessuno dei valori dell'enum Status.");
        }
        this.id = resultSet.getInt("id");
        this.text = resultSet.getString("text");
        this.versionDate = resultSet.getDate("version_date");
        this.revisionDate = resultSet.getDate("revision_date");
        this.authorProposal = new Author(resultSet);
    }

    public Article getParentArticle() {
        return parentArticle;
    }

    public void setParentArticle(Article parentArticle) {
        this.parentArticle = parentArticle;
    }

    public Status getStatus(){
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }
    public int setId(int id) {
        return this.id = id;
    }


    public String getText() {
        return text;
    }

    public void setText(String testo) {
        this.text = testo;
    }



    public Date getVersionDate() {
        return versionDate;
    }

    public void setVersionDate(Date versionDate) {
        this.versionDate = versionDate;
    }



    public Date getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(Date revisionDate) {
        this.revisionDate = revisionDate;

    }
    public Author getAuthorProposal() {
        return authorProposal;
    }

    public void setAuthorProposal(Author authorProposta) {
        this.authorProposal = authorProposta;
    }

}
