package gui;


import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.*;
import javax.swing.text.html.*;
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
        //TODO: Dividere l'Editor in modalità lettura e scrittura

        //I keep the initial editorKit, so I can get the color selection to work correctly when the text is unformatted
        //look at L246
        editorField.setEditorKit(new HTMLEditorKit());

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
                    insertHTML("BOLD", null);
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
                    insertHTML("ITALIC", null);
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
                    insertHTML("TEXT", null);
                }
            }
        });


        colorPickerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectionStart = editorField.getSelectionStart();
                int selectionEnd = editorField.getSelectionEnd();

                if(selectionStart == selectionEnd){
                    JOptionPane.showMessageDialog(mainPanelEditor, "Seleziona prima il testo !",
                            "Errore", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    //JDialog Color Chooser
                    JDialog colorChooserDialog = new JDialog();
                    colorChooserDialog.setModal(true);
                    colorChooserDialog.setLayout(new BorderLayout());

                    //Color Chooser Component
                    JColorChooser colorChooser = new JColorChooser();
                    AbstractColorChooserPanel defaultPanels[] = colorChooser.getChooserPanels();
                    colorChooser.removeChooserPanel( defaultPanels[4] ); // CMYK
                    colorChooser.removeChooserPanel( defaultPanels[2] );  // HSL
                    colorChooser.removeChooserPanel( defaultPanels[1] );  // HSV


                    //Buttons
                    JPanel buttonsPanel = new JPanel();
                    buttonsPanel.setLayout(new FlowLayout());
                    JButton applyColor = new JButton("Applica");
                    JButton retryColor = new JButton("Indietro");

                    //Listeners
                    retryColor.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            colorChooserDialog.dispose();
                        }
                    });

                    applyColor.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //CHECK SELECTED TEXT TAG
                            if(isBold())
                                insertHTML("BOLD", colorChooser.getColor());
                            else if(isItalic())
                                insertHTML("ITALIC", colorChooser.getColor());
                            else if(isLink())
                                JOptionPane.showMessageDialog(mainPanelEditor, "Non puoi cambiare colore ad un link!",
                                        "Errore", JOptionPane.ERROR_MESSAGE);
                            else
                                insertHTML("TEXT", colorChooser.getColor());

                            colorChooserDialog.dispose();
                        }
                    });

                    //Add Buttons to buttonsPanel
                    buttonsPanel.add(applyColor);
                    buttonsPanel.add(retryColor);


                    //Add Components to JDialog
                    colorChooserDialog.add(colorChooser, BorderLayout.CENTER);
                    colorChooserDialog.add(buttonsPanel, BorderLayout.SOUTH);


                    colorChooserDialog.setSize(450, 300);
                    colorChooserDialog.setLocationRelativeTo(null);
                    colorChooserDialog.setVisible(true);
                }
            }
        });




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
                    insertHTML("LINK", null);
                }

            }
        });
    }

    public void insertHTML(String tag, Color textColor) {
        int selectionStart = editorField.getSelectionStart();
        int selectionEnd = editorField.getSelectionEnd();
        String selectedText = editorField.getSelectedText();
        HTML.Tag HTML_TAG = null;

        HTMLEditorKit htmlEditorKit = (HTMLEditorKit) editorField.getEditorKit();
        HTMLDocument doc = (HTMLDocument) editorField.getDocument();


        // if color is null, default color is Black
        if(textColor == null){
            textColor = Color.BLACK;
        }

        //java.awt.Color to String
        String colorString = String.format("#%02x%02x%02x", textColor.getRed(), textColor.getGreen(), textColor.getBlue());


        // Setting tag
        switch (tag) {
            case "LINK":
                selectedText = "<a href=" + selectedText + ">" + selectedText + "</a>";
                HTML_TAG = HTML.Tag.A;
                break;
            case "BOLD":
                selectedText = "<b style=\"color:" + colorString + ";\">" + selectedText + "</b>";
                HTML_TAG = HTML.Tag.B;
                break;
            case "ITALIC":
                selectedText = "<i style=\"color:" + colorString + ";\">" + selectedText + "</i>";
                HTML_TAG = HTML.Tag.I;
                break;
            case "TEXT":
                selectedText = "<span style=\"color:" + colorString + ";\">" + selectedText + "</span>";
                HTML_TAG = HTML.Tag.SPAN;
                break;
        }


        try {
            boolean flag = false;
            if(selectionStart == 1) {
                selectionStart++;
                flag = true;
            }

            doc.remove(selectionStart, selectionEnd - selectionStart);

            htmlEditorKit.insertHTML(doc, selectionStart, selectedText, 0, 0, HTML_TAG);


            if(flag)
                doc.remove(1, 1);

        } catch (BadLocationException | IOException e) {
            e.printStackTrace();
        }


        // HyperlinkListener creation
        if(tag.equals("LINK")){
            editorField.setEditable(false);
            editorField.addHyperlinkListener(new HyperlinkListener() {
                public void hyperlinkUpdate(HyperlinkEvent e) {
                    if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                        editorField.setEditable(true);
                        System.out.println(e.getDescription());
                    }
                }
            });
        }
    }

    // Method to check if selected text is bold
    private boolean isBold() {
        StyledEditorKit styledEditorKit = (StyledEditorKit) editorField.getEditorKit();

        AttributeSet attr = styledEditorKit.getInputAttributes();
        return StyleConstants.isBold(attr);
    }

    // Method to check if selected text is italic
    private boolean isItalic() {
        StyledEditorKit styledEditorKit = (StyledEditorKit) editorField.getEditorKit();

        AttributeSet attr = styledEditorKit.getInputAttributes();
        return StyleConstants.isItalic(attr);
    }


    // Method to check if selected text is a link
    private boolean isLink(){
        StyledEditorKit styledEditorKit = (StyledEditorKit) editorField.getEditorKit();

        AttributeSet attr = styledEditorKit.getInputAttributes();
        return attr.isDefined(HTML.Tag.A);

    }

}