package gui.homepage;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Home {
    private JPanel mainPanelHome;
    private JButton editorButton;
    private JLabel titleLabel;
    private JTextField searchField;
    private JLabel featuredLabel;
    private JLabel categoriesLabel;
    private JLabel recentLabel;
    private JLabel footerLabel;
    private HomeRecentArticles homeRecentArticles;

    public Home() {
        editorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getWindow().switchPanel(Controller.getWindow().getPagePanel());
            }
        });

        // Titolo
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // Articoli in evidenza
        featuredLabel.setFont(new Font("Arial", Font.BOLD, 14));


        // Articoli recenti
        recentLabel.setFont(new Font("Arial", Font.BOLD, 14));


        // Footer
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 10));
    }

    public JPanel getPanel() {
        return mainPanelHome;
    }
}
