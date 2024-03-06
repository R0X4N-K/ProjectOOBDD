package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Window extends JFrame{
    private JPanel mainPanelWindow;
    private JPanel toolbarPanel;
    private JPanel windowPane;
    private Login loginPanel;
    private Home homePanel;
    private Registration registrationPanel;
    private Editor editorPanel;

    public Window(){
        super("Million Wiki");
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                Controller.deleteLockFile();
            }
        });
        setContentPane(mainPanelWindow);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(800, 700);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void switchPanel(JPanel refPanel){
        windowPane.removeAll();
        windowPane.add(refPanel);
        windowPane.repaint();
        windowPane.revalidate();
    }

    public JPanel getHomePanel() {
        return homePanel.getPanel();
    }
    public JPanel getLoginPanel(){
        return loginPanel.getPanel();
    }
    public JPanel getRegistrationPanel(){
        return registrationPanel.getPanel();
    }
    public JPanel getEditorPanel(){
        return editorPanel.getPanel();
    }

    public static Window checkWindow(Window window, Component leaf) throws NullPointerException {
        if (leaf != null) {
            if (window == null)
                window = (Window) SwingUtilities.getAncestorOfClass(Window.class, leaf);
            return window;
        }
        else {
            throw new NullPointerException("Non è possibile trovare \"window\", poiché \"leaf\" è null");
        }
    }


}
