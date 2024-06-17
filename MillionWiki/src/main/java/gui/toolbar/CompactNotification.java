package gui.toolbar;

import controller.Controller;
import model.ArticleVersion;

import javax.swing.*;

public class CompactNotification {
    private JLabel notificationLabel;
    private JButton openButton;
    private JPanel compactNotificationMainPanel;
    private final ArticleVersion version;
    private int modificationsCount = 0;

    public CompactNotification(ArticleVersion a) throws Exception {
        try {
            version = a;
            incrementModificationsCount(a);
            if (openButton.getActionListeners().length == 0) {
                openButton.addActionListener(e -> {
                    Controller.getWindow().getPage().setReviewerMode(version.getParentArticle().getId());
                });
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public void incrementModificationsCount(ArticleVersion version) throws Exception {
        if (version != null) {
            if (version.getParentArticle().getId() == this.version.getParentArticle().getId()) {
                modificationsCount++;
                notificationLabel.setText("Hai " + modificationsCount + " modifiche non valutate sull' articolo: " + version.getParentArticle().getTitle());
            }
        } else {
            throw new Exception("versione non coincidente");
        }
    }

    public JPanel getPanel() {
        return compactNotificationMainPanel;
    }

    public ArticleVersion getArticleVersion() {
        return version;
    }

}
