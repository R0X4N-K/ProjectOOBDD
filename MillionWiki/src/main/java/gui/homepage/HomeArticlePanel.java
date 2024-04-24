package gui.homepage;

import controller.Controller;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HomeArticlePanel extends JPanel{

    private JPanel homeArticleMainPanel;

    public HomeArticlePanel(String title, String articleText, int articleId) {
        setLayout(new BorderLayout());

        setBackground(Color.decode("#2c82c9"));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        titleLabel.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        titleLabel.setFont(new Font(
                titleLabel.getFont().getFontName(),
                Font.BOLD,
                15
        ));
        titleLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        titleLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Controller.getWindow().getPage().openPage(Controller.getArticlesById(articleId));
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
                Controller.getWindow().getPage().openPage(Controller.getArticlesById(articleId));
                Controller.getWindow().switchPanel(Controller.getWindow().getPage().getPanel());
               }
        });

        textPane.setBorder(new EmptyBorder(5, 5, 10, 0));

        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setPreferredSize(new Dimension(200, 150));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));

        add(titleLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}