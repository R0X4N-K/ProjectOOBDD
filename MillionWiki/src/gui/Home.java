package gui;

import javax.swing.*;

public class Home {
    private JButton accedi;
    private JButton registratiButton;
    private JPanel mainPanelHome;
    private Window window;

    public Home(Window window) {

        this.window = window;

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
    public JPanel getPanel() {
        return mainPanelHome;
    }
}
