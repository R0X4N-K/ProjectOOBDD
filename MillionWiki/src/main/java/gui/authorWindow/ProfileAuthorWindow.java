package gui.authorWindow;

import controller.Controller;
import gui.ErrorDisplayer;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.sql.SQLException;

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
        try {
            nicknameProfileJLabel.setText(Controller.getNicknameAuthorById(Controller.getWindow().getAuthorWindow().getIdAuthor()));
            nicknameProfileJLabel.setFont(new Font(
                    nicknameProfileJLabel.getFont().getFontName(),
                    Font.BOLD,
                    22)
            );
            nicknameProfileJLabel.setBorder(
                    new TitledBorder("Nome utente")
            );
        } catch (SQLException e) {
            ErrorDisplayer.showError(e);
            nicknameProfileJLabel.setText("!ERRORE DI VISUALIZZAZIONE!");
        }

        try {
            createdArticlesJLabel.setText(String.valueOf(Controller.getArticlesNumberByIdAuthor(Controller.getWindow().getAuthorWindow().getIdAuthor())));
            createdArticlesJLabel.setFont(new Font(
                    nicknameProfileJLabel.getFont().getFontName(),
                    Font.BOLD,
                    22)
            );
            createdArticlesJLabel.setBorder(
                    new TitledBorder("Articoli creati")
            );
        } catch (SQLException e) {
            ErrorDisplayer.showError(e);
            createdArticlesJLabel.setText("Impossibile visualizzare il numero di articoli creati");
        }

        try {
            sentProposalsJLabel.setText(String.valueOf(Controller.getArticlesNumberSentByIdAuthor(Controller.getWindow().getAuthorWindow().getIdAuthor())));
            sentProposalsJLabel.setFont(new Font(
                    nicknameProfileJLabel.getFont().getFontName(),
                    Font.BOLD,
                    22)
            );
            sentProposalsJLabel.setBorder(
                    new TitledBorder("Proposte inviate")
            );
        } catch (SQLException e) {
            ErrorDisplayer.showError(e);
            sentProposalsJLabel.setText("Impossibile visualizzare il numero di proposte");
        }
        try {
            float rating = Controller.getRatingByAuthorId(Controller.getWindow().getAuthorWindow().getIdAuthor());
            if (rating != 0) {
                ratingJLabel.setText(rating * 100 + " %");
                ratingJLabel.setFont(new Font(
                        nicknameProfileJLabel.getFont().getFontName(),
                        Font.BOLD,
                        22)
                );
                ratingJLabel.setBorder(
                        new TitledBorder("Valutazione")
                );
            } else {
                ratingJLabel.setText("Nessuna proposta ancora valutata");
            }
        } catch (SQLException e) {
            ErrorDisplayer.showError(e);
            ratingJLabel.setText("!ERRORE DI VISUALIZZAZIONE!");
        }
        switchPanel(cardReloaded);
    }



}
