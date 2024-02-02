package model;
import java.util.ArrayList;
import java.util.Date;
import java.lang.Exception;


public class Articolo {

    String titolo;
    Date dataCreazione;
    boolean revisione = false;
    private Autore autore;
    private VersioneArticolo versioneArticoloCorrente;
    private ArrayList<VersioneArticolo> proposteDiModifica;



    public Articolo(String titolo, Autore autore) throws Exception {
        setTitolo(titolo);
        this.autore = autore;
        this.dataCreazione = new Date();
        this.proposteDiModifica = new ArrayList<>();

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
        this.proposteDiModifica.add(nuovaVersione);
    }
    public Autore getAutore() {
        return autore;
    }
    public void setAutore(Autore autore) {
        this.autore = autore;
    }
    public ArrayList<VersioneArticolo> getProposteDiModifica() {
        return proposteDiModifica;
    }


}
