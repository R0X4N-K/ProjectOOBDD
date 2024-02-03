package model;

import java.util.ArrayList;

public class Author extends User {
    private String nickname;
    private String password;
    private float valutazione;
    private ArrayList<Article> pagineCreate;
    public Author(String nomeUtente, String password) throws Exception {

        setNickname(nomeUtente);

        setPassword(password);
        this.pagineCreate = new ArrayList<>();
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



    public float getValutazione(){

        return valutazione;
    }

    public void setValutazione (float valutazione) {
        this.valutazione = valutazione;
    }


    public ArrayList<Article> getPagineCreate() {
        return pagineCreate;
    }
    public void addPaginaCreata(Article article) {
        this.pagineCreate.add(article);
    }

    public Article scriviArticolo(String titolo) {
        Article nuovoArticle = null;
        try {
            nuovoArticle = new Article(titolo, this);
            addPaginaCreata(nuovoArticle);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nuovoArticle;
    }


    public ArticleVersion scriviArticolo(Article article, String testo) {
        ArticleVersion nuovaVersione = new ArticleVersion();
        nuovaVersione.setAutoreProposta(this);
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
