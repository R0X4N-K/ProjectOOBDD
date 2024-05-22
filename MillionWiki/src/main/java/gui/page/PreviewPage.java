package gui.page;

import controller.Controller;
import gui.ErrorDisplayer;
import model.Article;
import model.ArticleVersion;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.HierarchyEvent;
import java.awt.event.InputEvent;
import java.sql.SQLException;

public class PreviewPage extends JDialog {
    private JTextField titleTxtFld;
    private JEditorPane textEp;
    private JScrollPane scrollPane;

    public PreviewPage(String title, String text){
        init_component(title, text);

    }

    public PreviewPage(String titleDlg, String title, String text){
        setTitle(titleDlg);
        init_component(title, text);
    }


    public PreviewPage(){
        init_component(null, null);
    }

    private void init_component(String title, String text){
        setModal(true);

        titleTxtFld = new JTextField(title);
        textEp = new JEditorPane();
        scrollPane = new JScrollPane(textEp);

        textEp.setFont(new Font(textEp.getFont().getFontName(), textEp.getFont().getStyle(), 18));

        textEp.setContentType("text/html");
        textEp.setText(text);

        setLayout(new BorderLayout());
        setSize((int) Controller.getWindow().getSize().getWidth(), (int) Controller.getWindow().getSize().getHeight());
        setLocationRelativeTo(Controller.getWindow());
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        titleTxtFld.setFont(new Font(titleTxtFld.getFont().getFontName(), Font.BOLD, titleTxtFld.getFont().getSize() + 15));
        titleTxtFld.setEditable(false);
        titleTxtFld.setCaretColor(titleTxtFld.getBackground());

        textEp.setEditable(false);
        titleTxtFld.setCaretColor(titleTxtFld.getBackground());
        textEp.setCaretColor(textEp.getBackground());

        textEp.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (textEp.getWidth() > 0 && textEp.getHeight() > 0) {
                    SwingUtilities.invokeLater(() -> {
                        scrollPane.getVerticalScrollBar().setValue(0);
                    });
                }
            }
        });


        textEp.addHyperlinkListener(e -> {
            if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                try {
                    Article article = Controller.getArticlesById(Integer.parseInt(e.getDescription()));
                    ArticleVersion articleVersion = Controller.getLastArticleVersionByArticleId(Integer.parseInt(e.getDescription()));

                    titleTxtFld.setText(article.getTitle());
                    textEp.setText(articleVersion.getText());
                } catch (SQLException | IllegalArgumentException ex) {
                    ErrorDisplayer.showError(ex);
                    titleTxtFld.setText("!ERRORE DI VISUALIZZAZIONE!");
                    textEp.setText("!ERRORE DI VISUALIZZAZIONE!");
                }
            }
        });

        textEp.addMouseWheelListener(e -> {
            if ((e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) != 0) {
                textEp.setFont(new Font(textEp.getFont().getFontName(), textEp.getFont().getStyle(), (int) (textEp.getFont().getSize() + (e.getPreciseWheelRotation() * -1))));
                titleTxtFld.setFont(new Font(titleTxtFld.getFont().getFontName(),
                        titleTxtFld.getFont().getStyle(),
                        (int) (titleTxtFld.getFont().getSize() + (e.getPreciseWheelRotation() * -1))));
            }
            else
            {
                if (scrollPane != null)
                    scrollPane.dispatchEvent(SwingUtilities.convertMouseEvent(textEp, e, scrollPane));
            }
        });


        add(titleTxtFld, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);


        setVisible(true);

    }


    public JTextField getTitleTxtFld() {
        return titleTxtFld;
    }

    public JEditorPane getTextEp() {
        return textEp;
    }
}
