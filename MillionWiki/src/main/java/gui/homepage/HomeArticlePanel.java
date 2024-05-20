package gui.homepage;

import controller.Controller;
import gui.ErrorDisplayer;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class HomeArticlePanel extends JPanel{

    private JPanel homeArticleMainPanel;

    public HomeArticlePanel(String title, String articleText, int articleId) {
        String smallText = title;
        setLayout(new BorderLayout());

        setBackground(Color.decode("#2c82c9"));

        setToolTipText(title);

        if(title.length() > 21)
            smallText = smallText.substring(0, 21) + "...";

        JLabel titleLabel = new JLabel(smallText);
        titleLabel.setToolTipText(title);
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
                try {
                    Controller.getWindow().getPage().openPage(Controller.getArticlesById(articleId));
                    Controller.getWindow().switchPanel(Controller.getWindow().getPage().getPanel());
                } catch (SQLException | IllegalArgumentException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


        JTextPane textPane = new JTextPane();
        textPane.setToolTipText(title);
        textPane.setContentType("text/html");
        textPane.setText(articleText);
        textPane.getCaret().setVisible(false);
        textPane.setCaretColor(textPane.getBackground());
        textPane.setEditable(false);
        textPane.setCursor(new Cursor(Cursor.HAND_CURSOR));

            textPane.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        Controller.getWindow().getPage().openPage(Controller.getArticlesById(articleId));
                        Controller.getWindow().switchPanel(Controller.getWindow().getPage().getPanel());
                    } catch (SQLException | IllegalArgumentException ex) {
                        ErrorDisplayer.showError(ex);
                    }
                }
            });

        textPane.setCaret(new DefaultCaret() {
            @Override
            public void setSelectionVisible(boolean visible) {
                super.setSelectionVisible(false);  // Non mostra la selezione
            }
        });


        textPane.setBorder(new EmptyBorder(5, 5, 10, 0));

        textPane.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                textPane.setCaretPosition(0);
            }
        });

        textPane.addMouseWheelListener(new MouseWheelListener() {

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                e.consume();
            }
        });

        textPane.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
                    e.consume();
                }
            }
        });

        textPane.addCaretListener(new CaretListener() {

            @Override
            public void caretUpdate(CaretEvent e) {
                textPane.setCaretPosition(0);
            }
        });

        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setPreferredSize(new Dimension(200, 150));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));

        setMaximumSize(new Dimension(200, 150));
        setPreferredSize(new Dimension(200, 150));

        add(titleLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}