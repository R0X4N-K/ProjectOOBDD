package model;

import java.io.File;
import java.sql.ResultSet;

public class Cookie {
    int id;
    String password;

    static String cookieSavePath = "MillionWiki/cookie.toml";

    public Cookie(int id, String password) throws Exception {
        setPassword(password);
        setId(id);
    }

    public Cookie(ResultSet rs) throws Exception {
        setPassword(rs.getString("password"));
        setId(rs.getInt("id"));
    }

    public static Cookie retriveLogin () throws Exception { // TODO: Inserire effettiva lettura del file
        Cookie c = null;
        File f = new File(System.getProperty("user.dir").concat(cookieSavePath));
        if(f.exists() && !f.isDirectory()) {
            if (true){//(LetturaFile){
                int id = 0;//file.readInt();
                String password = "";//file.readString();
                c = new Cookie(id, password);
            }
        }
        return c;
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
}
