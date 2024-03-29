package gui.profileWindow;

import javax.swing.*;

public class ProposalCard {
    private JPanel proposalCardPanel;
    private JTable proposalCardJTable;
    private JScrollPane proposalCardJTableJScrollPane;

    public JPanel getPanel() {
        return proposalCardPanel;
    }

    private void createUIComponents() {
            proposalCardJTable = new JTable();
    }

    private JTable createTable() {
        return new JTable();
    }

    private void setProposalCardJTable() {
        proposalCardJTable = createTable();
    }



}
