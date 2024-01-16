package model;

import java.util.ArrayList;

public class Autore extends Utente {
    private String nomeUtente;
    private String password;
    private float valutazione;

    public Autore (String nomeUtente, String password) throws Exception {

        setNomeUtente(nomeUtente);

        setPassword(password);
    }

    public String getNomeUtente(){
        return nomeUtente;
    }

    public void setNomeUtente(String nome) throws Exception{
        if (!nomeUtente.isEmpty() && !password.isBlank()) {
            this.nomeUtente = nome;
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



    public Articolo scriviArticolo(String titolo) {

        Articolo nuovoArticolo = new Articolo(titolo);

        return nuovoArticolo;
    }

    public VersioneArticolo scriviArticolo(Articolo articolo, String testo) {

        VersioneArticolo nuovaVersione = new VersioneArticolo();

        return nuovaVersione;
    }

    public VersioneArticolo scriviArticolo(Articolo articolo, String testo, String titolo) {
        scriviArticolo(articolo, testo);

        VersioneArticolo nuovaVersione = new VersioneArticolo();

        return nuovaVersione;
    }



    public void visualizzaProposta(VersioneArticolo versioneArticolo) {

    }

    public void modificaStatoVersioneArticolo(VersioneArticolo versioneArticolo) {

    }
}
