package gui;

import javax.swing.*;
import java.awt.*;

public class Editor {
    private JPanel mainPanelEditor;
    private JEditorPane editorField;
    private JButton italicButton;
    private JButton boldButton;
    private JButton colorPickerButton;
    private JMenu toolMenu;
    private JComboBox<String> fontFamilyCbox;
    private JSpinner fontSizeSpinner;
    private JMenu newMenu;
    private JMenuBar menuBar;
    private JMenu Strumenti;
    private JScrollPane scrollPane;


    public Editor(){

        scrollPane = new JScrollPane(editorField);
        mainPanelEditor.add(scrollPane);


        menuBar.setBackground(Color.decode("#e8e4f0"));

        //La funzione permette di visualizzare la lista completa di tutti i Font supportati
        //Scorro l'array di stringhe e inserisco all'interno di fontFamilyCbox
        for (String fontFamily: GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()){
            fontFamilyCbox.addItem(fontFamily);
        }

        //Modifiche estetiche
        fontFamilyCbox.setBackground(Color.WHITE);
        boldButton.setBackground(Color.WHITE);
        italicButton.setBackground(Color.WHITE);
        colorPickerButton.setBackground(Color.WHITE);




    }


    public JPanel getPanel() {
        /*editorField.addCaretListener(
                new CaretListener() {
                    @Override
                    public void caretUpdate(CaretEvent e) {
                        System.out.println(editorField.getText());
                    }
                }
        );*/
        return mainPanelEditor;
    }

    private void createUIComponents() {
        toolMenu = new JMenu("Strumenti");
        toolMenu.add(new JMenuItem("Cerca"));
        toolMenu.add(new JMenuItem("Sostituisci"));
        toolMenu.add(new JMenuItem("Zoom in"));
        toolMenu.add(new JMenuItem("Zoom out"));

        //Nuovo menu per la creazione di vari oggetti
        newMenu = new JMenu("Nuovo");
        newMenu.add(new JMenuItem("Link"));
        newMenu.add(new JMenuItem("Paragrafo"));
        newMenu.add(new JMenuItem("Lista"));

    }

    //TODO: aggiungere custom create per il menu a tendina
}