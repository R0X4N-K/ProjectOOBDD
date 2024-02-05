import controller.Controller;
import gui.*;
import implementationDAO.ArticleDAOImplementation;

import java.sql.SQLException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        //ystem.outprintl("Hello MOtherfucker");
        Controller controller = new Controller();
        controller.getArticleByTitle("prova");
        new Window();

    }
}
