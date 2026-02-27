package view.menu.file;

import javax.swing.*;

public class FileMenu extends JMenu {
    private final JMenuItem openMenuItem;
    private final JMenuItem saveMenuItem;

    public FileMenu() {
        setText("File");
        openMenuItem = new JMenuItem("Open");
        saveMenuItem = new JMenuItem("Save");

        add(openMenuItem);
        add(saveMenuItem);
    }

    public JMenuItem getOpenMenuItem() {
        return openMenuItem;
    }

    public JMenuItem getSaveMenuItem() {
        return saveMenuItem;
    }
}
