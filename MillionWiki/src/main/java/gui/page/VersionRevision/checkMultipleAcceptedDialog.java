package gui.page.VersionRevision;

import javax.swing.*;
import java.awt.event.*;

public class checkMultipleAcceptedDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel warningLabel;
    private PageVersionPreview p;

    public checkMultipleAcceptedDialog(int acceptedCount, PageVersionPreview p) {
        this.p = p;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        warningLabel.setText("Hai accettato " + acceptedCount + " modifiche, vuoi che venga accettata solo l'ultima ?");

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
        p.setLastAccepted();
        p.checkReception();
        dispose();
    }

    private void onCancel() {
        dispose();
    }
}
