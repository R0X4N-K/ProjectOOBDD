package gui.page;

import controller.Controller;
import model.Article;
import model.ArticleVersion;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;

public class LinkOpener extends JDialog {
    private JTextField titleTxtFld;
    private JEditorPane textEp;
    private JScrollPane scrollPane;

    public LinkOpener(String title, String text){
        titleTxtFld = new JTextField(title);
        textEp = new JEditorPane();
        scrollPane = new JScrollPane(textEp);

        textEp.setContentType("text/html");
        textEp.setText(text);

        setLayout(new BorderLayout());
        setSize((int) Controller.getWindow().getSize().getWidth(), (int) Controller.getWindow().getSize().getHeight());
        setLocationRelativeTo(Controller.getWindow());

        titleTxtFld.setFont(new Font(getFont().getFontName(), Font.BOLD, getFont().getSize() + 15));
        titleTxtFld.setEditable(false);
        titleTxtFld.setCaretColor(titleTxtFld.getBackground());

        textEp.setEditable(false);
        titleTxtFld.setCaretColor(titleTxtFld.getBackground());
        textEp.setCaretColor(textEp.getBackground());

        textEp.addHyperlinkListener(e -> {
            if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                Article article = Controller.getArticlesById(Integer.parseInt(e.getDescription()));
                ArticleVersion articleVersion = Controller.getLastArticleVersionByArticleId(Integer.parseInt(e.getDescription()));

                titleTxtFld.setText(article.getTitle());
                textEp.setText(articleVersion.getText());
            }
        });

        add(titleTxtFld, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setModal(true);
        setVisible(true); 
    }

    public JTextField getTitleTxtFld() {
        return titleTxtFld;
    }

    public JEditorPane getTextEp() {
        return textEp;
    }
}
