package model;
import java.util.Date;
import java.util.String;

public class Articolo {

    String titolo;
    Date dataCreazione;
    boolean revisione = false;




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

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }
}
