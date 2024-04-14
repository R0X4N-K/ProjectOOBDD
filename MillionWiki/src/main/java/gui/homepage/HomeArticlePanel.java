package gui.homepage;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HomeArticlePanel extends JPanel{

    private JPanel homeArticleMainPanel;

    public HomeArticlePanel(String title, String articleText, int articleId) {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("<html><a href='" + articleId +"'>" + title + "</a></html>", SwingConstants.CENTER);
        titleLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        titleLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Controller.getWindow().getPage().openPage(Controller.getTitleByArticleId(articleId), Controller.getLastArticleVersionByArticleId(articleId).getText(), articleId);
                Controller.getWindow().switchPanel(Controller.getWindow().getPage().getPanel());
            }
        });

        JTextPane textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setText(articleText);
        textPane.getCaret().setVisible(false);
        textPane.setCaretColor(textPane.getBackground());
        textPane.setEditable(false);
        textPane.setCursor(new Cursor(Cursor.HAND_CURSOR));
        textPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Controller.getWindow().getPage().openPage(Controller.getTitleByArticleId(articleId), Controller.getLastArticleVersionByArticleId(articleId).getText(), articleId);
                Controller.getWindow().switchPanel(Controller.getWindow().getPage().getPanel());
               }
        });

        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setPreferredSize(new Dimension(200, 150));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        add(titleLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}