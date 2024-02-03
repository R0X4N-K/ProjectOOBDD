package model;

import java.util.ArrayList;

public class Author extends User {
    private String nickname;
    private String password;
    private float rating;
    private ArrayList<Article> createdPages;
    public Author(String nickname, String password) throws Exception {

        setNickname(nickname);

        setPassword(password);
        this.createdPages = new ArrayList<>();
    }

    public String getNickname(){
        return nickname;
    }

    public void setNickname(String nome) throws Exception{
        if (!nickname.isEmpty() && !password.isBlank()) {
            this.nickname = nome;
        } else {
            throw new Exception("NOME UTENTE VUOTO!"); // TODO: creare eccezione ad hoc
        }
    }



    public void setPassword(String password) throws Exception{
        if (!password.isEmpty() && !password.isBlank()) {
            this.password = password;
        } else {
            throw new Exception("PASSWORD VUOTA!"); // TODO: creare eccezione ad hoc
        }
    }



    public float getRating(){

        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }


    public ArrayList<Article> getCreatedPages() {
        return createdPages;
    }
    public void addNewArticle(Article article) {
        this.createdPages.add(article);
    }

    public Article writeArticle(String title) {
        Article newArticle = null;
        try {
            newArticle = new Article(title, this);
            addNewArticle(newArticle);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newArticle;
    }


    public ArticleVersion writeArticle(Article article, String testo) {
        ArticleVersion newVersione = new ArticleVersion();
        newVersione.setAuthorProposal(this);
        article.addModifica(newVersione);
        return newVersione;
    }

    public ArticleVersion writeArticle(Article article, String text, String title) {
        ArticleVersion newVersion = writeArticle(article, text);
        newVersion.setVersioneTitolo(title);
        return newVersion;
    }



    public void viewProposal(ArticleVersion articleVersion) {

    }

    public void editStatusArticleVersion(ArticleVersion articleVersion) {

    }




}
