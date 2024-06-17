package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public final class ErrorDisplayer {
    private static final String standardTitle = "Errore";
    private static final String standardErrorMessage = "Si è verificato un errore: ";

    private ErrorDisplayer() {
    }

    public static void showError(Exception ex) {
        showError(ex, null, null);
    }

    public static void showError(Exception ex, String title, String message) {
        if (title == null)
            title = standardTitle;
        if (message == null)
            message = standardErrorMessage;

        if (ex != null)
            message = message.concat(ex.getMessage());

        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
        System.err.println(message);
    }


    public static void showErrorWithActions(Exception ex, ActionListener onOk, ActionListener onCancel, JFrame frame) {
        showErrorWithActions(ex, null, null, onOk, onCancel, null, null, frame);
    }

    public static void showErrorWithActions(Exception ex, String title, String message, ActionListener onOk, ActionListener onCancel, JFrame frame) {
        showErrorWithActions(ex, title, message, onOk, onCancel, null, null, frame);
    }

    public static void showErrorWithActions(Exception ex, String title, String message, ActionListener onOk, ActionListener onCancel, String okButtonText, String cancelButtonText, JFrame frame) {


        if (title == null)
            title = standardTitle;
        if (message == null)
            message = standardErrorMessage;

        if (okButtonText == null)
            okButtonText = "Ok";
        if (cancelButtonText == null)
            cancelButtonText = "Cancel";

        JDialog dialog = new JDialog(frame, title);
        dialog.setAlwaysOnTop(true);
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialog.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
        dialog.setSize(500, 300);
        JPanel mainPane = new JPanel(new GridBagLayout());
        GridBagConstraints gbc;

        JPanel labelPane = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPane.add(labelPane, gbc);

        JPanel buttonPane = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPane.add(buttonPane, gbc);


        if (ex != null)
            message = message.concat(ex.getMessage());

        System.err.println(message);

        JLabel warningLabel = new JLabel(message);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        labelPane.add(warningLabel, gbc);
        dialog.add(mainPane);

        if (onOk != null) {
            JButton buttonOK = new JButton(okButtonText);
            buttonOK.addActionListener(onOk);
            buttonOK.addActionListener(e -> dialog.dispose());
            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            buttonPane.add(buttonOK, gbc);
        }

        JButton buttonCancel = new JButton(cancelButtonText);

        if (onCancel != null) {
            buttonCancel.addActionListener(onCancel);
        }
        buttonCancel.addActionListener(e -> dialog.dispose());
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        buttonPane.add(buttonCancel, gbc);

        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        dialog.repaint();

    }
}
