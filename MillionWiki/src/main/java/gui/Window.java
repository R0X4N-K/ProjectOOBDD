package gui;

import controller.Controller;
import gui.articleHistory.ArticleHistory;
import gui.articleHistory.ArticleHistoryTextWindow;
import gui.authorWindow.AuthorWindow;
import gui.homepage.Home;
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
import java.net.URL;

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
    private final AuthorWindow authorWindow;
    private final ArticleHistory articleHistory;
    private final ArticleHistoryTextWindow articleHistoryTextWindow;

    public Window() {
        super("Million Wiki");
        setIconImage(new ImageIcon(Window.class.getResource("/icons/logo/png/logo16px.png")).getImage());

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                Controller.deleteLockFile();
            }
        }, "Shutdown-thread"));





        setContentPane(mainPanelWindow);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(1200, 500);
        setMinimumSize(new Dimension(480, 500));
        setLocationRelativeTo(null);
        profileWindow = new ProfileWindow(this);
        authorWindow = new AuthorWindow(this);
        articleHistory = new ArticleHistory(this);
        articleHistoryTextWindow = new ArticleHistoryTextWindow(this);
        setVisible(true);
        profileWindow.setVisible(false);
        authorWindow.setVisible(false);
        articleHistory.setVisible(false);
        articleHistoryTextWindow.setVisible(false);
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
                homePanel.getHomeRecentArticles().getPanel().setLayout(
                        new GridLayout(2, 5, calculate_spacing_articles_home().x, calculate_spacing_articles_home().y)
                );
                homePanel.getHomeFeaturedArticles().getPanel().setLayout(
                        new GridLayout(2, 5, calculate_spacing_articles_home().x, calculate_spacing_articles_home().y)
                );
                homePanel.getHomeRecentArticles().getPanel().revalidate();
                homePanel.getHomeRecentArticles().getPanel().repaint();

                homePanel.getHomeFeaturedArticles().getPanel().revalidate();
                homePanel.getHomeFeaturedArticles().getPanel().repaint();
            }
            @Override
            public void componentMoved(ComponentEvent e) {
                toolbarMainPanel.updateSearchDialogPos();
            }
            @Override
            public void componentShown(ComponentEvent e) {}
            @Override
            public void componentHidden(ComponentEvent e) {}
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
    public AuthorWindow getAuthorWindow(){ return authorWindow; }
    public ArticleHistory getArticleHistory() { return articleHistory; }
    public ArticleHistoryTextWindow getArticleHistoryTextWindow() { return articleHistoryTextWindow; }


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

    public static void switchToLoggedWindow(Window window) {
        window.toolbarMainPanel.switchToLoggedToolbar();
        window.switchPanel(window.getHomePanel());
    }

    public static void switchToUnloggedWindow(Window window) {
        Controller.deleteCookie();
        window.toolbarMainPanel.switchToUnloggedToolbar();
    }

    private Point calculate_spacing_articles_home(){
        int width = getWidth();
        int height = getHeight();
        int propWidth = 1200;
        int propHeigth = 700;


        int hgap = (((propWidth * width) / 10000) - 50);

        int vgap =   (propHeigth * hgap) / 10000;

        return new Point(hgap, vgap);
    }

}
