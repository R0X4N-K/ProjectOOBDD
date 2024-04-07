package gui.authorWindow;

import controller.Controller;

import javax.swing.*;

public class ProfileAuthorWindow {

    private JPanel profileAuthorWindowMainPanel;
    private JLabel nicknameProfileJLabel;

    public ProfileAuthorWindow(){

    }
    public JPanel getPanel() {
        return profileAuthorWindowMainPanel;
    }
    public void setProfile(){
        nicknameProfileJLabel.setText("Benvenuto, questo è il profilo di " + Controller.getNicknameAuthorById(Controller.getWindow().getAuthorWindow().getIdAuthor()));
    }



}
