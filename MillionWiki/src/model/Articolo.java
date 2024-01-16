package model;
import java.util.Date;
import java.lang.Exception;


public class Articolo {

    String titolo;
    Date dataCreazione;
    boolean revisione = false;


    public Articolo(String titolo){
        try {
            setTitolo(titolo);
        } catch (Exception e) {
            // Gestisci l'eccezione qui, ad esempio, puoi stampare un messaggio o fare altro.
            e.printStackTrace();

        }


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
}
