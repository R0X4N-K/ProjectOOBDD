package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home {


    private JButton accedi;
    private JButton registratiButton;
    private JPanel mainPanelHome;

    public Home() {
        JFrame frame = new JFrame("Home");
        frame.setContentPane(mainPanelHome);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        accedi.addActionListener(new ActionListener() {
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
    }
}
