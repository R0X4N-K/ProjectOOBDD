package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // ATTRIBUTI
    private static DatabaseConnection instance;
    public Connection connection = null;
    private String nome = "postgres";
    private String password = "j9b!3fsq9k#"; // TODO:  CAMBIARE PASSWORD!!!!!!
    private String url = "jdbc:postgresql://wikidb.cix9wyrt2gqf.eu-south-1.rds.amazonaws.com:5432/wikidb";
    private String driver = "org.postgresql.Driver";

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