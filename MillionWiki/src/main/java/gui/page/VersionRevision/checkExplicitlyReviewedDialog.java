package gui.page.VersionRevision;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class checkExplicitlyReviewedDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel warningLabel;
    private final PageVersionPreview p;

    public checkExplicitlyReviewedDialog(int unexplicitedCount, PageVersionPreview p) {
        this.p = p;
        setContentPane(contentPane);
        setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
        getRootPane().setDefaultButton(buttonOK);
        setLocationRelativeTo(null);
        warningLabel.setText("Non hai espresso un parere esplicito su " + unexplicitedCount + " modifiche, sono tutte da rifiutare?");

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        p.rejectUnexplicited();
        dispose();
    }

    private void onCancel() {
        dispose();
    }

}
