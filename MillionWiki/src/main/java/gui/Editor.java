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
    private JMenu createMenu;
    private JMenuBar menuBar;
    private JMenu Strumenti;
    private JScrollPane scrollPane;


    public Editor(){



        editorField.setEditable(true);




//        editorField.addHyperlinkListener(new HyperlinkListener() {
//            public void hyperlinkUpdate(HyperlinkEvent e) {
//                if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
//                    if(Objects.equals(e.getDescription(), "prova"))
//                        System.out.println("Funziona");
//
//                }
//            }
//        });

//        editorField.addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyTyped(KeyEvent e) {
//                super.keyTyped(e);
//                System.out.println(editorField.getText());
//            }
//        });



        scrollPane = new JScrollPane(editorField);
        mainPanelEditor.add(scrollPane);


        menuBar.setBackground(Color.decode("#e8e4f0"));

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
        //Menu creazione
        createMenu = new JMenu("Crea");

        //Dichiarazione dei sotto menu di newMenu
        JMenuItem linkBtnNewMenu = new JMenuItem("Link");
        JMenuItem sectionBtnNewMenu = new JMenuItem("Paragrafo");
        JMenuItem listBtnNewMenu = new JMenuItem("Lista");

        //Aggiunta dei sotto menu al newMenu
        createMenu.add(linkBtnNewMenu);
        createMenu.add(sectionBtnNewMenu);
        createMenu.add(listBtnNewMenu);

        //Menu strumenti
        toolMenu = new JMenu("Strumenti");

        //Dichiarazione dei sotto menu di toolMenu
        JMenuItem searchBtnToolMenu = new JMenuItem("Cerca");
        JMenuItem replaceBtnToolMenu =new JMenuItem("Sostituisci");
        JMenuItem zoomInBtnToolMenu = new JMenuItem("Zoom in");
        JMenuItem zoomOutBtnToolMenu = new JMenuItem("Zoom out");

        //Aggiunta dei sotto menu al toolMenu
        toolMenu.add(searchBtnToolMenu);
        toolMenu.add(replaceBtnToolMenu);
        toolMenu.add(zoomInBtnToolMenu);
        toolMenu.add(zoomOutBtnToolMenu);




        //Implementazione degli ActionListeners dei sotto menu di createMenu
        linkBtnNewMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Verifica se l'utente ha selezionato del testo
                if(editorField.getSelectionStart() == editorField.getSelectionEnd()){
                    JOptionPane.showMessageDialog(mainPanelEditor, "Seleziona prima il testo !",
                            "Errore creazione link", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    //Ottengo l'indice di selezione iniziale, finale e il testo selezionato
                    int startIndex = editorField.getSelectionStart();
                    int endIndex = editorField.getSelectionEnd();
                    String linkText = editorField.getSelectedText();

                    //richiamo funzione insertLink
                    insertLink(startIndex, endIndex, linkText);
                }



            }
        });

    }

    public void insertLink(int startIndex, int endIndex, String linkText) {
        //TODO: trovare una soluzione per l'implementazione
        // https://stackoverflow.com/questions/16444170/clickable-html-link-in-jeditorpane-using-replaceselection-method
        // https://stackoverflow.com/questions/12932089/handling-hyperlink-right-clicks-on-a-jtextpane
    }
}