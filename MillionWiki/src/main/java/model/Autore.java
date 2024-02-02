package model;

import java.util.ArrayList;

public class Autore extends Utente {
    private String nickname;
    private String password;
    private float valutazione;
    private ArrayList<Articolo> pagineCreate;
    public Autore (String nomeUtente, String password) throws Exception {

        setNickname(nomeUtente);

        setPassword(password);
        this.pagineCreate = new ArrayList<>();
    }

    public String getNickname(){
        return nickname;
    }

    public void setNickname(String nome) throws Exception{
        if (!nickname.isEmpty() && !password.isBlank()) {
            this.nickname = nome;
        } else {
            throw new Exception("NOME UTENTE VUOTO!"); // TODO: creare eccezione ad hoc
        }
    }



    public void setPassword(String password) throws Exception{
        if (!password.isEmpty() && !password.isBlank()) {
            this.password = password;
        } else {
            throw new Exception("PASSWORD VUOTA!"); // TODO: creare eccezione ad hoc
        }
    }



    public float getValutazione(){

        return valutazione;
    }

    public void setValutazione (float valutazione) {
        this.valutazione = valutazione;
    }


    public ArrayList<Articolo> getPagineCreate() {
        return pagineCreate;
    }
    public void addPaginaCreata(Articolo articolo) {
        this.pagineCreate.add(articolo);
    }

    public Articolo scriviArticolo(String titolo) {
        Articolo nuovoArticolo = null;
        try {
            nuovoArticolo = new Articolo(titolo, this);
            addPaginaCreata(nuovoArticolo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nuovoArticolo;
    }


    public VersioneArticolo scriviArticolo(Articolo articolo, String testo) {
        VersioneArticolo nuovaVersione = new VersioneArticolo();
        nuovaVersione.setAutoreProposta(this);
        articolo.addModifica(nuovaVersione);
        return nuovaVersione;
    }

    public VersioneArticolo scriviArticolo(Articolo articolo, String testo, String titolo) {
        VersioneArticolo nuovaVersione = scriviArticolo(articolo, testo);
        nuovaVersione.setVersioneTitolo(titolo);
        return nuovaVersione;
    }



    public void visualizzaProposta(VersioneArticolo versioneArticolo) {

    }

    public void modificaStatoVersioneArticolo(VersioneArticolo versioneArticolo) {

    }




}
