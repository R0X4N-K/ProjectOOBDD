package gui.toolbar;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Profile {
    private JPanel profilePanel;
    private JButton creaArticoloButton;
    private JLabel NicknameJLabel;

    public Profile(){
        creaArticoloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
    public void setNicknameJLabel(String nickname) {
        NicknameJLabel.setText(nickname);
    }
    public JPanel getPanel() {
        return profilePanel;
    }
}
