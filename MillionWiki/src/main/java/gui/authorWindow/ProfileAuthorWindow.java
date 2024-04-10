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
        nicknameProfileJLabel.setText("Benvenuto, questo è il profilo di " + Controller.getNicknameAuthorById(Controller.getWindow().getAuthorWindow().getIdAuthor()));
        createdArticlesJLabel.setText("Ha creato " + Controller.getArticlesNumberByIdAuthor(Controller.getWindow().getAuthorWindow().getIdAuthor()) + " articoli");
        sentProposalsJLabel.setText("Ha inviato " + Controller.getArticlesNumberSentByIdAuthor(Controller.getWindow().getAuthorWindow().getIdAuthor()) + " proposte");
        float rating = Controller.getRatingByAuthorId(Controller.getWindow().getAuthorWindow().getIdAuthor());
        if (rating !=0){
            ratingJLabel.setText("Il suo rating (Proposte Accettate/Inviate) è del" + rating * 100 + "%");
        } else {
            ratingJLabel.setText("Nessuna proposta ancora valutata");
        }
        switchPanel(cardReloaded);
    }



}
