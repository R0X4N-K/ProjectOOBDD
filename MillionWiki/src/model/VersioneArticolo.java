package model;

import java.util.Date;

public class VersioneArticolo {
    public enum Stato{
        ATTESA,
        ACCETTATO,
        RIFIUTATO// opzionalmente può terminare con ";"
    }

    private Stato stato = Stato.ATTESA;
    private String versioneTitolo;
    private String testo;
    private Date dataVersione;
    private Date dataRevisione = null;



    public Stato getStato(){
        return stato;
    }

    public void setStato(Stato stato) {
        this.stato = stato;
    }



    public String getVersioneTitolo() {
        return versioneTitolo;
    }

    public void setVersioneTitolo(String versioneTitolo) {
        this.versioneTitolo = versioneTitolo;
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
}
