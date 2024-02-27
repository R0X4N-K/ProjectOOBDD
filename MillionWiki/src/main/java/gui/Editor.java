package gui;

import javax.swing.*;

public class Editor {
    private JPanel mainPanelEditor;
    private JEditorPane editorField;
    private JButton italicButton;
    private JButton boldButton;
    private JButton colorPickerButton;
    private JMenu toolMenu;
    private JMenu Strumenti;

    public JPanel getPanel() {
        /*editorField.addCaretListener(
                new CaretListener() {
                    @Override
                    public void caretUpdate(CaretEvent e) {
                        System.out.println(editorField.getText());
                    }
                }
        );*/
        return mainPanelEditor;
    }

    private void createUIComponents() {
        toolMenu = new JMenu("Strumenti");
        toolMenu.add(new JMenuItem("Cerca"));
        toolMenu.add(new JMenuItem("Aggiungi Link"));

    }

    //TODO: aggiungere custom create per il menu a tendina
}