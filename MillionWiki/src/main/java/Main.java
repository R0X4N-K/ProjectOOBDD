import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.ui.FlatButtonUI;
import controller.Controller;
import gui.*;
import implementationDAO.ArticleDAOImplementation;
import implementationDAO.ArticleVersionDAOImplementation;
import implementationDAO.AuthorDAOImplementation;

import javax.swing.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        //ystem.outprintl("Hello MOtherfucker");
        /*Controller controller = new Controller();
        controller.getArticleByTitle("prova");*/
        //Session.getInstance();
        FlatIntelliJLaf.setup();

        if (Controller.verifyAppIstances()) {
            Controller.notifyOtherAppIstances();
        } else {
            Controller.setArticleDAO(new ArticleDAOImplementation());
            Controller.setArticleVersionDAO(new ArticleVersionDAOImplementation());
            Controller.setAuthorDAO(new AuthorDAOImplementation());
            Controller.checkIfRememberedLogin();
            Controller.setWindow(new Window());
        }
     //Controller controller = Controller.ge;
     //ArticleDAOImplementation articleDAOImplementation = new ArticleDAOImplementation();
     //articleDAOImplementation.getAllArticles();


    }
}
