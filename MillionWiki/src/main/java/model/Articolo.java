package model;
import java.util.ArrayList;
import java.util.Date;
import java.lang.Exception;


public class Articolo {

    String titolo;
    Date dataCreazione;
    boolean revisione = false;
    private Autore autore;
    private ArrayList<VersioneArticolo> modifiche;



    public Articolo(String titolo, Autore autore) throws Exception {
        setTitolo(titolo);
        this.autore = autore;
        this.dataCreazione = new Date();
        this.modifiche = new ArrayList<>();

        // TODO: dataCreazione deve riferirsi a orario Server
    }



    public boolean isRevisione() {
        return revisione;
    }

    public void setRevisione(boolean revisione) {
        this.revisione = revisione;
    }



    public Date getDataCreazione() {
        return dataCreazione;
    }

    public void setDataCreazione(Date dataCreazione) {
        this.dataCreazione = dataCreazione;
    }



    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) throws Exception {

        if(!titolo.isEmpty() && !titolo.isBlank()) {
            this.titolo = titolo;
        }
        else {
            throw new Exception("Titolo Vuoto"); // TODO: Creare eccezione ad hoc
        }
    }

    public void addModifica(VersioneArticolo nuovaVersione) {
        this.modifiche.add(nuovaVersione);
    }
    public Autore getAutore() {
        return autore;
    }
    public void setAutore(Autore autore) {
        this.autore = autore;
    }
    public ArrayList<VersioneArticolo> getModifiche() {
        return modifiche;
    }


}
