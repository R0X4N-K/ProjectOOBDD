package gui.page.VersionRevision;

import controller.Controller;
import model.ArticleVersion;

import javax.swing.*;
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
    private int rejectedCount = 0;
    private int acceptedCount = 0;

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
            rejectRadioButton.setSelected(false);
            acceptedCount += acceptRadioButton.isSelected() ? 1 : -1;
            rejectedCount -= articleVersions.get(currentArticlePosition).getStatus() == ArticleVersion.Status.REJECTED ? 1 : 0;
            articleVersions.get(currentArticlePosition).setStatus(acceptRadioButton.isSelected() ? ArticleVersion.Status.ACCEPTED : ArticleVersion.Status.WAITING);
        });

        rejectRadioButton.addActionListener(e -> {
            acceptRadioButton.setSelected(false);
            rejectedCount += rejectRadioButton.isSelected() ? 1 : -1;
            acceptedCount -= articleVersions.get(currentArticlePosition).getStatus() == ArticleVersion.Status.ACCEPTED ? 1 : 0;
            articleVersions.get(currentArticlePosition).setStatus(rejectRadioButton.isSelected() ? ArticleVersion.Status.REJECTED : ArticleVersion.Status.WAITING);
        });

        confirmButton.addActionListener(e -> checkReception());
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
        if (articleId != -1) {
            ArrayList<ArticleVersion> temp = Controller.getNotificationsText(articleId);
            if (temp != null) {
                reviewed.clear();
                articleVersions = temp;
                currentArticlePosition = 0;
                changeProposal();
            }
        } else {
            System.err.println("PUPÙ");
        }
    }

    private void updateNextButton() {
        nextButton.setEnabled(currentArticlePosition < articleVersions.size() - 1);
    }

    private void updateBackButton() {
        backButton.setEnabled(currentArticlePosition > 0);
    }    
    
    private void checkReception() {

        if (reviewed.size() == articleVersions.size()){
            if (rejectedCount + acceptedCount < reviewed.size()) {
                checkExplicitlyReviewedDialog dialog = new checkExplicitlyReviewedDialog(reviewed.size() - (acceptedCount + rejectedCount), this);
                dialog.pack();
                dialog.setVisible(true);
            } else {
                if (acceptedCount > 1) {
                    checkMultipleAcceptedDialog dialog = new checkMultipleAcceptedDialog(acceptedCount, this);
                    dialog.pack();
                    dialog.setVisible(true);
                } else {
                    revisionCheck();
                }
            }
        } else {
            System.out.println("Devi vedere anche le altre");
        }
    }
    void setLastAccepted () {
        int i = 0;
        for (ArticleVersion articleVersion : articleVersions) {
            if (articleVersion.getStatus() == ArticleVersion.Status.ACCEPTED) {
                if (i < acceptedCount) {
                    articleVersion.setStatus(ArticleVersion.Status.REJECTED);
                    i++;
                }
            }
        }

    }

    void revisionCheck() {
        if (reviewed.size() == articleVersions.size()) {
            if (acceptedCount > 1) {
                System.out.println("NON PUOI ACCETTARE PIÙ ARTICOLI IN CONTEMPORANEA");
            } else {
                Controller.reviewArticles(articleVersions);
                Controller.getWindow().switchPanel(Controller.getWindow().getHomePanel());
            }
        } else {
            System.out.println("Devi vedere anche le altre");
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

    /*private void updateCounters() {
        reviewed = 0;
        acceptedCount = 0;
        for (ArticleVersion articleVersion : articleVersions) {
            acceptedCount += articleVersion.getStatus() == ArticleVersion.Status.ACCEPTED ? 1 : 0;
            reviewed += articleVersion.getStatus() != ArticleVersion.Status.WAITING ? 1 : 0;
        }
    }*/
    void rejectUnexplicited() {
        for (ArticleVersion articleVersion : articleVersions) {
            if (articleVersion.getStatus() == ArticleVersion.Status.WAITING) {
                articleVersion.setStatus(ArticleVersion.Status.REJECTED);
            }
        }
    }

    public JPanel getPanel() {
        return pageVersionPreviewMainPanel;
    }
}
