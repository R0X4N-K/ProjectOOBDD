package gui;

import javax.swing.*;

public class Window {
    private JPanel mainPanelWindow;
    private JPanel toolbarPanel;
    private JPanel windowPane;

    public Window(){
        JFrame frame = new JFrame("Million Wiki");
        frame.setContentPane(mainPanelWindow);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
