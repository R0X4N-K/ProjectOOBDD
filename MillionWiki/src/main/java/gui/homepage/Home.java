package gui.homepage;

import controller.Controller;
import gui.ErrorDisplayer;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Objects;


public class Home {
    private JPanel mainPanelHome;
    private JLabel titleLabel;
    private JLabel footerLabel;



    private HomeRecentArticles homeRecentArticles;
    private HomeFeaturedArticles homeFeaturedArticles;



    private JButton reloadHome;
    private JButton infoBtn;

    private JProgressBar progressBarHome;

    public Home() {

        infoBtn.addActionListener(e -> {
            try {
                Controller.getWindow().getPage().openPage(Objects.requireNonNull(Controller.getArticleByTitle("MillionWiki")));
            } catch (SQLException ex) {
                ErrorDisplayer.showError(ex);
            }
            Controller.getWindow().switchPanel(Controller.getWindow().getPage().getPanel());
        });

        reloadHome.addActionListener(e -> {
            if(!reloadHome.getIcon().toString().equals(
                    new ImageIcon(Home.class.getResource("/icons/reload.gif").toString()))){

                reloadHome.setIcon(new ImageIcon(Home.class.getResource("/icons/reload.gif")));
                if (!Controller.getSplashScreen().isVisible())
                    progressBarHome.setVisible(true);

                Controller.getWindow().getHomepage().getHomeFeaturedArticles().setHomeFeaturedArticles();
                Controller.getWindow().getHomepage().getHomeRecentArticles().setHomeRecentArticles();
                Controller.getWindow().switchPanel(Controller.getWindow().getHomePanel());
            }
        });

        // Titolo
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // Footer
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 10));
    }
    public HomeRecentArticles getHomeRecentArticles() {
        return homeRecentArticles;
    }

    public HomeFeaturedArticles getHomeFeaturedArticles() {
        return homeFeaturedArticles;
    }
    public JButton getReloadHome() {
        return reloadHome;
    }

    public JPanel getPanel() {
        return mainPanelHome;
    }
    public JProgressBar getProgressBarHome() {
        return progressBarHome;
    }

    public void incrementProgressBarHome(){
        progressBarHome.setValue(progressBarHome.getValue() + 1);
    }

}
