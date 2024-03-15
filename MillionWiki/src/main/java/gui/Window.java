package gui;

import controller.Controller;
import gui.toolbar.Toolbar;
import model.Cookie;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    private JPanel mainPanelWindow;
    private JPanel toolbarPanel;
    private JPanel windowPane;
    private Login loginPanel;
    private Home homePanel;
    private Registration registrationPanel;
    private Page pagePanel;
    private Toolbar toolbarMainPanel;

    public Window() {
        super("Million Wiki");
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                Controller.deleteLockFile();
            }
        }, "Shutdown-thread"));

        setContentPane(mainPanelWindow);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(800, 700);
        setLocationRelativeTo(null);
        setVisible(true);
        Cookie cookie = Controller.getCookie();
        if (cookie != null) {
            switchToLoggedWindow(this, cookie);
        } else {
            switchToUnloggedWindow(this);
        }
    }

    public void switchPanel(JPanel refPanel) {
        windowPane.removeAll();
        windowPane.add(refPanel);
        windowPane.repaint();
        windowPane.revalidate();
    }

    public JPanel getToolbarMainPanel() {
        return toolbarMainPanel.getPanel();
    }

    public JPanel getHomePanel() {
        return homePanel.getPanel();
    }

    public JPanel getLoginPanel() {
        return loginPanel.getPanel();
    }

    public JPanel getRegistrationPanel() {
        return registrationPanel.getPanel();
    }

    public JPanel getEditorPanel() {
        return pagePanel.getPanel();
    }

    public static Window checkWindow(Window window, Component leaf) throws NullPointerException {
        if (leaf != null) {
            if (window == null)
                window = (Window) SwingUtilities.getAncestorOfClass(Window.class, leaf);
            return window;
        } else {
            throw new NullPointerException("Non è possibile trovare \"window\", poiché \"leaf\" è null");
        }
    }

    public static void switchToLoggedWindow(Window window) {
        window.toolbarMainPanel.setNicknameProfileNicknameJLabel(Controller.getAuthorById(Controller.getCookie().getId()).getNickname());
        window.toolbarMainPanel.switchToLoggedToolbar();
    }

    public static void switchToLoggedWindow(Window window, Cookie cookie) {
        window.toolbarMainPanel.setNicknameProfileNicknameJLabel(Controller.getAuthorById(cookie.getId()).getNickname());
        window.toolbarMainPanel.switchToLoggedToolbar();
    }

    public static void switchToUnloggedWindow(Window window) {
        window.toolbarMainPanel.switchToUnloggedToolbar();
    }

}
