package gui.page;

import controller.Controller;
import model.Article;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;

public class PageLinker extends JPanel {
    private Thread searchThread;

    private JDialog inputLinkTxtDlg;
    private JPanel inputLinkPnl;
    private JTextField searchPageToLinkTxtFld;
    private JButton searchPageToLinkBtn;
    private JPanel articlesFoundPanel;
    private JPanel articlePreviewPanel;
    private JLabel articlePreviewTitleLbl;
    private JEditorPane articlePreviewFld;
    private JScrollPane scrollPane;
    private JPanel inputLinkButtonsPanel;


    private JButton sendBtn;
    private JButton closeBtn;


    final Article[] articleToLink = {null};

    public PageLinker() {
        init_component();
    }

    private void init_component(){
        inputLinkTxtDlg =  new JDialog();
        inputLinkTxtDlg.setTitle("Seleziona una pagina");
        inputLinkTxtDlg.setModal(true);
        inputLinkTxtDlg.setLayout(new BorderLayout());

        inputLinkPnl = new JPanel();
        inputLinkPnl.setLayout(new FlowLayout());

        searchPageToLinkTxtFld = new JTextField(20);
        searchPageToLinkBtn = new JButton("Cerca");

        articlesFoundPanel = new JPanel();
        articlesFoundPanel.setLayout(new BoxLayout(articlesFoundPanel, BoxLayout.Y_AXIS));

        articlePreviewPanel = new JPanel();
        articlePreviewPanel.setLayout(new BorderLayout());

        articlePreviewTitleLbl = new JLabel("Preview");
        articlePreviewFld = new JEditorPane();
        articlePreviewFld.setEditable(false);
        articlePreviewFld.setPreferredSize(new Dimension(320, 640));
        articlePreviewFld.setContentType("text/html");
        articlePreviewPanel.add(articlePreviewTitleLbl, BorderLayout.NORTH);
        articlePreviewPanel.add(articlePreviewFld, BorderLayout.CENTER);

        scrollPane = new JScrollPane(articlePreviewFld);
        articlePreviewPanel.add(scrollPane);

        inputLinkButtonsPanel = new JPanel();
        sendBtn = new JButton("Salva link");
        sendBtn.setEnabled(false);
        closeBtn = new JButton("Scarta");

        sendBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputLinkTxtDlg.dispose();
                Controller.getWindow().getPage().insertHTML("LINK", null, articleToLink[0].getTitle(), articleToLink[0].getId());
            }
        });

        closeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputLinkTxtDlg.dispose();
            }
        });

        inputLinkButtonsPanel.add(sendBtn);
        inputLinkButtonsPanel.add(closeBtn);


        searchPageToLinkBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (searchThread == null || !searchThread.isAlive()) {
                    searchThread = new Thread(() -> {
                        createSearchedPages();
                    }); // Crea un nuovo thread se il precedente è terminato
                    searchThread.setDaemon(true);
                    searchThread.start();
                }
            }
        });

        inputLinkPnl.add(searchPageToLinkTxtFld);
        inputLinkPnl.add(searchPageToLinkBtn);
        inputLinkPnl.setVisible(true);

        inputLinkTxtDlg.add(inputLinkPnl, BorderLayout.NORTH);
        inputLinkTxtDlg.add(articlesFoundPanel, BorderLayout.CENTER);
        inputLinkTxtDlg.add(articlePreviewPanel, BorderLayout.EAST);
        inputLinkTxtDlg.add(new JScrollPane(
                articlesFoundPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        ), BorderLayout.CENTER);
        inputLinkTxtDlg.add(inputLinkButtonsPanel, BorderLayout.SOUTH);

        inputLinkTxtDlg.setSize(640, 640);
        inputLinkTxtDlg.setResizable(false);
        inputLinkTxtDlg.setLocationRelativeTo(null);
        inputLinkTxtDlg.setVisible(true);
    }

    private void createSearchedPages(){
        ArrayList<Article> articlesFound = Controller.getMatchesArticlesByTitle(
                searchPageToLinkTxtFld.getText());

        if(articlesFound.isEmpty()){
        }
        else{
            articlesFoundPanel.removeAll();
            for(Article article : articlesFound){
                JPanel articleFoundItemPnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
                articleFoundItemPnl.setMaximumSize(new Dimension(320, 25));
                JLabel articleFoundItemLbl = new JLabel(article.getTitle());
                articleFoundItemPnl.add(articleFoundItemLbl);
                articleFoundItemPnl.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        sendBtn.setEnabled(true);
                        articlePreviewFld.setText(
                                Controller.getLastArticleVersionByArticleId(
                                        article.getId()
                                ).getText()
                        );

                        for (int i = 0 ; i < articlesFoundPanel.getComponentCount(); i++) {
                            articlesFoundPanel.getComponent(i).setBackground(null);
                            JPanel panel = (JPanel)  articlesFoundPanel.getComponent(i);
                            for (Component innerComponent : panel.getComponents()) {
                                if (innerComponent instanceof JLabel) {
                                    JLabel label = (JLabel) innerComponent;
                                    label.setForeground(null);
                                }
                            }
                        }
                        articleFoundItemPnl.setBackground(Color.decode("#007bff"));
                        articleFoundItemLbl.setForeground(Color.WHITE);
                        articleToLink[0] = article;
                    }
                });
                articlesFoundPanel.add(articleFoundItemPnl);
            }
            articlesFoundPanel.revalidate();
            articlesFoundPanel.repaint();
        }
    }

    public JButton getSendBtn() {
        return sendBtn;
    }

    public JDialog getInputLinkTxtDlg() {
        return inputLinkTxtDlg;
    }

    public Article[] getArticleToLink() {
        return articleToLink;
    }


}
