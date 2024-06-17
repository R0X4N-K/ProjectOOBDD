package database;

import controller.Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

public class DatabaseConnection {

    public static final String dbCredPath = Controller.getConfigFolder().concat("db_cred.toml");
    // ATTRIBUTI
    private static DatabaseConnection instance = null;
    public Connection connection = null;
    private String nome;
    private String password;
    private String url;
    private String driver;

    // COSTRUTTORE
    private DatabaseConnection() {

    }

    public static DatabaseConnection getInstance() throws SQLException, IllegalArgumentException, FileNotFoundException, ClassNotFoundException {
        if (instance == null) {
            instance = DBLogin();
        }
        return instance;
    }

    public static DatabaseConnection DBLogin() throws FileNotFoundException, IllegalArgumentException, SQLException, ClassNotFoundException {
        DatabaseConnection db = new DatabaseConnection();
        File f = new File(dbCredPath);
        if (checkCredIntegrity(f)) {
            Scanner s = new Scanner(f);
            s.nextLine();
            String temp = s.next();
            if (!temp.isBlank() && !temp.isEmpty()) {
                db.nome = temp;
                s.nextLine();
                s.nextLine();
                temp = s.next();
                if (!temp.isBlank() && !temp.isEmpty()) {
                    db.password = temp;
                    s.nextLine();
                    s.nextLine();
                    temp = s.next();
                    if (!temp.isBlank() && !temp.isEmpty()) {
                        db.url = temp;
                        s.nextLine();
                        s.nextLine();
                        temp = s.next();
                        if (!temp.isBlank() && !temp.isEmpty()) {
                            db.driver = temp;
                            db.connection = DriverManager.getConnection(db.url, db.nome, db.password);
                            //System.out.println(db);
                        } else {
                            throw new IllegalArgumentException("Driver Missing");
                        }
                    } else {
                        throw new IllegalArgumentException("URL Missing");
                    }
                } else {
                    throw new IllegalArgumentException("Password Missing");
                }

            } else {
                throw new IllegalArgumentException("Username Missing");
            }
        } else {
            throw new IllegalArgumentException("FILE CORRUPTED");
        }
        return db;
    }

    private static boolean checkCredIntegrity(File f) throws FileNotFoundException {
        boolean isValid = false;
        if (f.exists() && f.isFile()) {
            Scanner s = new Scanner(f);
            if (s.hasNextLine()) {
                if (Objects.equals(s.nextLine(), "[name]")) {
                    if (s.hasNext()) {
                        s.nextLine();
                        if (s.hasNextLine()) {
                            if (Objects.equals(s.nextLine(), "[password]")) {
                                if (s.hasNextLine()) {
                                    s.nextLine();
                                    if (s.hasNextLine()) {
                                        if (Objects.equals(s.nextLine(), "[url]")) {
                                            if (s.hasNextLine()) {
                                                s.nextLine();
                                                if (s.hasNextLine()) {
                                                    if (Objects.equals(s.nextLine(), "[driver]")) {
                                                        if (s.hasNextLine()) {
                                                            isValid = true;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        } else {
            throw new FileNotFoundException("File db_cred.toml non trovato nel percorso: " + DatabaseConnection.dbCredPath);
        }
        return isValid;
    }
}