package model;
import implementationDAO.ArticleDAOImplementation;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Author extends User {
    private int id;
    private String email;
    private String nickname;
    private String password;
    private float rating;
    private ArrayList<Article> createdPages;
    public Author(int id, String email, String nickname, String password) throws Exception {
        setId(id);
        setEmail(email);
        setNickname(nickname);
        setPassword(password);
        this.rating = 0;
        this.createdPages = new ArrayList<>();
    }
    public Author(ResultSet resultSet) throws SQLException, RuntimeException, Exception{
        setId(resultSet.getInt("id"));
        setEmail(resultSet.getString("email"));
        setNickname(resultSet.getString("nickname"));
        setPassword(resultSet.getString("password"));
        setRating(resultSet.getFloat("rating"));

        this.createdPages = new ArticleDAOImplementation().getAllArticlesByAuthor(id);
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


    public ArticleVersion writeArticle(Article article, String text) {
        ArticleVersion newVersion = new ArticleVersion();
        newVersion.setAuthorProposal(this);
        //article.addModifica(newVersione);
        return newVersion;
    }

    public ArticleVersion writeArticle(Article article, String text, String title) {
        ArticleVersion newVersion = writeArticle(article, text);
        //newVersion.setVersioneTitolo(title);
        return newVersion;
    }



    public void viewProposal(ArticleVersion articleVersion) {

    }

    public void editStatusArticleVersion(ArticleVersion articleVersion) {

    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
