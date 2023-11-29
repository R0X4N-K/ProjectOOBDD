package model;

import java.util.ArrayList;

public class Autore extends Utente {
    private String nomeUtente;
    private String password;
    private float valutazione;


    public String getNomeUtente(){
        return nomeUtente;
    }
    public void setNomeUtente(String nome){

        this.nomeUtente = nome;
    }



    public void setPassword(String password){

        this.password= password;
    }



    public float getValutazione(){

        return valutazione;
    }
    public void setValutazione(float valutazione) {
        this.valutazione = valutazione;
    }

    public Articolo scriviArticolo();
    public Articolo scriviArticolo(VersioneArticolo versioneArticolo);

    public void visualizzaProposta(VersioneArticolo versioneArticolo);

    public void modificaStatoVersioneArticolo(VersioneArticolo versioneArticolo);





}
