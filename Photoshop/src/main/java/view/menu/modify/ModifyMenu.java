package view.menu.modify;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ModifyMenu extends JMenu {
    private JRadioButtonMenuItem rotateImageItem;
    private JRadioButtonMenuItem resizeImageItem;

    public ModifyMenu(ButtonGroup buttonGroup) {
        setText("Modify");

        rotateImageItem = new JRadioButtonMenuItem("Rotate");
        resizeImageItem = new JRadioButtonMenuItem("Resize");

        buttonGroup.add(rotateImageItem);
        buttonGroup.add(resizeImageItem);

        add(rotateImageItem);
        add(resizeImageItem);
    }

    public JRadioButtonMenuItem getRotateImageItem() {
        return rotateImageItem;
    }

    public JRadioButtonMenuItem getResizeImageItem() {
        return resizeImageItem;
    }
}
