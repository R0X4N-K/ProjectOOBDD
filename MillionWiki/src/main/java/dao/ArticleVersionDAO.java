package dao;

import model.ArticleVersion;
import model.Author;

import java.util.ArrayList;
import java.util.Date;

public interface ArticleVersionDAO {
    ArticleVersion getLastArticleVersionByArticleId(int idArticle);
    ArrayList<ArticleVersion> getAllArticleVersions();
    public ArrayList<ArticleVersion> getAllArticleVersionByArticleId(int idArticle);
    public ArrayList<ArticleVersion> getAllArticleVersionByAuthorId(int authorId);
    int getVersionArticlesNumberSent(int idAuthor);
    ArticleVersion getArticleVersionByIdArticleVersion (int idArticleVersion);
    int insertArticleVersion(ArticleVersion articleVersion);
    int insertArticleVersion(int idArticle, ArticleVersion.Status status, String text, Date versionDate, Date revisionDate, int idAuthor, String titleProposal);
    int insertArticleVersion(String title, ArticleVersion.Status status, String text, Date versionDate, Date revisionDate, Author authorProposal, String titleProposal);
    void saveArticleVersion(int title, ArticleVersion.Status status, String text, Date versionDate, Date revisionDate, int authorProposal, String titleProposal);
}
