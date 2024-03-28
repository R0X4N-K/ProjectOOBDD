package dao;

import model.ArticleVersion;
import model.Author;

import java.util.ArrayList;
import java.util.Date;

public interface ArticleVersionDAO {
    ArticleVersion getLastArticleVersionByArticleId(int idArticle);
    ArrayList<ArticleVersion> getAllArticleVersions();
    int insertArticleVersion(ArticleVersion articleVersion);
    int insertArticleVersion(int idArticle, ArticleVersion.Status status, String text, Date versionDate, Date revisionDate, int idAuthor);
    int insertArticleVersion(String title, ArticleVersion.Status status, String text, Date versionDate, Date revisionDate, Author authorProposal);
    void saveArticleVersion(int title, ArticleVersion.Status status, String text, Date versionDate, Date revisionDate, int authorProposal);
}
