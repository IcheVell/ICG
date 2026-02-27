package view.menu.file;

import view.drawpanel.DrawPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;

public class FileMenu extends JMenu {
    private final JMenuItem newItem;
    private final JMenuItem openItem;
    private final JMenuItem saveItem;

    public FileMenu() {
        setText("File");

        newItem = new JMenuItem("New");
        saveItem = new JMenuItem("Save");
        openItem = new JMenuItem("Open");

        add(newItem);
        add(saveItem);
        add(openItem);
    }

    public JMenuItem getNewItem() {
        return newItem;
    }

    public JMenuItem getOpenItem() {
        return openItem;
    }

    public JMenuItem getSaveItem() {
        return saveItem;
    }
}
