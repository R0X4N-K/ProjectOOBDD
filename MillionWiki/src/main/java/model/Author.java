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

    public Article scriviArticolo(String titolo) {
        Article nuovoArticle = null;
        try {
            nuovoArticle = new Article(titolo, this);
            addNewArticle(nuovoArticle);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nuovoArticle;
    }


    public ArticleVersion scriviArticolo(Article article, String testo) {
        ArticleVersion nuovaVersione = new ArticleVersion();
        nuovaVersione.setAuthorProposal(this);
        article.addModifica(nuovaVersione);
        return nuovaVersione;
    }

    public ArticleVersion scriviArticolo(Article article, String testo, String titolo) {
        ArticleVersion nuovaVersione = scriviArticolo(article, testo);
        nuovaVersione.setVersioneTitolo(titolo);
        return nuovaVersione;
    }



    public void visualizzaProposta(ArticleVersion articleVersion) {

    }

    public void modificaStatoVersioneArticolo(ArticleVersion articleVersion) {

    }




}
