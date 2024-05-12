package gui.homepage;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;


public class Home {
    private JPanel mainPanelHome;
    private JLabel titleLabel;
    private JLabel featuredLabel;
    private JLabel recentLabel;
    private JLabel footerLabel;



    private HomeRecentArticles homeRecentArticles;
    private HomeFeaturedArticles homeFeaturedArticles;



    private JButton reloadHome;
    private JButton infoBtn;

    public Home() {

        infoBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Controller.getWindow().getPage().openPage(Objects.requireNonNull(Controller.getArticleByTitle("MillionWiki")));
                Controller.getWindow().switchPanel(Controller.getWindow().getPage().getPanel());
            }
        });

        reloadHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reloadHome.setIcon(new ImageIcon(Home.class.getResource("/icons/reload.gif")));
                Controller.getWindow().getHomepage().getHomeFeaturedArticles().setHomeFeaturedArticles();
                Controller.getWindow().getHomepage().getHomeRecentArticles().setHomeRecentArticles();
                Controller.getWindow().switchPanel(Controller.getWindow().getHomePanel());
            }
        });

        // Titolo
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // Articoli in evidenza
        featuredLabel.setFont(new Font("Arial", Font.BOLD, 18));


        // Articoli recenti
        recentLabel.setFont(new Font("Arial", Font.BOLD, 18));


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
}
