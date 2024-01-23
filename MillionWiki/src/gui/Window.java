package gui;

import javax.swing.*;

public class Window extends JFrame{
    private JPanel mainPanelWindow;
    private JPanel toolbarPanel;
    private JPanel windowPane;
    private JPanel homePanel;
    private JPanel loginPanel;
    private JPanel registrationPanel;

    public Window(){
        JFrame frame = new JFrame("Million Wiki");
        frame.setContentPane(mainPanelWindow);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void createUIComponents() {
        toolbarPanel= new Toolbar(this).getPanel();
        homePanel = new Home(this).getPanel();
        loginPanel = new Login(this).getPanel();
        registrationPanel =  new Registration(this).getPanel();
    }
    public void switchPanel(JPanel refPanel){
        windowPane.removeAll();
        windowPane.add(refPanel);
        windowPane.repaint();
        windowPane.revalidate();
    }

    public JPanel getHomePanel() {
        return this.homePanel;
    }
    public JPanel getLoginPanel(){
        return this.loginPanel;
    }
    public JPanel getRegistrationPanel(){
        return this.registrationPanel;
    }

}
