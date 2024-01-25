package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home {
    private JPanel mainPanelHome;
    private Window window;

    public Home(Window window) {

        this.window = window;

    }
    public JPanel getPanel() {
        return mainPanelHome;
    }
}
