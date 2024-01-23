package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home {
    private JButton accedi;
    private JButton registratiButton;
    private JPanel mainPanelHome;
    private Window window;

    public Home(Window window) {

        this.window = window;

        accedi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.switchPanel(window.getLoginPanel());
            }
        });
        registratiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.switchPanel(window.getRegistrationPanel());
            }
        });
    }
    public JPanel getPanel() {
        return mainPanelHome;
    }
}
