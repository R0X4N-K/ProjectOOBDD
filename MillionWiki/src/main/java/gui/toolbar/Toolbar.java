package gui.toolbar;
import controller.Controller;
import model.Article;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.MouseInputListener;
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

    public Toolbar() {



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

                    ArrayList<Article> matchesArticles = Controller.getMatchesArticlesByTitle(searchTxtFld.getText());

                    for (Article mathesArticle : matchesArticles) {
                        System.out.println(mathesArticle.getTitle());
                    }
                    System.out.println("----");
                }
                else{

                }
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
                    Controller.getWindow().getPage().setEditorMode();
                    Controller.getWindow().switchPanel(Controller.getWindow().getPagePanel());
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
