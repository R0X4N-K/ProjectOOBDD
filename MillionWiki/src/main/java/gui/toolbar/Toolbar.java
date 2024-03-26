package gui.toolbar;
import controller.Controller;
import model.Article;
import model.ArticleVersion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.MouseInputListener;
import javax.swing.plaf.basic.BasicTextFieldUI;
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
    private JButton searchBtn;

    private JDialog searchDialog;
    private JPanel searchDialogPanel;

    public Toolbar() {

        searchTxtFld.setColumns(30);

        searchDialog = new JDialog();
        searchDialog.setLayout(new BorderLayout());
        searchDialog.setSize(200, 200);
        searchDialog.setResizable(false);
        searchDialog.setVisible(false);
        searchDialog.setType(Window.Type.UTILITY);
        searchDialog.setAlwaysOnTop(true);
        searchDialog.setFocusableWindowState(false);
        searchDialog.setUndecorated(true);

        // Aggiungi il pannello al JDialog invece che al JDialog stesso
        searchDialogPanel = new JPanel();
        searchDialogPanel.setLayout(new BoxLayout(searchDialogPanel, BoxLayout.Y_AXIS));

        searchDialog.add(searchDialogPanel, BorderLayout.CENTER);
        searchDialog.add(new JScrollPane(searchDialogPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));

        searchDialog.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {

            }

            @Override
            public void componentMoved(ComponentEvent e) {
                updateSearchDialogPos();
            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });


        if (Controller.checkLoggedUser()) {
            switchPanel(LoggedToolbar.getPanel());
        } else {
            switchPanel(UnloggedToolbar.getPanel());
        }


        homeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getWindow().switchPanel(Controller.getWindow().getHomePanel());
            }
        });


        searchTxtFld.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if(!searchTxtFld.getText().isBlank() && !searchTxtFld.getText().isEmpty()){

                    searchDialog.setVisible(true);
                    updateSearchDialogPos();

                    ArrayList<Article> matchesArticles = Controller.getMatchesArticlesByTitle(searchTxtFld.getText());
                    searchDialogPanel.removeAll();
                    for (Article mathesArticle : matchesArticles) {
                        System.out.println(mathesArticle.getTitle());


                        JLabel articleItem = new JLabel(mathesArticle.getTitle());
                        articleItem.setFont(new Font(searchTxtFld.getFont().getFontName(), searchTxtFld.getFont().getStyle(), searchTxtFld.getFont().getSize() + 1));
                        articleItem.setBorder(new EmptyBorder(4, 4, 0, 0));

                        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                        panel.setMaximumSize(new Dimension(searchTxtFld.getWidth(), 30));
                        panel.add(articleItem);

                        panel.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseEntered(MouseEvent e) {
                                panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                                panel.setBackground(Color.decode("#007bff"));
                                articleItem.setForeground(Color.WHITE);
                            }

                            @Override
                            public void mouseExited(MouseEvent e) {
                                panel.setCursor(Cursor.getDefaultCursor());
                                panel.setBackground(null);
                                articleItem.setForeground(Color.BLACK);
                            }

                            @Override
                            public void mouseClicked(MouseEvent e) {
                                System.out.println("Hai cliccato su " + mathesArticle.getTitle());

                                ArticleVersion articleVersion = Controller.getLastArticleVersionByArticleId(mathesArticle.getId());
                               if(articleVersion != null){
                                   Controller.getWindow().switchPanel(Controller.getWindow().getPagePanel());
                                   Controller.getWindow().getPage().setViewerMode();
                                   Controller.getWindow().getPage().setTitlePageField(mathesArticle.getTitle());
                                   Controller.getWindow().getPage().setTextPageField(articleVersion.getText());
                                   Controller.getWindow().getPage().setIdArticle(mathesArticle.getId());
                                   searchDialog.setVisible(false);
                               }

                            }
                        });

                        searchDialogPanel.add(panel);
                    }

                    if(matchesArticles.size() < 9)
                        searchDialog.setSize(searchDialog.getWidth(), matchesArticles.size() * 35);
                    else
                        searchDialog.setSize(searchDialog.getWidth(), 200);

                    if(matchesArticles.isEmpty()){
                        searchDialog.setSize(searchDialog.getWidth(), 25);
                        searchDialogPanel.add(new JLabel("Nessun risultato"));
                    }
                    System.out.println("----");
                }
                else{
                    searchDialogPanel.removeAll();
                    searchDialog.setVisible(false);
                }
                searchDialogPanel.revalidate();
                searchDialogPanel.repaint();
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



        switchUnloggedLoggedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanel(LoggedToolbar.getPanel());
            }
        });
        switchToUnloggedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanel(UnloggedToolbar.getPanel());
            }
        });
        createPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Controller.checkLoggedUser()) {
                    Controller.getWindow().switchPanel(Controller.getWindow().getPagePanel());
                    Controller.getWindow().getPage().setEditorMode();
                    Controller.getWindow().getPage().setIdArticle(-1);
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

    public void updateSearchDialogPos(){
        searchDialog.setLocation((int) searchTxtFld.getLocationOnScreen().getX(), (int) (searchTxtFld.getLocationOnScreen().getY() + searchTxtFld.getHeight()));
        searchDialog.setSize(searchTxtFld.getWidth(), searchDialog.getHeight());
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

    public void setProfile(String nickname) {
        LoggedToolbar.setNicknameProfile(nickname);
    }

    public JPanel getPanel() {
        return mainPanelToolbar;
    }

}
