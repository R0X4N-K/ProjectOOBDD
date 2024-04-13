package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // ATTRIBUTI
    private static DatabaseConnection instance;
    public Connection connection = null;
    private final String nome = "postgres";
    private final String password = "j9b!3fsq9k#"; // TODO:  CAMBIARE PASSWORD!!!!!!
    private final String url = "jdbc:postgresql://wikidb.cix9wyrt2gqf.eu-south-1.rds.amazonaws.com:5432/wikidb";
    //private final String url = "jdbc:postgresql://127.0.0.1:5432/wikidb"; // LOCAL
    private final String driver = "org.postgresql.Driver";

    // COSTRUTTORE
    private DatabaseConnection() throws SQLException {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, nome, password);
        } catch (ClassNotFoundException ex) {
            System.out.println("Database Connection Creation Failed : " + ex.getMessage()); //TODO: trasformare in log
            ex.printStackTrace();
        }

    }


    public static DatabaseConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseConnection();
        } else if (instance.connection.isClosed()) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
}