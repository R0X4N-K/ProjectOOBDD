package model;

import controller.Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.util.IllegalFormatException;
import java.util.Objects;
import java.util.Scanner;

public class Cookie {
    private int id;
    private String password;

    static private final String configFolder = System.getProperty("user.home").concat(FileSystems.getDefault().getSeparator()).concat(".MillionWiki").concat(FileSystems.getDefault().getSeparator()); // TODO: Spostare in altra classe
    static private final String cookieSavePath = configFolder.concat("cookie.toml");

    public Cookie(int id, String password) throws Exception {
        setPassword(password);
        setId(id);
    }

    public Cookie(ResultSet rs) throws Exception {
        setPassword(rs.getString("password"));
        setId(rs.getInt("id"));
    }

    public static Cookie retriveLogin () throws Exception {
        Cookie c = null;
        File f = new File(cookieSavePath);
        if (validateFile(f)){
            Scanner s = null;
            try {
                s = new Scanner(f);
                s.nextLine();
                int id = s.nextInt();
                s.nextLine();
                s.nextLine();
                String password = s.next();
                c = new Cookie(id, password);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return c;
    }

    public void writeCookie () throws NullPointerException, IllegalFormatException {
        if (id < 0)
            throw new NullPointerException("invalid id"); // TODO: Cambiare Exception
        if (password == null)
            throw new NullPointerException("null password");
        // TODO: else if (controllo Hash) throw new IllegalFormatException

        else {
            File f = new File(cookieSavePath);
            try {
                Files.createDirectories(Paths.get(configFolder));
                f.createNewFile();

                try (FileWriter writer = new FileWriter(cookieSavePath)) {
                    writer.write("[id]\n".concat(Integer.toString(id)).concat("\n[password_hash]\n").concat(password));
                    writer.close();
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void setPassword(String password) throws Exception{
        if (!password.isEmpty() && !password.isBlank()) {
            this.password = password;
        } else {
            throw new Exception("PASSWORD VUOTA!"); // TODO: creare eccezione ad hoc
        }
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }



    private static boolean validateFile(File f) { //FUNZIONA
        boolean isValid = false;
        if (f.exists() && f.isFile()) {
            Scanner s = null;
            try {
                s = new Scanner(f);
                if (s.hasNextLine()) {
                    if (Objects.equals(s.nextLine(), "[id]")) {
                        if (s.hasNextInt()) {
                            s.nextLine();
                            if (s.hasNextLine()) {
                                if(Objects.equals(s.nextLine(), "[password_hash]")) {
                                    if(s.hasNextLine()) {
                                        //TODO: if (controllo hash)
                                        isValid = true;
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return isValid;
    }

    public static String getConfigFolder() {
        return configFolder;
    }

    public static void deleteCookie() {
        Controller.setCookie(null);
        Path cookiePath = Paths.get(cookieSavePath);
        if (Files.exists(cookiePath)) {
            try {
                Files.delete(cookiePath);
            } catch (IOException e) {
                System.err.println("Errore durante l'eliminazione del file " + cookieSavePath);
                e.printStackTrace();
            }
        }
    }
}
