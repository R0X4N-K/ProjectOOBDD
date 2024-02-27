package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home {
    private JPanel mainPanelHome;
    private JButton editorButton;
    private Window window;

    public Home() {
        editorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window = Window.checkWindow(window, mainPanelHome);
                window.switchPanel(window.getEditorPanel());
            }
        });
    }
    public JPanel getPanel() {
        return mainPanelHome;
    }


}
