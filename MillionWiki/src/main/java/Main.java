import com.formdev.flatlaf.FlatLightLaf;
import controller.Controller;
import gui.ErrorDisplayer;
import gui.SplashScreen;
import gui.Window;
import implementationDAO.ArticleDAOImplementation;
import implementationDAO.ArticleVersionDAOImplementation;
import implementationDAO.AuthorDAOImplementation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
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
            Runtime.getRuntime().addShutdownHook(new Thread(Controller::deleteLockFile, "Shutdown-thread"));
            Controller.setSplashScreen(new SplashScreen());
            init();
        }
     //Controller controller = Controller.ge;
     //ArticleDAOImplementation articleDAOImplementation = new ArticleDAOImplementation();
     //articleDAOImplementation.getAllArticles();


    }

    private static void init() {
        try {
            Controller.setArticleDAO(new ArticleDAOImplementation());
            Controller.setArticleVersionDAO(new ArticleVersionDAOImplementation());
            Controller.setAuthorDAO(new AuthorDAOImplementation());
            Controller.checkIfRememberedLogin();
            Controller.setWindow(new Window());
        } catch (SQLException | FileNotFoundException | ClassNotFoundException e) {
            ErrorDisplayer.showErrorWithActions(e, null, null, e1 -> init(), e2 -> Runtime.getRuntime().exit(2), "Riprova", "Chiudi", Controller.getSplashScreen());
        }
    }
}
