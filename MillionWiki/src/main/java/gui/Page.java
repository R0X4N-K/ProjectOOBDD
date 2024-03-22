package gui;


import controller.Controller;
import model.Article;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.HyperlinkEvent;
import javax.swing.text.*;
import javax.swing.text.html.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;

import static model.ArticleVersion.Status.WAITING;


public class Page {
    private JPanel mainPanelPage;
    private JEditorPane pageField;
    private JButton italicButton;
    private JButton boldButton;
    private JButton colorPickerButton;
    private JMenu toolMenu;
    private JMenu createMenu;
    private JMenuBar menuBar;
    private JButton textButton;
    private JPanel menuBarPanel;
    private JTextField searchTxtFld;
    private JPanel searchPanel;
    private JButton closeSearchBtn;
    private JButton searchBtn;
    private JToggleButton caseSensitiveToggleButton;
    private JButton previousOccurrenceBtn;
    private JButton nextOccurrenceBtn;
    private JLabel searchErrorLbl;
    private JButton editBtn;
    private JEditorPane titlePageField;
    private JButton sendButton;
    private int searchOccurrenceIndex;
    private ArrayList<Point> searchOccurrencePositions;
    private boolean titlePageFieldFlag = false;
    private int pageMode = 0; //0 ViewerMode, 1 EditorMode

    private int idArticle = -1;


