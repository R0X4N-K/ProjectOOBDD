package gui.toolbar;

import model.ArticleVersion;

import javax.swing.*;

public class CompactNotification {
    private JLabel notificationLabel;
    private JButton apriButton;
    private JPanel compactNotificationMainPanel;
    private ArticleVersion articleVersion;
    private int modificationsCount = 0;

    public CompactNotification(ArticleVersion a) {
        articleVersion = a;
        incrementModificationsCount();
    }

    public void incrementModificationsCount() {
        modificationsCount++;
        notificationLabel.setText("Hai " + modificationsCount + "modifiche non valutate sull\' articolo: " + articleVersion.getParentArticle().getTitle());
    }

    public ArticleVersion getArticleVersion() {return articleVersion;}

    public JPanel getPanel() {return compactNotificationMainPanel;}
}
