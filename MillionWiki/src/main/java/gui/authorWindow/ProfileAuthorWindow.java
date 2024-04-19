package gui.authorWindow;

import controller.Controller;

import javax.swing.*;

public class ProfileAuthorWindow {

    private JPanel profileAuthorWindowMainPanel;
    private JLabel nicknameProfileJLabel;
    private JPanel profileAuthorCards;
    private JPanel cardReloaded;
    private JPanel cardReloading;
    private JLabel createdArticlesJLabel;
    private JLabel sentProposalsJLabel;
    private JLabel ratingJLabel;

    public ProfileAuthorWindow(){

    }
    public JPanel getPanel() {
        return profileAuthorWindowMainPanel;
    }
    public void switchPanel(JPanel refPanel) {
        profileAuthorCards.removeAll();
        profileAuthorCards.add(refPanel);
        profileAuthorCards.repaint();
        profileAuthorCards.revalidate();
    }
    public void setProfile(){
        switchPanel(cardReloading);
        nicknameProfileJLabel.setText("<html> Benvenuto, questo è il profilo di " + "<b>" + Controller.getNicknameAuthorById(Controller.getWindow().getAuthorWindow().getIdAuthor()) + "</b>" + "</html>");
        createdArticlesJLabel.setText("<html> Ha creato " + "<b>" + Controller.getArticlesNumberByIdAuthor(Controller.getWindow().getAuthorWindow().getIdAuthor()) + "</b>" + " articoli" + "</html>");
        sentProposalsJLabel.setText("<html> Ha inviato " + "<b>" +Controller.getArticlesNumberSentByIdAuthor(Controller.getWindow().getAuthorWindow().getIdAuthor()) + "</b>" + " proposte" + "</html>");
        float rating = Controller.getRatingByAuthorId(Controller.getWindow().getAuthorWindow().getIdAuthor());
        if (rating !=0){
            ratingJLabel.setText("<html> Il suo rating (Proposte Accettate/Inviate) è del " + "<b>" +rating * 100 + " %" + "</b>" + "</html>");
        } else {
            ratingJLabel.setText("Nessuna proposta ancora valutata");
        }
        switchPanel(cardReloaded);
    }



}
