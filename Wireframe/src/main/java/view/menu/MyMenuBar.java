package view.menu;

import view.menu.edit.EditMenu;
import view.menu.file.FileMenu;
import view.menu.help.HelpMenu;

import javax.swing.*;
import java.awt.*;

public class MyMenuBar extends JMenuBar {
    private FileMenu fileMenu;
    private EditMenu editMenu;
    private HelpMenu helpMenu;

    public MyMenuBar() {
        setBackground(Color.LIGHT_GRAY);

        fileMenu = new FileMenu();
        editMenu = new EditMenu();
        helpMenu = new HelpMenu();

        add(fileMenu);
        add(editMenu);
        add(helpMenu);
    }

    public FileMenu getFileMenu() {
        return fileMenu;
    }

    public EditMenu getEditMenu() {
        return editMenu;
    }

    public HelpMenu getHelpMenu() {
        return helpMenu;
    }
}
