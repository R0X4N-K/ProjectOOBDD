package gui.page;
import controller.Controller;
import model.Article;
import model.ArticleVersion;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.HyperlinkEvent;
import javax.swing.text.*;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
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
    private int pageMode = 0; //0 ViewerMode, 1 EditorMode


    private int idArticle = -1;
    private Thread thread;


    //TODO: scorporare la classe ->
    // continuare a inserire funzionalità in PageUtils:
    //      - createColorChooserComponent
    //      - altri listeners
    //      - funzioni di ricerca

    public Page(){

        new PageUtils(
                mainPanelPage,
                pageField,
                boldButton,
                italicButton,
                textButton,
                colorPickerButton,
                titlePageField,
                searchPanel,
                searchTxtFld,
                searchErrorLbl,
                searchBtn,
                nextOccurrenceBtn,
                previousOccurrenceBtn,
                closeSearchBtn,
                editBtn,
                sendButton
        );

        //Set della modalità viewer di Default
        setViewerMode();

        setSearchOccurrenceIndex(0);
        setSearchOccurrencePositions(new ArrayList<>());

        pageField.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
        pageField.setEditorKit(new HTMLEditorKit());


        JScrollPane scrollPane = new JScrollPane(pageField);
        mainPanelPage.add(scrollPane);

        menuBar.setBackground(Color.decode("#e8e4f0"));

    }

    public void createColorChooserComponent(){
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

    public void search(){
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
                insertHTML("COLOR", colorChooser.getColor(), null, -1);

            colorChooserDialog.dispose();
        });

        //Add Buttons to buttonsPanel
        buttonsPanel.add(applyColor);
        buttonsPanel.add(retryColor);
        return buttonsPanel;
    }

    public JPanel getPanel() {
        setViewerMode();
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
                PageLinker pageLinker = new PageLinker();
            }
            else{

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
            pageField.setFont(new Font(
				    pageField.getFont().getFontName(),
				    pageField.getFont().getStyle(),
				    pageField.getFont().getSize() + 1));
            titlePageField.setFont(new Font(
				    titlePageField.getFont().getFontName(),
				    titlePageField.getFont().getStyle(),
				    titlePageField.getFont().getSize() + 1));
        });
        zoomOutBtnToolMenu.addActionListener(e -> {
            pageField.setFont(new Font(pageField.getFont().getFontName(), pageField.getFont().getStyle(), pageField.getFont().getSize() - 1));
            titlePageField.setFont(new Font(titlePageField.getFont().getFontName(), titlePageField.getFont().getStyle(), titlePageField.getFont().getSize() - 1));
        });
    }


    public void insertHTML(String tag, Color textColor, String inputText, int idArticleLink) {
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
                selectedText = "<a href=" + idArticleLink + ">" + selectedText + "</a><span> </span>";
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
    }

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

    public Point getCurrentSearchOccurrenceIndex(){
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

    public void incrementIndexOfSearch() {
        if(!getSearchOccurrencePositions().isEmpty()) {
            if (getSearchOccurrenceIndex() < getSearchOccurrencePositions().size() - 1)
                setSearchOccurrenceIndex(getSearchOccurrenceIndex() + 1);
            else
                resetIndexOfSearch();
        }
    }
    public void decrementIndexOfSearch() {
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
        setMode(false);
    }
    public void setEditorMode(){
        setMode(true);
    }

    private void setMode(boolean mode){
        createMenu.setVisible(mode);
        textButton.setVisible(mode);
        boldButton.setVisible(mode);
        colorPickerButton.setVisible(mode);
        italicButton.setVisible(mode);
        pageField.setEditable(mode);
        titlePageField.setEditable(mode);
        sendButton.setVisible(mode);

        if(mode){
            pageField.setCaretColor(Color.BLACK);
            titlePageField.setCaretColor(Color.BLACK);
            editBtn.setVisible(false);
            pageMode = 1;
        }
        else{
            pageField.setCaretColor(pageField.getBackground());
            titlePageField.setCaretColor(Color.BLACK);
            titlePageField.setCaretColor(Color.BLACK);
            editBtn.setVisible(true);
            pageMode = 0;
        }
    }

    public void openPage(String title, String text, int idArticle){
        setViewerMode();
        setTitlePageField(title);
        setTextPageField(text);
        setIdArticle(idArticle);
        if (thread == null || !thread.isAlive()) {
            thread = new Thread(() -> {
                Controller.incrementArticleViews(idArticle);
            });
        }
        thread.setDaemon(true);
        thread.start();

    }

    public void createNewPage(){
        setEditorMode();
        setIdArticle(-1);
        pageField.setText("");
        titlePageField.setText("");
    }

    public int getIdArticle() {
        return idArticle;
    }
    public void setIdArticle(int id) {
        idArticle = id;
    }

    public void setTitlePageField(String title) {
        this.titlePageField.setText(title);
    }

    public void setTextPageField(String title) {
        this.pageField.setText(title);
    }
}
