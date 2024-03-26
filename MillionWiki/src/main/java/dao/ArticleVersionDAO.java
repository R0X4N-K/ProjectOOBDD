package dao;

import model.ArticleVersion;
import model.Author;

import java.util.ArrayList;
import java.util.Date;

public interface ArticleVersionDAO {
    public ArticleVersion getLastArticleVersionByArticleId(int idArticle);
    ArrayList<ArticleVersion> getAllArticleVersions();
    public int insertArticleVersion(ArticleVersion articleVersion);
    public int insertArticleVersion(int idArticle, ArticleVersion.Status status, String text, Date versionDate, Date revisionDate, int idAuthor);
    public int insertArticleVersion(String title, ArticleVersion.Status status, String text, Date versionDate, Date revisionDate, Author authorProposal);
    public void saveArticleVersion(int title, ArticleVersion.Status status, String text, Date versionDate, Date revisionDate, int authorProposal);
}
