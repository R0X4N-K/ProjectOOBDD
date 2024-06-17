import com.formdev.flatlaf.FlatLightLaf;
import controller.Controller;
import gui.ErrorDisplayer;
import gui.SplashScreen;
import gui.Window;
import implementationDAO.ArticleDAOImplementation;
import implementationDAO.ArticleVersionDAOImplementation;
import implementationDAO.AuthorDAOImplementation;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.sql.SQLException;


public class Main {
    public static void main(String[] args) {
        FlatLightLaf.setup();

        UIManager.put("Button.background", UIManager.getDefaults());

        if (Controller.verifyAppInstances()) {
            Controller.notifyOtherAppInstances();
        } else {
            Runtime.getRuntime().addShutdownHook(new Thread(Controller::deleteLockFile, "Shutdown-thread"));
            Controller.setSplashScreen(new SplashScreen());
            init();
        }
    }

    private static void init() {
        try {
            Controller.setArticleDAO(new ArticleDAOImplementation());
            Controller.setArticleVersionDAO(new ArticleVersionDAOImplementation());
            Controller.setAuthorDAO(new AuthorDAOImplementation());
            Controller.checkIfRememberedLogin();
            Controller.setWindow(new Window());
        } catch (SQLException | FileNotFoundException | ClassNotFoundException e) {
            String message = null;
            if (e.getClass().getCanonicalName().equals("java.io.FileNotFoundException"))
                message = " Senza questo file, non ci si può connettere ad alcun server MillionWiki. \n";
            ErrorDisplayer.showErrorWithActions(e, null, message, e1 -> init(), e2 -> Runtime.getRuntime().exit(2), "Riprova", "Chiudi", Controller.getSplashScreen());
        }
    }
}
