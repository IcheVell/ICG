package view.toolspanel;

import javax.swing.*;
import java.awt.*;

public class ToolPanel extends JPanel {
    JButton filtersButton;
    JButton modifyButton;
    JPopupMenu filterPopupMenu;
    JPopupMenu modifyPopupMenu;
    JRadioButtonMenuItem grayscaleFilterItem;
    private JRadioButtonMenuItem invertFilterItem;
    private JRadioButtonMenuItem smoothingFilterItem;
    private JRadioButtonMenuItem sharpenFilterItem;
    private JRadioButtonMenuItem embossFilterItem;
    private JRadioButtonMenuItem gammaFilterItem;
    private JRadioButtonMenuItem robertsEdgeFilterItem;
    private JRadioButtonMenuItem sobelsEdgeFilterItem;
    private JRadioButtonMenuItem ditheringFilterItem;
    private JRadioButtonMenuItem orderedDitheringFilterItem;
    private JRadioButtonMenuItem waterColorFilterItem;
    private JRadioButtonMenuItem rotateModifyItem;
    private JRadioButtonMenuItem resizeModifyItem;

    public ToolPanel() {
        filtersButton = new JButton("Filters");
        filtersButton.setFocusable(false);
        filtersButton.setBackground(Color.GRAY);

        filterPopupMenu = new JPopupMenu();

        ButtonGroup filterGroup = new ButtonGroup();

        grayscaleFilterItem = new JRadioButtonMenuItem("Grayscale");
        invertFilterItem = new JRadioButtonMenuItem("Invert");
        smoothingFilterItem = new JRadioButtonMenuItem("Smoothing");
        sharpenFilterItem = new JRadioButtonMenuItem("Sharpen");
        embossFilterItem = new JRadioButtonMenuItem("Emboss");
        gammaFilterItem = new JRadioButtonMenuItem("Gamma");
        robertsEdgeFilterItem = new JRadioButtonMenuItem("Roberts Edge");
        sobelsEdgeFilterItem = new JRadioButtonMenuItem("Sobels Edge");
        ditheringFilterItem = new JRadioButtonMenuItem("Dithering");
        orderedDitheringFilterItem = new JRadioButtonMenuItem("Ordered Dithering");
        waterColorFilterItem = new JRadioButtonMenuItem("Water Color");

        filterGroup.add(grayscaleFilterItem);
        filterGroup.add(invertFilterItem);
        filterGroup.add(smoothingFilterItem);
        filterGroup.add(sharpenFilterItem);
        filterGroup.add(embossFilterItem);
        filterGroup.add(gammaFilterItem);
        filterGroup.add(robertsEdgeFilterItem);
        filterGroup.add(sobelsEdgeFilterItem);
        filterGroup.add(ditheringFilterItem);
        filterGroup.add(orderedDitheringFilterItem);
        filterGroup.add(waterColorFilterItem);

        filterPopupMenu.add(grayscaleFilterItem);
        filterPopupMenu.add(invertFilterItem);
        filterPopupMenu.add(smoothingFilterItem);
        filterPopupMenu.add(sharpenFilterItem);
        filterPopupMenu.add(embossFilterItem);
        filterPopupMenu.add(gammaFilterItem);
        filterPopupMenu.add(robertsEdgeFilterItem);
        filterPopupMenu.add(sobelsEdgeFilterItem);
        filterPopupMenu.add(ditheringFilterItem);
        filterPopupMenu.add(orderedDitheringFilterItem);
        filterPopupMenu.add(waterColorFilterItem);

        modifyButton = new JButton("Modify");
        modifyButton.setFocusable(false);
        modifyButton.setBackground(Color.GRAY);

        modifyPopupMenu = new JPopupMenu();

        rotateModifyItem = new JRadioButtonMenuItem("Rotate");
        resizeModifyItem = new JRadioButtonMenuItem("Resize");

        modifyPopupMenu.add(rotateModifyItem);
        modifyPopupMenu.add(resizeModifyItem);

        filterGroup.add(rotateModifyItem);
        filterGroup.add(resizeModifyItem);

        setBackground(Color.LIGHT_GRAY);
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 2));
        add(filtersButton);
        add(modifyButton);
    }

    public JButton getFiltersButton() {
        return filtersButton;
    }

    public JPopupMenu getFilterPopupMenu() {
        return filterPopupMenu;
    }

    public JButton getModifyButton() {
        return modifyButton;
    }

    public JPopupMenu getModifyPopupMenu() {
        return modifyPopupMenu;
    }

    public JRadioButtonMenuItem getGrayscaleFilterItem() {
        return grayscaleFilterItem;
    }

    public JRadioButtonMenuItem getInvertFilterItem() {
        return invertFilterItem;
    }

    public JRadioButtonMenuItem getSmoothingFilterItem() {
        return smoothingFilterItem;
    }

    public JRadioButtonMenuItem getSharpenFilterItem() {
        return sharpenFilterItem;
    }

    public JRadioButtonMenuItem getEmbossFilterItem() {
        return embossFilterItem;
    }

    public JRadioButtonMenuItem getGammaFilterItem() {
        return gammaFilterItem;
    }

    public JRadioButtonMenuItem getRobertsEdgeFilterItem() {
        return robertsEdgeFilterItem;
    }

    public JRadioButtonMenuItem getSobelsEdgeFilterItem() {
        return sobelsEdgeFilterItem;
    }

    public JRadioButtonMenuItem getDitheringFilterItem() {
        return ditheringFilterItem;
    }

    public JRadioButtonMenuItem getOrderedDitheringFilterItem() {
        return orderedDitheringFilterItem;
    }

    public JRadioButtonMenuItem getWaterColorFilterItem() {
        return waterColorFilterItem;
    }

    public JRadioButtonMenuItem getRotateModifyItem() {
        return rotateModifyItem;
    }

    public JRadioButtonMenuItem getResizeModifyItem() {
        return resizeModifyItem;
    }
}
