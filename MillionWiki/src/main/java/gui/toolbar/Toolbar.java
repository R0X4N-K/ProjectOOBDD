package gui.toolbar;
import controller.Controller;
import gui.page.Page;
import model.Article;
import model.ArticleVersion;
import model.Author;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Toolbar {
    private JPanel commonToolbar;
    private JButton homeBtn;



    private JTextField searchTxtFld;
    private JPanel accessUserPanel;
    private JPanel mainPanelToolbar;
    private JPanel uncommonToolbar;
    private UnloggedToolbar UnloggedToolbar;
    private LoggedToolbar LoggedToolbar;
    private JButton switchUnloggedLoggedButton;
    private JButton switchToUnloggedButton;
    private JButton createPageButton;
    private JComboBox typeSearchCb;

    private AnchoredDialog searchDialog;
    private JPanel searchDialogPanel;

    private Thread searchThread;

    public Toolbar() {

        searchThread = new Thread(this::search);
        searchThread.setDaemon(true);


        //CHECK LOGGED USER
        if (Controller.checkLoggedUser()) {
            switchPanel(LoggedToolbar.getPanel());
        } else {
            switchPanel(UnloggedToolbar.getPanel());
        }

        searchTxtFld.setColumns(30);

        //SEARCH COMPONENTS CREATION
        createSearchDialogComponent();

        //LISTENERS


        homeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getWindow().switchPanel(Controller.getWindow().getHomePanel());
//                Controller.getWindow().getHomepage().getHomeFeaturedArticles().setHomeFeaturedArticles();
//                Controller.getWindow().getHomepage().getHomeRecentArticles().setHomeRecentArticles();
            }
        });


        searchTxtFld.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (searchThread == null || !searchThread.isAlive()) {
                    searchThread = new Thread(Toolbar.this::search); // Crea un nuovo thread se il precedente è terminato
                    searchThread.setDaemon(true);
                    searchThread.start();
                }
            }
        });

        typeSearchCb.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (searchThread == null || !searchThread.isAlive()) {
                    searchThread = new Thread(Toolbar.this::search); // Crea un nuovo thread se il precedente è terminato
                    searchThread.setDaemon(true);
                    searchThread.start();
                }
            }
        });

        searchTxtFld.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(searchDialogPanel.getComponentCount() > 0)
                    searchDialog.setVisible(true);
            }
        });


        searchTxtFld.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                searchDialog.setVisible(false);
                searchTxtFld.setBorder(new LineBorder(Color.GRAY, 1, false));
            }
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                searchTxtFld.setBorder(new LineBorder(Color.decode("#007bff"), 2, false));
            }
        });




        createPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Controller.checkLoggedUser()) {
                    Controller.getWindow().switchPanel(Controller.getWindow().getPagePanel());
                    if(Controller.getWindow().getPage().getMode() != Page.Mode.EDITOR)
                        Controller.getWindow().getPage().createNewPage();
                }
                else{
                    if((JOptionPane.showConfirmDialog(null, "Devi essere loggato, effettuare il login ?", "Non sei loggato",
                            JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE)) == 0){
                        Controller.getWindow().switchPanel(Controller.getWindow().getLoginPanel());
                    }
                }
            }
        });
    }

    private void search(){
        if(!searchTxtFld.getText().isBlank() && !searchTxtFld.getText().isEmpty()){
            searchDialog.setVisible(true);
            searchDialog.updateDialogPos();

            searchDialogPanel.removeAll();

            ArrayList<Article> matchesArticles = null;
            ArrayList<Author> matchesAuthors = null;

            if(typeSearchCb.getSelectedItem().equals("Articoli")){
                matchesArticles = Controller.getMatchesArticlesByTitle(searchTxtFld.getText());

                for (Article mathesArticle : matchesArticles) {

                    JLabel articleItem = new JLabel(mathesArticle.getTitle());
                    articleItem.setFont(new Font(searchTxtFld.getFont().getFontName(), searchTxtFld.getFont().getStyle(), searchTxtFld.getFont().getSize() + 1));
                    articleItem.setBorder(new EmptyBorder(4, 2, 0, 0));

                    JPanel articleItemPnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    articleItemPnl.setMaximumSize(new Dimension(1000, 30));
                    articleItemPnl.add(articleItem);

                    articleItemPnl.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            articleItemPnl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                            articleItemPnl.setBackground(Color.decode("#007bff"));
                            articleItem.setForeground(Color.WHITE);
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            articleItemPnl.setCursor(Cursor.getDefaultCursor());
                            articleItemPnl.setBackground(null);
                            articleItem.setForeground(Color.BLACK);
                        }

                        @Override
                        public void mouseClicked(MouseEvent e) {
                            ArticleVersion articleVersion = Controller.getLastArticleVersionByArticleId(mathesArticle.getId());
                            if(articleVersion != null){
                                Controller.getWindow().switchPanel(Controller.getWindow().getPagePanel());
                                Controller.getWindow().getPage().openPage(mathesArticle);
                                searchDialog.setVisible(false);
                            }

                        }
                    });

                    searchDialogPanel.add(articleItemPnl);
                }

                if(matchesArticles.size() < 9)
                    searchDialog.setSize(searchDialog.getWidth(), matchesArticles.size() * 35);
                else
                    searchDialog.setSize(searchDialog.getWidth(), 200);

                if(matchesArticles.isEmpty()){
                    searchDialog.setSize(searchDialog.getWidth(), 25);
                    searchDialogPanel.add(new JLabel("Nessun risultato"));
                }



            }

            else{
                matchesAuthors = Controller.getMatchesAuthorByNickname(searchTxtFld.getText());

                for (Author author : matchesAuthors) {

                    JLabel authorItem = new JLabel(author.getNickname());
                    authorItem.setFont(new Font(searchTxtFld.getFont().getFontName(), searchTxtFld.getFont().getStyle(), searchTxtFld.getFont().getSize() + 1));
                    authorItem.setBorder(new EmptyBorder(4, 2, 0, 0));

                    JPanel authorItemPnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    authorItemPnl.setMaximumSize(new Dimension(1000, 30));
                    authorItemPnl.add(authorItem);

                    authorItemPnl.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            authorItemPnl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                            authorItemPnl.setBackground(Color.decode("#007bff"));
                            authorItem.setForeground(Color.WHITE);
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            authorItemPnl.setCursor(Cursor.getDefaultCursor());
                            authorItemPnl.setBackground(null);
                            authorItem.setForeground(Color.BLACK);
                        }

                        @Override
                        public void mouseClicked(MouseEvent e) {
                            //TODO: apertura profilo autore
                            Controller.getWindow().getAuthorWindow().setIdAuthor(author.getId());
                            Controller.getWindow().getAuthorWindow().setAuthorWindow();
                            Controller.getWindow().getAuthorWindow().setVisible(true);
                        }
                    });

                    searchDialogPanel.add(authorItemPnl);
                }

                if(matchesAuthors.size() < 9)
                    searchDialog.setSize(searchDialog.getWidth(), matchesAuthors.size() * 35);
                else
                    searchDialog.setSize(searchDialog.getWidth(), 200);

                if(matchesAuthors.isEmpty()){
                    searchDialog.setSize(searchDialog.getWidth(), 25);
                    searchDialogPanel.add(new JLabel("Nessun risultato"));
                }
            }



        }
        else{
            searchDialogPanel.removeAll();
            searchDialog.setVisible(false);
        }
        searchDialogPanel.revalidate();
        searchDialogPanel.repaint();
    }

    private void createSearchDialogComponent(){

        searchDialogPanel = new JPanel();
        searchDialogPanel.setLayout(new BoxLayout(searchDialogPanel, BoxLayout.Y_AXIS));
        searchDialog = new AnchoredDialog(searchTxtFld, null, 200, 200, searchDialogPanel, AnchoredDialog.SizeAnchoring.WIDTH, AnchoredDialog.AnchoringPointX.LEFT, AnchoredDialog.AnchoringPointY.UP, AnchoredDialog.SpawnPoint.DOWN);

    }


    public void switchPanel(JPanel refPanel) {
        uncommonToolbar.removeAll();
        uncommonToolbar.add(refPanel);
        uncommonToolbar.repaint();
        uncommonToolbar.revalidate();
    }

    public void switchToLoggedToolbar() {
        switchPanel(LoggedToolbar.getPanel());
    }

    public void switchToUnloggedToolbar() {
        switchPanel(UnloggedToolbar.getPanel());
    }

    public JPanel getLoggedToolbar() {
        return LoggedToolbar.getPanel();
    }
    public JPanel getPanel() {
        return mainPanelToolbar;
    }
    public JTextField getSearchTxtFld() {
        return searchTxtFld;
    }
}
