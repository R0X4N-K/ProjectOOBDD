package gui.toolbar;

import model.ArticleVersion;

import javax.swing.*;

import static controller.Controller.getAuthorById;

public class Notification {
    private JRadioButton acceptRadioButton;
    private JRadioButton rejectRadioButton;
    private JPanel notificationPanel;
    private JButton profileButton;
    private JButton proposalButton;
    private JButton confirmButton;

    public JPanel getPanel() {
        return notificationPanel;
    }

    public Notification (){
        acceptRadioButton.addActionListener(e -> rejectRadioButton.setSelected(false));

        rejectRadioButton.addActionListener(e -> acceptRadioButton.setSelected(false));

        confirmButton.addActionListener(e -> checkReception());
    }

    public Notification (ArticleVersion a) {
        this();
        profileButton.setText(getAuthorById(a.getId()).getNickname());
        //TODO: Add Link in proposalButton
    }
    private void checkReception() {
        if (acceptRadioButton.isSelected() && !rejectRadioButton.isSelected()) {
            //TODO: Accept Proposal
        } else if (!acceptRadioButton.isSelected() && rejectRadioButton.isSelected()) {
            // TODO: Refuse Proposal
        } else {
            System.out.println("Decidi se accettare o meno, se vuoi visualizzare la proposta premi il tasto proposta"); // TODO: change to dialog
        }
    }
}
