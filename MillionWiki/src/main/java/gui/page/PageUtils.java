package gui.page;

import controller.Controller;
import gui.ErrorDisplayer;
import model.Article;
import model.ArticleVersion;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.text.BadLocationException;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

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
    JMenuItem exportBtnToolMenu;
    JButton closeEditorMode;
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
                     JButton infoPageBtn,
                     JMenuItem exportBtnToolMenu,
                     JButton closeEditorMode) {

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
        this.exportBtnToolMenu = exportBtnToolMenu;
        this.closeEditorMode = closeEditorMode;
        final UndoManager undoMgr = new UndoManager();

        init_listeners(undoMgr);
    }

    public void init_listeners(UndoManager undoMgr){
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
                try {
                    Article article = Controller.getArticlesById(Integer.parseInt(e.getDescription()));
                    ArticleVersion articleVersion = Controller.getLastArticleVersionByArticleId(Integer.parseInt(e.getDescription()));
                    new PreviewPage(article.getTitle(), articleVersion.getText());
                } catch (SQLException | IllegalArgumentException ex) {
                    throw new RuntimeException(ex);
                }
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
        pageField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_V) {
                    String data = null;
                    try {
                        data = (String) Toolkit.getDefaultToolkit()
                                .getSystemClipboard().getData(DataFlavor.stringFlavor);
                    } catch (UnsupportedFlavorException | IOException ex) {
                        throw new RuntimeException(ex);
                    }

                    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(data), null);
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

                if(titlePageField.getText().isBlank() && titlePageField.getText().isEmpty()){
                    titlePageField.setForeground(Color.GRAY);
                    titlePageField.setText("Inserisci il titolo");
                    titlePageField.setCaretPosition(0);
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

                if(titlePageField.getForeground() == Color.GRAY && e.getKeyCode() != KeyEvent.VK_BACK_SPACE){
                    titlePageField.setCaretPosition(0);
                    titlePageField.setText("");
                    titlePageField.setForeground(Color.BLACK);
                }
            }
        });

        titlePageField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(titlePageField.getForeground() == Color.GRAY){
                    titlePageField.setCaretPosition(0);
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
            System.out.println(pageField.getText());
            //TODO: CONTROLLI SUL TITOLO E SUL TESTO
            try {
                if(!titlePageField.getText().isBlank() &&
                        pageField.getDocument().getLength() != 0 &&
                        titlePageField.getForeground() != Color.GRAY &&
                        pageField.getDocument().getText(0, pageField.getDocument().getLength()) != null &&
                        !pageField.getDocument().getText(0, pageField.getDocument().getLength()).trim().isEmpty()
                ){
                    if (Controller.getWindow().getPage().getIdArticle() == -1){
                        //Crea pagina
                        Controller.getWindow().getPage().setIdArticle(Controller.createArticle(titlePageField.getText(), Controller.getCookie().getId(), pageField.getText()));
                        if (Controller.getWindow().getPage().getIdArticle() != -1){
                            JOptionPane.showMessageDialog(mainPanelPage, "Creazione e salvatggio articolo avvenuta correttamente !",
                                    "Creazione articolo", JOptionPane.PLAIN_MESSAGE);

                            Controller.getWindow().getPage().setViewerMode();
                        }
                        else {
                            JOptionPane.showMessageDialog(mainPanelPage, "Creazione e salvatggio articolo fallita!",
                                    "Errore", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else{
                        //Modifica pagina
                        if (Controller.createProposal(Controller.getWindow().getPage().getIdArticle(), titlePageField.getText(),pageField.getText(), Controller.getCookie().getId()) != -1){
                            // Finesta -> Operazione avvenuta con successo
                            JOptionPane.showMessageDialog(mainPanelPage, "Modifica avvenuta correttamente !",
                                    "Modifica articolo", JOptionPane.PLAIN_MESSAGE);

                            Controller.getWindow().getPage().setViewerMode();
                        }
                        else {
                            // Finestra -> Operazione Fallita
                            JOptionPane.showMessageDialog(mainPanelPage, "Modifica articolo fallita !",
                                    "Errore", JOptionPane.ERROR);
                        }
                    }
                }
                else{
                    //TITOLO O TESTO VUOTO
                    JOptionPane.showMessageDialog(mainPanelPage, "Titolo o testo mancante",
                            "Errore", JOptionPane.ERROR_MESSAGE);
                }
            } catch (BadLocationException | SQLException ex) {
                throw new RuntimeException(ex);
            }

        });

        infoPageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Controller.getWindow().getAuthorWindow().setIdAuthor(Controller.getAuthorByNickname(infoPageBtn.getText()).getId());
                    Controller.getWindow().getAuthorWindow().setAuthorWindow();
                    Controller.getWindow().getAuthorWindow().setVisible(true);
                } catch (SQLException ex) {
                    ErrorDisplayer.showError(ex);
                }
            }
        });

        exportBtnToolMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportArticle();
            }
        });

        closeEditorMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getWindow().getPage().setViewerMode();
            }
        });

    }
    public void exportArticle() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salva come HTML");

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try {
                FileWriter fw = new FileWriter(fileToSave);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(pageField.getText());
                bw.close();
                fw.close();
                JOptionPane.showMessageDialog(null, "Documento HTML esportato con successo.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Si è verificato un errore durante l'esportazione.");
                ex.printStackTrace();
            }
        }
    }

}
