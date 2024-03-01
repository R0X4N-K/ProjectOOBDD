package gui;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.EditorKit;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class Editor {
    private JPanel mainPanelEditor;
    private JEditorPane editorField;
    private JButton italicButton;
    private JButton boldButton;
    private JButton colorPickerButton;
    private JMenu toolMenu;
    private JMenu createMenu;
    private JMenuBar menuBar;
    private JButton textButton;
    private JMenu Strumenti;
    private JScrollPane scrollPane;


    public Editor(){

        editorField.setEditable(true);


        boldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Verifica se l'utente ha selezionato del testo
                if(editorField.getSelectionStart() == editorField.getSelectionEnd()){
                    JOptionPane.showMessageDialog(mainPanelEditor, "Seleziona prima il testo !",
                            "Errore", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    insertHTML("BOLD");
                }
            }
        });

        italicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Verifica se l'utente ha selezionato del testo
                if(editorField.getSelectionStart() == editorField.getSelectionEnd()){
                    JOptionPane.showMessageDialog(mainPanelEditor, "Seleziona prima il testo !",
                            "Errore", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    insertHTML("ITALIC");
                }
            }
        });

        textButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Verifica se l'utente ha selezionato del testo
                if(editorField.getSelectionStart() == editorField.getSelectionEnd()){
                    JOptionPane.showMessageDialog(mainPanelEditor, "Seleziona prima il testo !",
                            "Errore", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    insertHTML("TEXT");
                }
            }
        });


//        editorField.addHyperlinkListener(new HyperlinkListener() {
//            public void hyperlinkUpdate(HyperlinkEvent e) {
//                if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
//                    if(Objects.equals(e.getDescription(), "prova"))
//                        System.out.println("Funziona");
//
//                }
//            }
//        });

        editorField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                //System.out.println(editorField.getText());
            }
        });



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
                            "Errore", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    //insertHTML("LINK");
                }

            }
        });

    }

    public void insertHTML(String tag) {
        //TODO: trovare una soluzione per l'implementazione

        int selectionStart = editorField.getSelectionStart();
        int selectionEnd = editorField.getSelectionEnd();

        String selectedText = editorField.getSelectedText();
        HTML.Tag HTML_TAG = null;

        // Imposta tag
        switch (tag) {
            case "LINK":
                break;
            case "BOLD":
                selectedText = "<b>" + selectedText + "</b>";
                HTML_TAG = HTML.Tag.B;
                break;
            case "ITALIC":
                selectedText = "<i>" + selectedText + "</i>";
                HTML_TAG = HTML.Tag.I;
                break;
            case "TEXT":
                break;
        }

        HTMLEditorKit htmlEditorKit = (HTMLEditorKit) editorField.getEditorKit();
        HTMLDocument doc = (HTMLDocument) editorField.getDocument();


        try {
            // doc.insertAfterStart(startElem, "<b>WTF</b>");
            doc.remove(selectionStart, selectionEnd-selectionStart);
            htmlEditorKit.insertHTML((HTMLDocument) doc, selectionStart, selectedText, 0, 0, HTML_TAG);
        } catch (BadLocationException | IOException e1) {
            e1.printStackTrace();
        }

        System.out.println(editorField.getText());


    }
}