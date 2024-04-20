package gui.page;

import controller.Controller;
import model.Article;
import model.ArticleVersion;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.awt.event.*;

public class PageUtils {
    JPanel mainPanelPage;
    JEditorPane pageField;
    JButton boldButton;
    JButton italicButton;
    JButton textButton;
    JButton colorPickerButton;
    JEditorPane titlePageField;
    JPanel searchPanel;
    JTextField searchTxtFld;
    JLabel searchErrorLbl;
    JButton searchBtn;
    JButton nextOccurrenceBtn;
    JButton previousOccurrenceBtn;
    JButton closeSearchBtn;
    JButton editBtn;
    JButton sendButton;
    JButton infoPageBtn;

    public PageUtils(JPanel mainPanelPage,
                     JEditorPane pageField,
                     JButton boldButton,
                     JButton italicButton,
                     JButton textButton,
                     JButton colorPickerButton,
                     JEditorPane titlePageField,
                     JPanel searchPanel,
                     JTextField searchTxtFld,
                     JLabel searchErrorLbl,
                     JButton searchBtn,
                     JButton nextOccurrenceBtn,
                     JButton previousOccurrenceBtn,
                     JButton closeSearchBtn,
                     JButton editBtn,
                     JButton sendButton,
                     JButton infoPageBtn) {

        this.mainPanelPage = mainPanelPage;
        this.pageField = pageField;
        this.boldButton = boldButton;
        this.italicButton = italicButton;
        this.textButton = textButton;
        this.colorPickerButton = colorPickerButton;
        this.titlePageField = titlePageField;
        this.searchPanel = searchPanel;
        this.searchTxtFld = searchTxtFld;
        this.searchErrorLbl = searchErrorLbl;
        this.searchBtn = searchBtn;
        this.nextOccurrenceBtn = nextOccurrenceBtn;
        this.previousOccurrenceBtn = previousOccurrenceBtn;
        this.closeSearchBtn = closeSearchBtn;
        this.editBtn = editBtn;
        this.sendButton = sendButton;
        this.infoPageBtn = infoPageBtn;

        init_listeners();
    }

    public void init_listeners(){
        boldButton.addActionListener(e -> {
            //Verifica se l'utente ha selezionato del testo
            if(pageField.getSelectionStart() == pageField.getSelectionEnd()){
                JOptionPane.showMessageDialog(mainPanelPage, "Seleziona prima il testo !",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            }
            else{
                Controller.getWindow().getPage().insertHTML("BOLD",
                        null, null, -1);
            }
        });

        italicButton.addActionListener(e -> {
            //Verifica se l'utente ha selezionato del testo
            if(pageField.getSelectionStart() == pageField.getSelectionEnd()){
                JOptionPane.showMessageDialog(mainPanelPage, "Seleziona prima il testo !",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            }
            else{
                Controller.getWindow().getPage().insertHTML("ITALIC",
                        null, null, -1);
            }
        });

        textButton.addActionListener(e -> {
            //Verifica se l'utente ha selezionato del testo
            if(pageField.getSelectionStart() == pageField.getSelectionEnd()){
                JOptionPane.showMessageDialog(mainPanelPage, "Seleziona prima il testo !",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            }
            else{
                Controller.getWindow().getPage().insertHTML("TEXT",
                        null, null, -1);
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
                Controller.getWindow().getPage().createColorChooserComponent();
            }
        });

        pageField.addMouseWheelListener(e -> {
            if ((e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) != 0) {
                pageField.setFont(new Font(pageField.getFont().getFontName(),
                        pageField.getFont().getStyle(),
                        (int) (pageField.getFont().getSize() + (e.getPreciseWheelRotation() * -1))));

                titlePageField.setFont(new Font(titlePageField.getFont().getFontName(), titlePageField.getFont().getStyle(), (int) (titlePageField.getFont().getSize() + (e.getPreciseWheelRotation() * -1))));
            }
        });

        pageField.addHyperlinkListener(e -> {
            if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                Article article = Controller.getArticlesById(Integer.parseInt(e.getDescription()));
                ArticleVersion articleVersion = Controller.getLastArticleVersionByArticleId(Integer.parseInt(e.getDescription()));
                new PreviewPage(article.getTitle(), articleVersion.getText());
            }
        });

        pageField.addMouseWheelListener(e -> {
            if ((e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) != 0) {
                pageField.setFont(new Font(pageField.getFont().getFontName(), pageField.getFont().getStyle(), (int) (pageField.getFont().getSize() + (e.getPreciseWheelRotation() * -1))));
                titlePageField.setFont(new Font(titlePageField.getFont().getFontName(),
                        titlePageField.getFont().getStyle(),
                        (int) (titlePageField.getFont().getSize() + (e.getPreciseWheelRotation() * -1))));
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

        pageField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ALT) {
                    pageField.setEditable(false);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ALT) {
                    pageField.setEditable(true);
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

        searchBtn.addActionListener(e -> {
            Controller.getWindow().getPage().search();
        });

        nextOccurrenceBtn.addActionListener(e -> {
            Controller.getWindow().getPage().incrementIndexOfSearch();
            pageField.requestFocus();
            pageField.select(
                    Controller.getWindow().getPage().getCurrentSearchOccurrenceIndex().x,
                    Controller.getWindow().getPage().getCurrentSearchOccurrenceIndex().y);
        });
        previousOccurrenceBtn.addActionListener(e -> {
            Controller.getWindow().getPage().decrementIndexOfSearch();
            pageField.requestFocus();
            pageField.select(
                    Controller.getWindow().getPage().getCurrentSearchOccurrenceIndex().x,
                    Controller.getWindow().getPage().getCurrentSearchOccurrenceIndex().y);
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
                Controller.getWindow().getPage().setEditorMode();
            }
            else{
                if((JOptionPane.showConfirmDialog(null, "Devi essere loggato, effettuare il login ?", "Non sei loggato",
                        JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE)) == 0){

                    Controller.getWindow().switchPanel(Controller.getWindow().getLoginPanel());
                }
            }
        });

        sendButton.addActionListener(e -> {
            //TODO: CONTROLLI SUL TITOLO E SUL TESTO
            if (Controller.getWindow().getPage().getIdArticle() == -1){
                //Crea pagina
                Controller.getWindow().getPage().setIdArticle(Controller.createArticle(titlePageField.getText(), Controller.getCookie().getId(), pageField.getText()));
                if (Controller.getWindow().getPage().getIdArticle() != -1){
                    // Finesta -> Operazione avvenuta con successo
                }
                else {
                    // Finestra -> Operazione Fallita
                }
            }
            else{
                //Modifica pagina
                if (Controller.createProposal(Controller.getWindow().getPage().getIdArticle(), titlePageField.getText(),pageField.getText(), Controller.getCookie().getId()) != -1){
                    // Finesta -> Operazione avvenuta con successo
                }
                else {
                    // Finestra -> Operazione Fallita
                }
            }
        });

        infoPageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getWindow().getAuthorWindow().setIdAuthor(Controller.getAuthorByNickname(infoPageBtn.getText()).getId());
                Controller.getWindow().getAuthorWindow().setAuthorWindow();
                Controller.getWindow().getAuthorWindow().setVisible(true);
            }
        });
    }

}
