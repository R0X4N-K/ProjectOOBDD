package model;

import java.util.Date;

public class ArticleVersion {

    public enum Status {
        WAITING,
        ACCEPTED,
        REJECTED// opzionalmente può terminare con ";"
    }

    private Article parentArticle;
    private Status status = Status.WAITING;
    private int id;
    private String text;
    private Date versionDate;
    private Date revisionDate = null;
    private Author authorProposal; // Aggiunto

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
