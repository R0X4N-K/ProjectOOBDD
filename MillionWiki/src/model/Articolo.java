package model;
import java.util.Date;


public class Articolo {

    String titolo;
    Date dataCreazione;
    boolean revisione = false;


    public Articolo(String titolo){
        this.titolo=titolo;

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

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }
}
