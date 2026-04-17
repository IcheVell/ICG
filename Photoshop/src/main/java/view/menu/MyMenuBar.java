package view.menu;

import view.menu.help.HelpMenu;
import view.menu.file.FileMenu;
import view.menu.filter.FilterMenu;
import view.menu.modify.ModifyMenu;
import view.menu.view.ViewMenu;

import javax.swing.*;
import java.awt.*;

public class MyMenuBar extends JMenuBar {
    private final FileMenu fileMenu;
    private final ViewMenu viewMenu;
    private final ModifyMenu modifyMenu;
    private final FilterMenu filterMenu;
    private final HelpMenu helpMenu;

    public MyMenuBar() {
        fileMenu = new FileMenu();
        viewMenu = new ViewMenu();
        filterMenu = new FilterMenu();
        modifyMenu = new ModifyMenu(filterMenu.getFilterButtonGroup());
        helpMenu = new HelpMenu();

        setBackground(Color.LIGHT_GRAY);

        add(fileMenu);
        add(viewMenu);
        add(modifyMenu);
        add(filterMenu);
        add(helpMenu);
    }

    public FileMenu getFileMenu() {
        return fileMenu;
    }

    public FilterMenu getFilterMenu() {
        return filterMenu;
    }

    public ViewMenu getViewMenu() {
        return viewMenu;
    }

    public ModifyMenu getModifyMenu() {
        return modifyMenu;
    }

    public HelpMenu getHelpMenu() {
        return helpMenu;
    }
}
