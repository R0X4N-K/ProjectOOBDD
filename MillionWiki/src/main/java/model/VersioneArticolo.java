package model;

import java.util.Date;

public class VersioneArticolo {

    public enum Stato{
        ATTESA,
        ACCETTATO,
        RIFIUTATO// opzionalmente può terminare con ";"
    }

    private Articolo articoloPrimordiale;
    private Stato stato = Stato.ATTESA;
    private int id;
    private String testo;
    private Date dataVersione;
    private Date dataRevisione = null;
    private Autore autoreProposta; // Aggiunto

    public int getId() {
        return id;
    }

    public Stato getStato(){
        return stato;
    }

    public void setStato(Stato stato) {
        this.stato = stato;
    }


    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }



    public Date getDataVersione() {
        return dataVersione;
    }

    public void setDataVersione(Date dataVersione) {
        this.dataVersione = dataVersione;
    }



    public Date getDataRevisione() {
        return dataRevisione;
    }

    public void setDataRevisione(Date dataRevisione) {
        this.dataRevisione = dataRevisione;

    }
    public Autore getAutoreProposta() {
        return autoreProposta;
    }

    public void setAutoreProposta(Autore autoreProposta) {
        this.autoreProposta = autoreProposta;
    }

}
