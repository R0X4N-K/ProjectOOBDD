package model;

import controller.Controller;

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

    public String getTitleProposal() {
        return titleProposal;
    }

    public void setTitleProposal(String titleProposal) {
        this.titleProposal = titleProposal;
    }

    private String titleProposal;

    public ArticleVersion() {

    }
    public ArticleVersion(int id, String text, Date versionDate, Date revisionDate, Article parentArticle, Author authorProposal) {
        setId(id);
        setAuthorProposal(authorProposal);
        setText(text);
        setVersionDate(versionDate);
        setRevisionDate(revisionDate);
        setParentArticle(parentArticle);
    }
    public ArticleVersion(int id, Date versionDate, Article parentArticle, Author authorProposal) {
        setId(id);
        setAuthorProposal(authorProposal);
        setText(text);
        setVersionDate(versionDate);
        setParentArticle(parentArticle);
    }

    public ArticleVersion(int id, Article parentArticle, String text, String titleProposal) {
        setId(id);
        setParentArticle(parentArticle);
        setText(text);
        setTitleProposal(titleProposal);
        setStatus(Status.WAITING);
        this.versionDate = new Date();
        setAuthorProposal(parentArticle.getAuthor());
    }

    public ArticleVersion(int id, Article parentArticle, String text, String titleProposal, Author authorProposal, Date versionDate, String status) {
        setId(id);
        setParentArticle(parentArticle);
        setText(text);
        setTitleProposal(titleProposal);
        setStatus(Status.WAITING);
        setTitleProposal(titleProposal);
        setAuthorProposal(authorProposal);
        this.versionDate = new Date();
        setVersionDate(versionDate);
        try {
            setStatus(status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ArticleVersion(int id, Article parentArticle, String text, Author authorProposal) {
        setId(id);
        setParentArticle(parentArticle);
        setText(text);
        setStatus(Status.WAITING);
        this.versionDate = new Date();
        setAuthorProposal(authorProposal);
    }

    public ArticleVersion(ResultSet resultSet) throws SQLException, RuntimeException{
        String statusString = resultSet.getString("status");
        try {
            setStatus(Status.valueOf(statusString));
        } catch (IllegalArgumentException e) {
            System.out.println("La stringa fornita non corrisponde a nessuno dei valori dell'enum Status.");
        }
        setId(resultSet.getInt("id"));
        setText(resultSet.getString("text"));
        setVersionDate(resultSet.getDate("version_date"));
        setRevisionDate(resultSet.getDate("revision_date"));
        setParentArticle(Controller.getArticlesById(resultSet.getInt("parent_article")));
        try {
            setAuthorProposal(Controller.getAuthorById(resultSet.getInt("author_proposal")));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public void setStatus(String status) throws Exception {

        switch (status){
            case "WAITING" : this.status = Status.WAITING; break;
            case "REJECTED" : this.status = Status.REJECTED; break;
            case "ACCEPTED" : this.status = Status.ACCEPTED; break;
            default : throw new Exception("Uno stato può essere uguale solo a waiting, accepted e rejected");
        }
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

    public void setAuthorProposal(Author authorProposal){
        this.authorProposal = authorProposal;
    }
}
