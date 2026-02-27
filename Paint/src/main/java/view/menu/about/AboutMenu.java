package view.menu.about;

import javax.swing.*;
import java.awt.*;

public class AboutMenu extends JMenu {
    private final JMenuItem aboutDeveloperItem;
    private final JMenuItem aboutProgramItem;

    public AboutMenu() {
        aboutDeveloperItem = new JMenuItem("About developer");
        aboutProgramItem = new JMenuItem("About program");

        setText("Help");

        add(aboutDeveloperItem);
        add(aboutProgramItem);
    }

    public JMenuItem getAboutDeveloperItem() {
        return aboutDeveloperItem;
    }

    public JMenuItem getAboutProgramItem() {
        return aboutProgramItem;
    }
}
