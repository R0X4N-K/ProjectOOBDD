package gui;

import javax.swing.*;
import java.awt.*;
public class Home {


    private JButton accedi;
    private JButton registratiButton;
    private JPanel mainPanelHome;

    public Home() {

        /*accedi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                new Login();
            }
        });
        registratiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                new Registration();
            }
        });
        */
    }
    public Component getPanel() {
        return mainPanelHome;
    }
}
