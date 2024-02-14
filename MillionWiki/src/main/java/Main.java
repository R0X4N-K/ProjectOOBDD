import controller.Controller;
import gui.*;
import implementationDAO.ArticleDAOImplementation;
import implementationDAO.ArticleVersionDAOImplementation;
import implementationDAO.AuthorDAOImplementation;
import model.Session;

import java.sql.SQLException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        //ystem.outprintl("Hello MOtherfucker");
        /*Controller controller = new Controller();
        controller.getArticleByTitle("prova");*/
        Session.getInstance();
        Controller.setArticleDAO(new ArticleDAOImplementation());
        Controller.setArticleVersionDAO(new ArticleVersionDAOImplementation());
        Controller.setAuthorDAO(new AuthorDAOImplementation());
        new Window();
    }
}
