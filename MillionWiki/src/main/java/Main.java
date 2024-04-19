import com.formdev.flatlaf.FlatIntelliJLaf;
import controller.Controller;
import gui.*;
import gui.SplashScreen;
import gui.Window;
import implementationDAO.ArticleDAOImplementation;
import implementationDAO.ArticleVersionDAOImplementation;
import implementationDAO.AuthorDAOImplementation;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.net.URL;
import java.nio.file.attribute.FileAttribute;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        //ystem.outprintl("Hello MOtherfucker");
        /*Controller controller = new Controller();
        controller.getArticleByTitle("prova");*/
        //Session.getInstance();
        FlatIntelliJLaf.setup();

        UIManager.put("Button.background", UIManager.getDefaults());


        if (Controller.verifyAppIstances()) {
            Controller.notifyOtherAppIstances();
        } else {
            Controller.setSplashScreen(new SplashScreen());
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
