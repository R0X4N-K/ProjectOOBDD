package gui.Toolbar;
import gui.Window;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Toolbar {
    private JPanel commonToolbar;
    private JButton homeBtn;
    private JTextField searchTxtFld;
    private JPanel accessUserPanel;
    private JPanel mainPanelToolbar;
    private Window window;
    public Toolbar() {
        homeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window = Window.checkWindow(window, getPanel());
                window.switchPanel(window.getHomePanel());
            }
        });

    }
    public JPanel getPanel() {
        return mainPanelToolbar;
    }
}
