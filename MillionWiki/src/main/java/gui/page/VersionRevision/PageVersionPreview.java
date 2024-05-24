package gui.page.VersionRevision;

import controller.Controller;
import gui.ErrorDisplayer;
import model.ArticleVersion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class PageVersionPreview {
    private JPanel pageVersionPreviewMainPanel;
    private JButton backButton;
    private JButton nextButton;
    private JRadioButton acceptRadioButton;
    private JRadioButton rejectRadioButton;
    private JButton confirmButton;
    private JButton profileButton;
    private JEditorPane titlePane;
    private JEditorPane editorPane;
    private ArrayList<ArticleVersion> articleVersions = new ArrayList<>();
    private int currentArticlePosition = 0;
    private ArrayList<ArticleVersion> reviewed = new ArrayList<>();
    private ArrayList<ArticleVersion> accepted = new ArrayList<>();
    private int rejectedCount = 0;

    public PageVersionPreview(){
        backButton.addActionListener( actionEvent -> {
            currentArticlePosition -=1;
            changeProposal();
        });


        nextButton.addActionListener( actionEvent -> {
            currentArticlePosition += 1;
            changeProposal();
        });


        acceptRadioButton.addActionListener(e -> {
            rejectedCount -= rejectRadioButton.isSelected() ? 1 : 0;
            rejectRadioButton.setSelected(false);
            if (acceptRadioButton.isSelected()) {
                accepted.add(articleVersions.get(currentArticlePosition));
                articleVersions.get(currentArticlePosition).setStatus(ArticleVersion.Status.ACCEPTED);
            } else {
                accepted.remove(articleVersions.get(currentArticlePosition));
                articleVersions.get(currentArticlePosition).setStatus(ArticleVersion.Status.WAITING);
            }
        });

        rejectRadioButton.addActionListener(e -> {
            acceptRadioButton.setSelected(false);
            rejectedCount += rejectRadioButton.isSelected() ? 1 : -1;
            accepted.remove(articleVersions.get(currentArticlePosition));
            articleVersions.get(currentArticlePosition).setStatus(rejectRadioButton.isSelected() ? ArticleVersion.Status.REJECTED : ArticleVersion.Status.WAITING);
        });

        confirmButton.addActionListener(e -> checkReception());
        profileButton.addActionListener(e -> {
            Controller.getWindow().getAuthorWindow().setIdAuthor(articleVersions.get(currentArticlePosition).getAuthorProposal().getId());
            Controller.getWindow().getAuthorWindow().setAuthorWindow();
            Controller.getWindow().getAuthorWindow().setVisible(true);
        });
    }

    public void setEditorPane(JEditorPane editorPane, JEditorPane titlePane) {
        if(editorPane != null && titlePane != null) {
            this.titlePane = titlePane;
            this.editorPane = editorPane;
            updateBackButton();
            updateNextButton();
        }
    }

    public void setArticleVersions(int articleId) {
        reviewed.clear();
        accepted.clear();
        rejectedCount = 0;
        try {
            if (articleId != -1) {
                ArrayList<ArticleVersion> temp = Controller.getNotificationsText(articleId);
                if (temp != null) {
                    reviewed.clear();
                    articleVersions = temp;
                    currentArticlePosition = 0;
                    changeProposal();
                }
            } else {
                System.err.println("Articolo inesistente");
            }
        } catch (SQLException | IllegalArgumentException e) {
            ErrorDisplayer.showError(e);
        }
    }

    private void updateNextButton() {
        nextButton.setEnabled(currentArticlePosition < articleVersions.size() - 1);
    }

    private void updateBackButton() {
        backButton.setEnabled(currentArticlePosition > 0);
    }    
    
    void checkReception() {

        if (reviewed.size() == articleVersions.size()){
            if (rejectedCount + accepted.size() < reviewed.size()) {
                checkExplicitlyReviewedDialog dialog = new checkExplicitlyReviewedDialog(reviewed.size() - (accepted.size() + rejectedCount), this);
                dialog.pack();
                dialog.setVisible(true);
            } else {
                if (accepted.size() > 1) {
                    checkMultipleAcceptedDialog dialog = new checkMultipleAcceptedDialog(accepted.size(), this);
                    dialog.pack();
                    dialog.setVisible(true);
                } else {
                    try {
                        Controller.reviewArticles(articleVersions);
                    } catch (SQLException e) {
                        ErrorDisplayer.showError(e);
                    }
                    Controller.getWindow().switchPanel(Controller.getWindow().getHomePanel());
                }
            }
        } else {
            System.out.println("Devi vedere anche le altre");
            JOptionPane.showMessageDialog(null, "Impossibile procedere alla revisione. Non tutte le versioni sono state visualizate");
        }
    }
    void setLastAccepted () {
        while (accepted.size() > 1){
            accepted.getFirst().setStatus(ArticleVersion.Status.REJECTED);
            rejectedCount += 1;
        }
    }

    private void openVersion(){
        editorPane.setText(articleVersions.get(currentArticlePosition).getText());
        titlePane.setText(articleVersions.get(currentArticlePosition).getTitleProposal());
        acceptRadioButton.setSelected(articleVersions.get(currentArticlePosition).getStatus() == ArticleVersion.Status.ACCEPTED);
        rejectRadioButton.setSelected(articleVersions.get(currentArticlePosition).getStatus() == ArticleVersion.Status.REJECTED);
    }

    private void changeProposal(){
        profileButton.setText(articleVersions.get(currentArticlePosition).getAuthorProposal().getNickname());
        if(!reviewed.contains(articleVersions.get(currentArticlePosition))) {
            reviewed.add(articleVersions.get(currentArticlePosition));
        }
        openVersion();
        updateBackButton();
        updateNextButton();
    }


    void rejectUnexplicited() {
        for (ArticleVersion articleVersion : articleVersions) {
            if (articleVersion.getStatus() == ArticleVersion.Status.WAITING) {
                articleVersion.setStatus(ArticleVersion.Status.REJECTED);
                rejectedCount++;
            }
        }
        checkReception();
    }

    public JPanel getPanel() {
        return pageVersionPreviewMainPanel;
    }
}