    public Page(){

        //Set della modalità viewer di Default
        setViewerMode();

        setSearchOccurrenceIndex(0);
        setSearchOccurrencePositions(new ArrayList<>());


        pageField.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
        pageField.setEditorKit(new HTMLEditorKit());



        //TEST
        boldButton.addActionListener(e -> {
            //Verifica se l'utente ha selezionato del testo
            if(pageField.getSelectionStart() == pageField.getSelectionEnd()){
                JOptionPane.showMessageDialog(mainPanelPage, "Seleziona prima il testo !",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            }
            else{
                insertHTML("BOLD", null, null);
            }
        });

        italicButton.addActionListener(e -> {
            //Verifica se l'utente ha selezionato del testo
            if(pageField.getSelectionStart() == pageField.getSelectionEnd()){
                JOptionPane.showMessageDialog(mainPanelPage, "Seleziona prima il testo !",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            }
            else{
                insertHTML("ITALIC", null, null);
            }
        });

        textButton.addActionListener(e -> {
            //Verifica se l'utente ha selezionato del testo
            if(pageField.getSelectionStart() == pageField.getSelectionEnd()){
                JOptionPane.showMessageDialog(mainPanelPage, "Seleziona prima il testo !",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            }
            else{
                insertHTML("TEXT", null, null);
            }
        });


        colorPickerButton.addActionListener(e -> {
            int selectionStart = pageField.getSelectionStart();
            int selectionEnd = pageField.getSelectionEnd();

            if(selectionStart == selectionEnd){
                JOptionPane.showMessageDialog(mainPanelPage, "Seleziona prima il testo !",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            }
            else {
                //JDialog Color Chooser
                JDialog colorChooserDialog = new JDialog();
                colorChooserDialog.setModal(true);
                colorChooserDialog.setLayout(new BorderLayout());

                //Color Chooser Component
                JColorChooser colorChooser = new JColorChooser();
                AbstractColorChooserPanel[] defaultPanels = colorChooser.getChooserPanels();
                colorChooser.removeChooserPanel( defaultPanels[4] ); // CMYK
                colorChooser.removeChooserPanel( defaultPanels[2] );  // HSL
                colorChooser.removeChooserPanel( defaultPanels[1] );  // HSV


                //Buttons
                JPanel buttonsPanel = getButtonsPanel(colorChooserDialog, colorChooser);

                //Add Components to JDialog
                colorChooserDialog.add(colorChooser, BorderLayout.CENTER);
                colorChooserDialog.add(buttonsPanel, BorderLayout.SOUTH);


                colorChooserDialog.setSize(450, 300);
                colorChooserDialog.setLocationRelativeTo(null);
                colorChooserDialog.setVisible(true);
            }
        });




        //CTRL + MOUSE WHEEL SHORTCUT
        pageField.addMouseWheelListener(e -> {
            if ((e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) != 0) {
                pageField.setFont(new Font(pageField.getFont().getFontName(), pageField.getFont().getStyle(), (int) (pageField.getFont().getSize() + (e.getPreciseWheelRotation() * -1))));
                titlePageField.setFont(new Font(titlePageField.getFont().getFontName(), titlePageField.getFont().getStyle(), (int) (titlePageField.getFont().getSize() + (e.getPreciseWheelRotation() * -1))));
            }
        });



        //CTRL + F SEARCH
        pageField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_F) {
                    if(!searchPanel.isVisible())
                        searchPanel.setVisible(true);
                    searchTxtFld.requestFocus();
                }

            }
        });
        titlePageField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_F) {
                    if(!searchPanel.isVisible())
                        searchPanel.setVisible(true);
                    searchTxtFld.requestFocus();
                }
            }
        });

        titlePageField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(!titlePageFieldFlag && pageMode != 0){
                    titlePageFieldFlag = true;
                    titlePageField.setText("");
                }
            }
        });

        titlePageField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB) {
                    e.consume();
                    pageField.requestFocus();
                }
            }
        });





        searchTxtFld.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                searchErrorLbl.setVisible(false);
            }
        });


        searchBtn.addActionListener(e -> search());

        nextOccurrenceBtn.addActionListener(e -> {
            incrementIndexOfSearch();
            pageField.requestFocus();
            pageField.select(getCurrentSearchOccurrenceIndex().x, getCurrentSearchOccurrenceIndex().y);
        });
        previousOccurrenceBtn.addActionListener(e -> {
            decrementIndexOfSearch();
            pageField.requestFocus();
            pageField.select(getCurrentSearchOccurrenceIndex().x, getCurrentSearchOccurrenceIndex().y);
        });



        closeSearchBtn.addActionListener(e -> {
            if(searchPanel.isVisible()){
                searchPanel.setVisible(false);
                searchErrorLbl.setVisible(false);
                pageField.requestFocus();
            }
        });


        editBtn.addActionListener(actionEvent -> {
            if(Controller.checkLoggedUser()){
                setEditorMode();
            }
            else{
                if((JOptionPane.showConfirmDialog(null, "Devi essere loggato, effettuare il login ?", "Non sei loggato",
                        JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE)) == 0){

                    Controller.getWindow().switchPanel(Controller.getWindow().getLoginPanel());
                }
            }
        });





        JScrollPane scrollPane = new JScrollPane(pageField);
        mainPanelPage.add(scrollPane);


        menuBar.setBackground(Color.decode("#e8e4f0"));

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            if (idArticle == -1){
                //Crea pagina
                idArticle = Controller.createArticle(titlePageField.getText() , Controller.getCookie().getId() , new Date() , false , pageField.getText());
                if (idArticle != -1){
                    // Finesta -> Operazione avvenuta con successo
                }
                else {
                    // Finestra -> Operazione Fallita
                }

            }
            else{
                //Modifica pagina
                if (Controller.createProposal(idArticle, titlePageField.getText(), WAITING, pageField.getText(), new Date(), null, Controller.getCookie().getId()) != -1){
                    // Finesta -> Operazione avvenuta con successo
                }
                else {
                    // Finestra -> Operazione Fallita
                }
            }
           }
        });
    }

    private void search(){
        String textToSearch = null;
        String text = null;
        resetIndexOfSearch();


        //Get text to search
        try {
            textToSearch = searchTxtFld.getDocument().getText(0, searchTxtFld.getDocument().getLength());
        } catch (BadLocationException ex) {
            throw new RuntimeException(ex);
        }

        //Get text
        try {
            text = pageField.getDocument().getText(0, pageField.getDocument().getLength());
        } catch (BadLocationException ex) {
            throw new RuntimeException(ex);
        }

        //Check case-sensitive
        if(!caseSensitiveToggleButton.isSelected())
        {
            textToSearch = textToSearch.toLowerCase();
            text = text.toLowerCase();
        }

        //search
        if(!textToSearch.isBlank() && !text.isBlank()){
            pageField.requestFocus();
            if (text.contains(textToSearch)) {
                searchErrorLbl.setVisible(false);
                getSearchOccurrencePositions().clear();
                resetIndexOfSearch();
                pageField.select(-1, -1);

                int index = 0;
                while ((index = text.indexOf(textToSearch, index)) != -1) {
                    getSearchOccurrencePositions().add(new Point(index, textToSearch.length() + index));
                    index += textToSearch.length();
                }

                pageField.select(getCurrentSearchOccurrenceIndex().x, getCurrentSearchOccurrenceIndex().y);

            }
            else{
                searchErrorLbl.setVisible(true);
                pageField.select(-1, -1);
            }
        }
    }

    private JPanel getButtonsPanel(JDialog colorChooserDialog, JColorChooser colorChooser) {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());
        JButton applyColor = new JButton("Applica");
        JButton retryColor = new JButton("Indietro");

        //Listeners
        retryColor.addActionListener(e1 -> colorChooserDialog.dispose());

        applyColor.addActionListener(e12 -> {
            //CHECK SELECTED TEXT TAG
            if(isLink())
                JOptionPane.showMessageDialog(mainPanelPage, "Non puoi cambiare colore ad un link!",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            else
                insertHTML("COLOR", colorChooser.getColor(), null);

            colorChooserDialog.dispose();
        });

        //Add Buttons to buttonsPanel
        buttonsPanel.add(applyColor);
        buttonsPanel.add(retryColor);
        return buttonsPanel;
    }


    public JPanel getPanel() {
        return mainPanelPage;
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
        JMenuItem zoomInBtnToolMenu = new JMenuItem("Zoom in");
        zoomInBtnToolMenu.setToolTipText("Ctrl + Mouse wheel rotation");
        JMenuItem zoomOutBtnToolMenu = new JMenuItem("Zoom out");
        zoomOutBtnToolMenu.setToolTipText("Ctrl + Mouse wheel rotation");

        //Aggiunta dei sotto menu al toolMenu
        toolMenu.add(searchBtnToolMenu);
        toolMenu.add(zoomInBtnToolMenu);
        toolMenu.add(zoomOutBtnToolMenu);




        //Implementazione degli ActionListeners dei sotto menu di createMenu
        linkBtnNewMenu.addActionListener(e -> {

            //Verifica se l'utente ha selezionato del testo
            if(pageField.getSelectionStart() == pageField.getSelectionEnd()){
                //TODO: JDialog per prendere in inupt il nome del link da inserire

                JDialog inputLinkTxtDlg =  new JDialog();
                inputLinkTxtDlg.setTitle("Seleziona una pagina");
                inputLinkTxtDlg.setModal(true);
                inputLinkTxtDlg.setLayout(new BorderLayout());

                JPanel inputLinkPnl = new JPanel();
                inputLinkPnl.setLayout(new FlowLayout());

                JTextField searchPageToLinkTxtFld = new JTextField(20);
                JButton searchPageToLinkBtn = new JButton("Cerca");


                searchPageToLinkBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //TODO: serve una funzione in Controller che prenda tutti gli articoli che hanno
                        // una corrispondenza, quindi che restituisca un ArrayList di Article
                        Article searchedArticle = Controller.getArticleByTitle(searchPageToLinkTxtFld.getText());


                        //TODO: completare visualizzazione articoli e possibilità di selezione e creazione link
                        if(searchedArticle == null){
                            System.out.println("Nessun articolo trovato con questo titolo: " + searchPageToLinkTxtFld.getText());
                        }
                        else{
                            System.out.println("Pagina trovata: " + searchPageToLinkTxtFld.getText());
                        }
                    }
                });

                inputLinkPnl.add(searchPageToLinkTxtFld);
                inputLinkPnl.add(searchPageToLinkBtn);
                inputLinkPnl.setVisible(true);

                inputLinkTxtDlg.add(inputLinkPnl, BorderLayout.CENTER);

                inputLinkTxtDlg.setSize(450, 300);
                inputLinkTxtDlg.setResizable(false);
                inputLinkTxtDlg.setLocationRelativeTo(null);
                inputLinkTxtDlg.setVisible(true);
            }
            else{
                insertHTML("LINK", null, null);
            }

        });

        searchBtnToolMenu.addActionListener(e -> {
            String textToSearch = "";
            // Get the selected text as search

            if(!searchPanel.isVisible()) {
                searchPanel.setVisible(true);
                searchTxtFld.requestFocus();
            }
            else {
                searchPanel.setVisible(false);
                pageField.requestFocus();
            }
            mainPanelPage.revalidate();

        });

        zoomInBtnToolMenu.addActionListener(e -> {
            pageField.setFont(new Font(pageField.getFont().getFontName(), pageField.getFont().getStyle(), pageField.getFont().getSize() + 1));
            titlePageField.setFont(new Font(titlePageField.getFont().getFontName(), titlePageField.getFont().getStyle(), titlePageField.getFont().getSize() + 1));
        });
        zoomOutBtnToolMenu.addActionListener(e -> {
            pageField.setFont(new Font(pageField.getFont().getFontName(), pageField.getFont().getStyle(), pageField.getFont().getSize() - 1));
            titlePageField.setFont(new Font(titlePageField.getFont().getFontName(), titlePageField.getFont().getStyle(), titlePageField.getFont().getSize() - 1));
        });
    }

    private void insertHTML(String tag, Color textColor, String inputText) {
        int selectionStart = pageField.getSelectionStart();
        int selectionEnd = pageField.getSelectionEnd();
        String selectedText = pageField.getSelectedText();

        if(selectionStart == selectionEnd && inputText != null)
            selectedText = inputText;

        HTML.Tag HTML_TAG = HTML.Tag.SPAN;

        HTMLEditorKit htmlEditorKit = (HTMLEditorKit) pageField.getEditorKit();
        HTMLDocument doc = (HTMLDocument) pageField.getDocument();

        //Get style of selected text
        MutableAttributeSet attr = htmlEditorKit.getInputAttributes();

        // if color is null, default color is Black
        if(textColor == null){
            textColor = Color.BLACK;
        }


        // Setting tag
        switch (tag) {
            case "LINK":
                //TODO: deve avere il riferimento alla pagina tramite id e non tramite al titolo
                // perchè il titolo può cambiare

                selectedText = "<a href=" + selectedText + ">" + selectedText + "</a>";
                HTML_TAG = HTML.Tag.A;
                break;
            case "BOLD":
                if (!attr.containsAttribute(StyleConstants.FontConstants.Bold, Boolean.TRUE)) {
                    attr.addAttribute(StyleConstants.Bold, Boolean.TRUE);
                }
                else{
                    attr.removeAttribute(StyleConstants.Bold);
                }
                selectedText = "<span style=\"" + getStringFromAttributeSet(attr) + "\">" + selectedText + "</span>";

                break;
            case "ITALIC":
                if (!attr.containsAttribute(StyleConstants.Italic, Boolean.TRUE)) {
                    attr.addAttribute(StyleConstants.Italic, Boolean.TRUE);
                }
                else{
                    attr.removeAttribute(StyleConstants.Italic);
                }
                selectedText = "<span style=\"" + getStringFromAttributeSet(attr) + "\">" + selectedText + "</span>";

                break;
            case "TEXT":
                if (attr.containsAttribute(StyleConstants.FontConstants.Bold, Boolean.TRUE)) {
                    attr.removeAttribute(StyleConstants.Bold);
                }
                if (attr.containsAttribute(StyleConstants.Italic, Boolean.TRUE)) {
                    attr.removeAttribute(StyleConstants.Italic);
                }
                selectedText = "<span style=\"" + getStringFromAttributeSet(attr) + "\">" + selectedText + "</span>";

                break;
            case "COLOR":
                attr.addAttribute(StyleConstants.Foreground, textColor);
                selectedText = "<span style=\"" + getStringFromAttributeSet(attr) + "\">" + selectedText + "</span>";
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

            pageField.addHyperlinkListener(e -> {
                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    pageField.setEditable(true);
                    System.out.println(e.getDescription());
                }
            });
        }
    }

    // Method to check if selected text is bold
