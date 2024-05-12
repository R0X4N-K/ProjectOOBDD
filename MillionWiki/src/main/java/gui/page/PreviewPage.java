package gui.page;

import controller.Controller;
import model.Article;
import model.ArticleVersion;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.awt.event.InputEvent;

public class PreviewPage extends JDialog {
    private JTextField titleTxtFld;
    private JEditorPane textEp;
    private JScrollPane scrollPane;

    public PreviewPage(String title, String text){
        init_component(title, text);
    }
    public PreviewPage(){
        init_component(null, null);
    }

    public void setTitle(String title){
        titleTxtFld.setText(title);
    }
    public void setText(String text){
        textEp.setText(text);
    }

    private void init_component(String title, String text){
        titleTxtFld = new JTextField(title);
        textEp = new JEditorPane();
        scrollPane = new JScrollPane(textEp);

        textEp.setFont(new Font(textEp.getFont().getFontName(), textEp.getFont().getStyle(), 18));

        textEp.setContentType("text/html");
        textEp.setText(text);

        setLayout(new BorderLayout());
        setSize((int) Controller.getWindow().getSize().getWidth(), (int) Controller.getWindow().getSize().getHeight());
        setLocationRelativeTo(Controller.getWindow());

        titleTxtFld.setFont(new Font(titleTxtFld.getFont().getFontName(), Font.BOLD, titleTxtFld.getFont().getSize() + 15));
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

        textEp.addMouseWheelListener(e -> {
            if ((e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) != 0) {
                textEp.setFont(new Font(textEp.getFont().getFontName(), textEp.getFont().getStyle(), (int) (textEp.getFont().getSize() + (e.getPreciseWheelRotation() * -1))));
                titleTxtFld.setFont(new Font(titleTxtFld.getFont().getFontName(),
                        titleTxtFld.getFont().getStyle(),
                        (int) (titleTxtFld.getFont().getSize() + (e.getPreciseWheelRotation() * -1))));
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
