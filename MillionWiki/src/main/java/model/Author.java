package model;

import java.util.ArrayList;

public class Author extends User {
    private final ArrayList<Article> createdPages;
    private int id;
    private String email;
    private String nickname;
    private float rating;

    public Author(int id, String email, String nickname) throws IllegalArgumentException {
        setId(id);
        setEmail(email);
        setNickname(nickname);
        this.rating = 0;
        this.createdPages = new ArrayList<>();
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String name) throws IllegalArgumentException {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Nickname cannot be null or empty");
        } else {
            nickname = name;
        }
    }

    public float getRating() {

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
