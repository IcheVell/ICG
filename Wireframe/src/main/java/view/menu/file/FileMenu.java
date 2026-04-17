package view.menu.file;

import javax.swing.*;

public class FileMenu extends JMenu {
    private final JMenuItem openItem;
    private final JMenuItem saveItem;

    public FileMenu() {
        setText("File");

        openItem = new JMenuItem("Open");
        saveItem = new JMenuItem("Save");

        add(openItem);
        add(saveItem);
    }

    public JMenuItem getOpenItem() {
        return openItem;
    }

    public JMenuItem getSaveItem() {
        return saveItem;
    }
}
