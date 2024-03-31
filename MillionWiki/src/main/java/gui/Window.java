package gui;

import controller.Controller;
import gui.page.Page;
import gui.profileWindow.ProfileWindow;
import gui.session.Login;
import gui.session.Registration;
import gui.toolbar.Toolbar;
import model.Cookie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class Window extends JFrame {
    private JPanel mainPanelWindow;
    private JPanel toolbarPanel;
    private JPanel windowPane;
    private Login loginPanel;
    private Home homePanel;
    private Registration registrationPanel;
    private Page pagePanel;
    private Toolbar toolbarMainPanel;
    private final ProfileWindow profileWindow;

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
        setSize(1200, 700);
        setMinimumSize(new Dimension(480, 480));
        setLocationRelativeTo(null);
        profileWindow = new ProfileWindow(this);
        setVisible(true);
        profileWindow.setVisible(false);
        Cookie cookie = Controller.getCookie();
        if (cookie != null) {
            switchToLoggedWindow(this);
        } else {
            switchToUnloggedWindow(this);
        }


        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                toolbarMainPanel.updateSearchDialogPos();
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                toolbarMainPanel.updateSearchDialogPos();
            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });

    }

    public void switchPanel(JPanel refPanel) {
        windowPane.removeAll();
        windowPane.add(refPanel);
        windowPane.repaint();
        windowPane.revalidate();
    }

    public ProfileWindow getprofileWindow() {
        return profileWindow;
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

    public JPanel getPagePanel() {
        return pagePanel.getPanel();
    }
    public Page getPage(){
        return pagePanel;
    }

    /*public static void switchToLoggedWindow(Window window) {
        window.toolbarMainPanel.setProfile(Controller.getAuthorById(Controller.getCookie().getId()).getNickname());
        window.toolbarMainPanel.switchToLoggedToolbar();
    }*/

    public static void switchToLoggedWindow(Window window) {
        window.toolbarMainPanel.switchToLoggedToolbar();
        window.switchPanel(window.getHomePanel());
    }

    public static void switchToUnloggedWindow(Window window) {
        Controller.deleteCookie();
        window.toolbarMainPanel.switchToUnloggedToolbar();
    }

}
