package view.menu;

import models.CanvasModel;
import view.menu.about.AboutMenu;
import view.menu.file.FileMenu;
import view.menu.tools.ToolsMenu;

import javax.swing.*;
import java.awt.*;

public class MyMenuBar extends JMenuBar {
    private final FileMenu fileMenu;
    private final AboutMenu aboutMenu;
    private final ToolsMenu toolsMenu;

    public MyMenuBar() {
        fileMenu = new FileMenu();
        aboutMenu = new AboutMenu();
        toolsMenu = new ToolsMenu();

        add(fileMenu);
        add(toolsMenu);
        add(aboutMenu);

        setBackground(Color.LIGHT_GRAY);
    }

    public FileMenu getFileMenu() {
        return fileMenu;
    }

    public AboutMenu getAboutMenu() {
        return aboutMenu;
    }

    public ToolsMenu getToolsMenu() {
        return toolsMenu;
    }
}