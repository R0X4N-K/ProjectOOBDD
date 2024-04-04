package gui.page;

import javax.swing.*;
import java.awt.*;

public class LinkOpener extends JDialog {
    private JTextField titleTxtFld;
    private JEditorPane textEp;
    public LinkOpener(String title, String text){
        titleTxtFld = new JTextField(title);
        textEp = new JEditorPane();
        textEp.setContentType("text/html");
        textEp.setText(text);

        setLayout(new BorderLayout());
        setSize(1200, 700);
        setLocationRelativeTo(null);

        titleTxtFld.setFont(new Font(getFont().getFontName(), Font.BOLD, getFont().getSize() + 15));
        titleTxtFld.setEditable(false);
        titleTxtFld.setCaretColor(titleTxtFld.getBackground());

        textEp.setEditable(false);
        titleTxtFld.setCaretColor(titleTxtFld.getBackground());
        textEp.setCaretColor(textEp.getBackground());
        add(titleTxtFld, BorderLayout.NORTH);
        add(textEp, BorderLayout.CENTER);

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
