package gui.authorWindow;

import controller.Controller;
import gui.ErrorDisplayer;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private JLabel loadingLbl;
    private JProgressBar progressBar;

    private JPanel ratingPnl;

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
            createdArticlesJLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    for (ActionListener listener : Controller.getWindow().getAuthorWindow().getButtonToCreatedPages().getActionListeners()) {
                        listener.actionPerformed(new ActionEvent(Controller.getWindow().getAuthorWindow().getButtonToCreatedPages(), ActionEvent.ACTION_PERFORMED, "Trigger from AuthorWindow"));
                    }
                }
            });
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
            ratingJLabel.setFont(new Font(
                    nicknameProfileJLabel.getFont().getFontName(),
                    Font.BOLD,
                    22)
            );
            ratingPnl.setBorder(
                    new TitledBorder("Valutazione")
            );

            ratingJLabel.setOpaque(true);

            if (rating != 0) {
                rating = rating * 100;
                progressBar.setValue((int) rating);
                progressBar.setVisible(true);
                ratingJLabel.setText(String.format("%.1f", rating) + " %");

                if(rating > 50){
                    ratingJLabel.setForeground(Color.decode("#5EAC24"));
                    ratingJLabel.setIcon(
                            new ImageIcon(
                                    ProfileAuthorWindow.class.getResource(
                                            "/icons/up.png"
                                    )
                            )
                    );
                    progressBar.setForeground(Color.decode("#5EAC24"));
                    progressBar.setBackground(Color.GRAY);

                }else if(rating < 50){
                    ratingJLabel.setForeground(Color.decode("#ED1C24"));
                    ratingJLabel.setIcon(
                            new ImageIcon(
                                    ProfileAuthorWindow.class.getResource(
                                            "/icons/down.png"
                                    )
                            )
                    );

                    progressBar.setForeground(Color.decode("#ED1C24"));
                    progressBar.setBackground(Color.GRAY);
                }else if(rating == 50){
                    ratingJLabel.setForeground(Color.decode("#FDBF40"));
                    ratingJLabel.setIcon(
                            new ImageIcon(
                                    ProfileAuthorWindow.class.getResource(
                                            "/icons/middle.png"
                                    )
                            )
                    );

                    progressBar.setForeground(Color.decode("#FDBF40"));
                    progressBar.setBackground(Color.GRAY);
                }

            } else {
                ratingJLabel.setForeground(Color.GRAY);
                ratingJLabel.setText("...");
                ratingJLabel.setIcon(null);
                progressBar.setVisible(false);
            }
        } catch (SQLException e) {
            ErrorDisplayer.showError(e);
            ratingJLabel.setText("!ERRORE DI VISUALIZZAZIONE!");
        }
        switchPanel(cardReloaded);
    }


    public JPanel getRatingPnl() {
        return ratingPnl;
    }


}