//    private boolean isBold() {
//        StyledEditorKit styledEditorKit = (StyledEditorKit) pageField.getEditorKit();
//
//        AttributeSet attr = styledEditorKit.getInputAttributes();
//        return StyleConstants.isBold(attr);
//    }
//
//    // Method to check if selected text is italic
//    private boolean isItalic() {
//        StyledEditorKit styledEditorKit = (StyledEditorKit) pageField.getEditorKit();
//
//        AttributeSet attr = styledEditorKit.getInputAttributes();
//        return StyleConstants.isItalic(attr);
//    }


    // Method to check if selected text is a link
    private boolean isLink(){
        StyledEditorKit styledEditorKit = (StyledEditorKit) pageField.getEditorKit();

        AttributeSet attr = styledEditorKit.getInputAttributes();
        return attr.isDefined(HTML.Tag.A);

    }

    private String getStringFromAttributeSet(AttributeSet attr){
        String attributeString = "";
        if (attr != null) {
            StringBuilder stringBuilder = new StringBuilder();
            Enumeration<?> attributeNames = attr.getAttributeNames();
            while (attributeNames.hasMoreElements()) {
                Object key = attributeNames.nextElement();
                Object value = attr.getAttribute(key);
                stringBuilder.append(key).append(": ").append(value).append("; ");
            }
            attributeString = stringBuilder.toString();
        }
        return attributeString;
    }


    private Point getCurrentSearchOccurrenceIndex(){
        if(!getSearchOccurrencePositions().isEmpty())
            return getSearchOccurrencePositions().get(getSearchOccurrenceIndex());
        else
            return new Point(-1, -1);
    }


    public ArrayList<Point> getSearchOccurrencePositions() {
        return searchOccurrencePositions;
    }

    public void setSearchOccurrencePositions(ArrayList<Point> searchOccurrencePositions) {
        this.searchOccurrencePositions = searchOccurrencePositions;
    }

    private int getSearchOccurrenceIndex() {
        return searchOccurrenceIndex;
    }
    private void setSearchOccurrenceIndex(int newIndex) {
        searchOccurrenceIndex = newIndex;
    }


    private void incrementIndexOfSearch() {
        if(!getSearchOccurrencePositions().isEmpty()) {
            if (getSearchOccurrenceIndex() < getSearchOccurrencePositions().size() - 1)
                setSearchOccurrenceIndex(getSearchOccurrenceIndex() + 1);
            else
                resetIndexOfSearch();
        }
    }
    private void decrementIndexOfSearch() {
        if(!getSearchOccurrencePositions().isEmpty()) {
            if (getSearchOccurrenceIndex() != 0)
                setSearchOccurrenceIndex(getSearchOccurrenceIndex() - 1);
            else
                setSearchOccurrenceIndex(getSearchOccurrencePositions().size() - 1);
        }
    }
    private void resetIndexOfSearch(){
        setSearchOccurrenceIndex(0);
    }


    public void setViewerMode(){
        createMenu.setVisible(false);
        textButton.setVisible(false);
        boldButton.setVisible(false);
        colorPickerButton.setVisible(false);
        italicButton.setVisible(false);
        pageField.setEditable(false);
        pageField.setCaretColor(pageField.getBackground());
        titlePageField.setEditable(false);
        titlePageField.setCaretColor(titlePageField.getBackground());
        toolMenu.setVisible(true);
        editBtn.setVisible(true);

        pageMode = 0;
    }
    public void setEditorMode(){
        createMenu.setVisible(true);
        textButton.setVisible(true);
        boldButton.setVisible(true);
        colorPickerButton.setVisible(true);
        italicButton.setVisible(true);
        pageField.setEditable(true);
        pageField.setCaretColor(Color.BLACK);
        titlePageField.setEditable(true);
        titlePageField.setCaretColor(Color.BLACK);
        editBtn.setVisible(false);

        pageMode = 1;
    }

    public void setIdArticle(int id) {
        idArticle = id;
    }
}