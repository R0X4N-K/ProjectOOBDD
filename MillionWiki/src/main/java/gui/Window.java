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

public class Window extends JFrame {
    private final ProfileWindow profileWindow;
    private final AuthorWindow authorWindow;
    private final ArticleHistory articleHistory;
    private final ArticleHistoryTextWindow articleHistoryTextWindow;
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
        setIconImage(new ImageIcon(Window.class.getResource("/icons/logo/png/logo16px.png")).getImage());


        setContentPane(mainPanelWindow);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(1200, 500);
        setMinimumSize(new Dimension(1000, 500));
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
            }

            @Override
            public void componentShown(ComponentEvent e) {
            }

            @Override
            public void componentHidden(ComponentEvent e) {
            }
        });
    }

    public static void switchToLoggedWindow(Window window) {
        window.toolbarMainPanel.switchToLoggedToolbar();
        window.switchPanel(window.getHomePanel());
    }

    public static void switchToUnloggedWindow(Window window) {
        Controller.deleteCookie();
        window.toolbarMainPanel.switchToUnloggedToolbar();
    }

    public void switchPanel(JPanel refPanel) {

        if (!checkChangesNotSaved()) {
            windowPane.removeAll();
            windowPane.add(refPanel);
            windowPane.repaint();
            windowPane.revalidate();
        } else {
            if ((JOptionPane.showConfirmDialog(null, "Attenzione, potresti avere delle modifiche non salvate, continuare ?", "Modifiche non salvate",
                    JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE)) == 0) {
                windowPane.removeAll();
                windowPane.add(refPanel);
                windowPane.repaint();
                windowPane.revalidate();

                Controller.getWindow().getPage().setViewerMode();

            }
        }

    }

    private boolean checkChangesNotSaved() {
        return pagePanel.getMode() == Page.Mode.EDITOR;
    }

    public ProfileWindow getprofileWindow() {
        return profileWindow;
    }

    public AuthorWindow getAuthorWindow() {
        return authorWindow;
    }

    public ArticleHistory getArticleHistory() {
        return articleHistory;
    }

    public ArticleHistoryTextWindow getArticleHistoryTextWindow() {
        return articleHistoryTextWindow;
    }

    public JPanel getToolbarMainPanel() {
        return toolbarMainPanel.getPanel();
    }

    public Home getHomepage() {
        return homePanel;
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

    public Page getPage() {
        return pagePanel;
    }

    private Point calculate_spacing_articles_home() {
        int width = getWidth();
        int height = getHeight();

        int articleWidth = 200;
        int articleHeight = 150;

        int baseGap = 5;

        int residualWidth = width - (5 * articleWidth);
        int hgap = (residualWidth / 6) + (residualWidth * baseGap / 100);
        if (hgap < 0) hgap = 0;

        int vgap = (height - (4 * articleHeight)) / 5;
        if (vgap < 0) vgap = 0;

        return new Point(hgap, vgap);
    }


}
