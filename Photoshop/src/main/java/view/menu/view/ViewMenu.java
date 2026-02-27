package view.menu.view;

import javax.swing.*;

public class ViewMenu extends JMenu {
    private JRadioButtonMenuItem originalImageItem;
    private JRadioButtonMenuItem filteredImageItem;

    public ViewMenu() {
        setText("View");
        ButtonGroup buttonGroup = new ButtonGroup();

        originalImageItem = new JRadioButtonMenuItem("Original");
        originalImageItem.setSelected(true);
        filteredImageItem = new JRadioButtonMenuItem("Filtered");

        buttonGroup.add(originalImageItem);
        buttonGroup.add(filteredImageItem);

        add(originalImageItem);
        add(filteredImageItem);
    }

    public JRadioButtonMenuItem getOriginalImageItem() {
        return originalImageItem;
    }

    public JRadioButtonMenuItem getFilteredImageItem() {
        return filteredImageItem;
    }
}
