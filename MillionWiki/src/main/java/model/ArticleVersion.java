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
    private Author authorProposal; // Aggiunto

    public ArticleVersion() {
        this.parentArticle = parentArticle;
        this.text = text;
        this.versionDate = new Date();
        this.authorProposal = authorProposal;
    }

    public ArticleVersion(ResultSet resultSet) throws SQLException, RuntimeException, IllegalArgumentException {
        String statusString = resultSet.getString("status");
        this.status = Status.valueOf(statusString);
        this.id = resultSet.getInt("id");
        this.text = resultSet.getString("text");
        this.versionDate = resultSet.getDate("version_date");
        this.revisionDate = resultSet.getDate("revision_date");
        this.authorProposal = new Author(resultSet);
    }
    public int getId() {
        return id;
    }

    public Status getStatus(){
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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
