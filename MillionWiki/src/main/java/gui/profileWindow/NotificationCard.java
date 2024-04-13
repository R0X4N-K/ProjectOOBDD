package gui.profileWindow;

import javax.swing.*;

public class NotificationCard {
    private JPanel notificationCardMainPanel;
    private JPanel notificationCards;
    private JPanel Reloaded;
    private JPanel Reloading;
    private JTable notificationJTable;
    private JScrollPane scrollPaneJTable;

    NotificationCard(){

    }
    public JPanel getPanel(){
        return notificationCardMainPanel;
    }
    public void switchPanel(JPanel refPanel){
        notificationCards.removeAll();
        notificationCards.add(refPanel);
        notificationCards.repaint();
        notificationCards.revalidate();
    }

    public void setNotificationCard(){

    }

    private void createUIComponents() {
        notificationJTable = new JTable();
    }

}
