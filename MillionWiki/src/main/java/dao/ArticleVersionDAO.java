package dao;

import model.ArticleVersion;
import model.Author;

import java.util.ArrayList;
import java.util.Date;

public interface ArticleVersionDAO {
    ArrayList<ArticleVersion> getAllArticleVersions();
    public int insertArticleVersion(ArticleVersion articleVersion);
    public int insertArticleVersion(String title, ArticleVersion.Status status, String text, Date versionDate, Date revisionDate, Author authorProposal);

}
