package model;

import java.util.Date;

public class ArticleVersion {

    public enum Stato{
        ATTESA,
        ACCETTATO,
        RIFIUTATO// opzionalmente può terminare con ";"
    }

    private Article articlePrimordiale;
    private Stato stato = Stato.ATTESA;
    private int id;
    private String testo;
    private Date dataVersione;
    private Date dataRevisione = null;
    private Author authorProposta; // Aggiunto

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
    public Author getAutoreProposta() {
        return authorProposta;
    }

    public void setAutoreProposta(Author authorProposta) {
        this.authorProposta = authorProposta;
    }

}
