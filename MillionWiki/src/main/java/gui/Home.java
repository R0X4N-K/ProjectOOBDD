package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home {
    private JPanel mainPanelHome;
    private JButton editorButton;

    public Home() {
        editorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getWindow().switchPanel(Controller.getWindow().getPagePanel());
            }
        });
    }

    public JPanel getPanel() {
        return mainPanelHome;
    }


}
