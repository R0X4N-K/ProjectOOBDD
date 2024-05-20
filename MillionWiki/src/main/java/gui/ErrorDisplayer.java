package gui;

import javax.swing.*;

public final class ErrorDisplayer {
    private ErrorDisplayer(){}

    public static void showError(Exception ex){
        JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        System.err.println("Si è verificato un errore: " + ex.getMessage());
    }
}
