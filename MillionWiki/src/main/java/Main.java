import com.formdev.flatlaf.FlatLightLaf;
import controller.Controller;
import gui.SplashScreen;
import gui.Window;
import implementationDAO.ArticleDAOImplementation;
import implementationDAO.ArticleVersionDAOImplementation;
import implementationDAO.AuthorDAOImplementation;

import javax.swing.*;
import java.sql.SQLException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        //ystem.outprintl("Hello MOtherfucker");
        /*Controller controller = new Controller();
        controller.getArticleByTitle("prova");*/
        //Session.getInstance();
        FlatLightLaf.setup();

        UIManager.put("Button.background", UIManager.getDefaults());

        if (Controller.verifyAppInstances()) {
            Controller.notifyOtherAppInstances();
        } else {
            Controller.setSplashScreen(new SplashScreen());
            try {
                Controller.setArticleDAO(new ArticleDAOImplementation());
                Controller.setArticleVersionDAO(new ArticleVersionDAOImplementation());
                Controller.setAuthorDAO(new AuthorDAOImplementation());
            } catch (SQLException e) {
                // TODO: showError da errorDisplayer
            }
            Controller.checkIfRememberedLogin();
            Controller.setWindow(new Window());
        }
     //Controller controller = Controller.ge;
     //ArticleDAOImplementation articleDAOImplementation = new ArticleDAOImplementation();
     //articleDAOImplementation.getAllArticles();


    }
}
