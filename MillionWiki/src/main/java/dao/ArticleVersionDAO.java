package dao;

import model.ArticleVersion;
import model.Author;

import java.util.ArrayList;
import java.util.Date;

public interface ArticleVersionDAO {
    ArticleVersion getLastArticleVersionByArticleId(int idArticle);
    ArrayList<ArticleVersion> getAllArticleVersions();
    ArrayList<ArticleVersion> getAllArticleVersionsWaiting(int authorId);
    ArrayList<ArticleVersion> getAllArticleVersionsWaitingFull(int articleId);
    void reviewArticles(ArrayList<ArticleVersion> a);
    ArrayList<ArticleVersion> getAllArticleVersionByArticleId(int idArticle);
    ArrayList<ArticleVersion> getAllArticleVersionExcludingTextByArticleId(int idArticle);
    ArrayList<ArticleVersion> getAllArticleVersionByAuthorId(int authorId);
    ArrayList<ArticleVersion> getAllArticleVersionExcludingTextByAuthorId(int authorId);
    int getVersionArticlesNumberSent(int idAuthor);
    ArticleVersion getArticleVersionByIdArticleVersion (int idArticleVersion);
    int insertArticleVersion(ArticleVersion articleVersion);
    int insertArticleVersion(int idArticle, String text, int idAuthor, String titleProposal);
}
