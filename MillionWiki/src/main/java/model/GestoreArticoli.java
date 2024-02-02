package model;

import java.util.ArrayList;

public final class GestoreArticoli {
    private static ArrayList<Articolo> articoli = new ArrayList<>(); //Aggiunto per memorizzare gli articoli
    private GestoreArticoli(){

    }
    public static Articolo fornisciArticolo(String nomeArticolo) {
        for (Articolo articolo : articoli) {
            if (articolo.getTitolo().equals(nomeArticolo)) {
                return articolo;
            }
        }
        return null;
    }


    public static VersioneArticolo fornisciVersioneArticolo(String nomeArticolo) {
        Articolo articolo = fornisciArticolo(nomeArticolo);
        if (articolo != null) {
            ArrayList<VersioneArticolo> modifiche = articolo.getProposteDiModifica();
            if (!modifiche.isEmpty()) {
                return modifiche.get(modifiche.size() - 1); // Restituisce l'ultima modifica
            }
        }
        return null;
    }

    public static ArrayList<VersioneArticolo> notificaAutore(Autore autore) {
        ArrayList<VersioneArticolo> modificheDaRivedere = new ArrayList<>();
        for (Articolo articolo : autore.getPagineCreate()) {
            for (VersioneArticolo modifica : articolo.getProposteDiModifica()) {
                if (modifica.getStato() == VersioneArticolo.Stato.ATTESA) {
                    modificheDaRivedere.add(modifica);
                }
            }
        }
        return modificheDaRivedere;
    }

    public static void memorizzaArticolo(Articolo articoloDaMemorizzare){
        articoli.add(articoloDaMemorizzare);
    }

    /*
    CONTROLLO PLAGIO  - DA IMPLEMENTARE
    public static void controllaValiditaArticolo(Articolo articoloDaValidare){

    }

    public static void controllaValiditaArticolo(VersioneArticolo versioneArticoloDaValidare){

    }
    */
}
