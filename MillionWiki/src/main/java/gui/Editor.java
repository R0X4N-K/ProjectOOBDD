package gui;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

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

        editorField.setText("<p><a href=prova>Prova</a><h5> </h5></p>");
        editorField.setEditable(false);


        editorField.addHyperlinkListener(new HyperlinkListener() {
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    if(Objects.equals(e.getDescription(), "prova"))
                        System.out.println("Funziona");

                }
            }
        });

        editorField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                System.out.println(editorField.getText());
            }
        });



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
}