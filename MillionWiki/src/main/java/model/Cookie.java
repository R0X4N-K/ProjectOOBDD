package model;

import controller.Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.IllegalFormatException;
import java.util.Objects;
import java.util.Scanner;

public class Cookie {
    private int id;
    private String password;

    static private final String cookieSavePath = Controller.getConfigFolder().concat("cookie.toml");

    public Cookie(int id, String password) throws IllegalArgumentException {
        setPassword(password);
        setId(id);
    }

    public static Cookie retriveLogin () throws FileNotFoundException, IllegalArgumentException {
        Cookie c = null;
        File f = new File(cookieSavePath);
        if (validateCookieFile(f)){
            Scanner s = new Scanner(f);
            s.nextLine();
            int id = s.nextInt();
            s.nextLine();
            s.nextLine();
            String password = s.next();
            c = new Cookie(id, password);
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
                Files.createDirectories(Paths.get(Controller.getConfigFolder()));
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


    public void setPassword(String password) throws IllegalArgumentException {
        if (!password.isEmpty() && !password.isBlank()) {
            this.password = password;
        } else {
            throw new IllegalArgumentException("PASSWORD VUOTA!");
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



    private static boolean validateCookieFile(File f) throws FileNotFoundException {
        boolean isValid = false;
        if (f.exists() && f.isFile()) {
            Scanner s = null;
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
        }
        return isValid;
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
