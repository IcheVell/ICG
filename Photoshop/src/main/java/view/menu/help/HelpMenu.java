package view.menu.help;

import javax.swing.*;

public class HelpMenu extends JMenu {
    private final JMenuItem aboutMenuItem;

    public HelpMenu() {
        setText("Help");
        aboutMenuItem = new JMenuItem("About");

        add(aboutMenuItem);
    }

    public JMenuItem getHelpMenuItem() {
        return aboutMenuItem;
    }
}
