package dao;

import model.Article;
import model.ArticleVersion;
import model.Author;

import java.util.ArrayList;
import java.util.Date;

public interface ArticleVersionDAO {
    ArrayList<ArticleVersion> getAllArticleVersions();
    public void insertArticleVersion(ArticleVersion articleVersion);
    public void insertArticleVersion(Article parentArticle, ArticleVersion.Status status, String text, Date versionDate, Date revisionDate, Author authorProposal);

}
