package gui;
import javax.swing.*;
import java.awt.*;
public class Toolbar {
    private JPanel mainToolbar;
    private JButton homeButton;
    private JTextField textField1;
    private JPanel unloggedUserPanel;
    private JButton loginButton;
    private JButton registerButton;
    private JPanel Toolbar;
    private Window window;
    public Toolbar() {
    }
    public Component getPanel() {
        return mainToolbar;
    }
}
