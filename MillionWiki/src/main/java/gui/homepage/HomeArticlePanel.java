package gui.homepage;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class HomeArticlePanel extends JPanel{

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

        JEditorPane editorPane = new JEditorPane();
        editorPane.setContentType("text/html");
        editorPane.setText(articleText);
        editorPane.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(editorPane);
        scrollPane.setPreferredSize(new Dimension(200, 150));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        add(titleLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}