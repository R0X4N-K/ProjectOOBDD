package gui.toolbar;
import controller.Controller;
import gui.Page;
import gui.Window;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Toolbar {
    private JPanel commonToolbar;
    private JButton homeBtn;
    private JTextField searchTxtFld;
    private JPanel accessUserPanel;
    private JPanel mainPanelToolbar;
    private JPanel uncommonToolbar;
    private UnloggedToolbar UnloggedToolbar;
    private LoggedToolbar LoggedToolbar;
    private JButton switchUnloggedLoggedButton;
    private JButton switchToUnloggedButton;
    private JButton createPageButton;
    private Window window;

    public Toolbar() {
        if (Controller.checkLoggedUser()) {
            switchPanel(LoggedToolbar.getPanel());
        } else {
            switchPanel(UnloggedToolbar.getPanel());
        }
        homeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window = Window.checkWindow(window, getPanel());
                window.switchPanel(window.getHomePanel());
            }
        });

        switchUnloggedLoggedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanel(LoggedToolbar.getPanel());
            }
        });
        switchToUnloggedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanel(UnloggedToolbar.getPanel());
            }
        });
        createPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Controller.checkLoggedUser()) {
                    window = Window.checkWindow(window, getPanel());
                    window.getPage().setEditorMode();
                    window.switchPanel(window.getPagePanel());
                    window.getPage().setIdArticle(-1);
                }
                else{
                    if((JOptionPane.showConfirmDialog(null, "Devi essere loggato, effettuare il login ?", "Non sei loggato",
                            JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE)) == 0){
                        window = Window.checkWindow(window, getPanel());
                        window.switchPanel(window.getLoginPanel());
                    }
                }
            }
        });
    }

    public void switchPanel(JPanel refPanel) {
        uncommonToolbar.removeAll();
        uncommonToolbar.add(refPanel);
        uncommonToolbar.repaint();
        uncommonToolbar.revalidate();
    }

    public void switchToLoggedToolbar() {
        switchPanel(LoggedToolbar.getPanel());
    }

    public void switchToUnloggedToolbar() {
        switchPanel(UnloggedToolbar.getPanel());
    }

    public JPanel getLoggedToolbar() {
        return LoggedToolbar.getPanel();
    }

    public void setProfile(String nickname) {
        LoggedToolbar.setNicknameProfile(nickname);
    }

    public JPanel getPanel() {
        return mainPanelToolbar;
    }

}
