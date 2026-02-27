package view.menu.filter;

import view.toolspanel.ToolPanel;

import javax.swing.*;

public class FilterMenu extends JMenu {
    private JRadioButtonMenuItem grayscaleItem;
    private JRadioButtonMenuItem invertItem;
    private JRadioButtonMenuItem smoothingItem;
    private JRadioButtonMenuItem sharpenItem;
    private JRadioButtonMenuItem embossItem;
    private JRadioButtonMenuItem gammaItem;
    private JRadioButtonMenuItem robertsEdgeItem;
    private JRadioButtonMenuItem sobelsEdgeItem;
    private JRadioButtonMenuItem ditheringItem;
    private JRadioButtonMenuItem orderedDitheringItem;
    private JRadioButtonMenuItem waterColorItem;
    
    private ButtonGroup filterButtonGroup;

    public FilterMenu() {
        setText("Filter");

        filterButtonGroup = new ButtonGroup();

        grayscaleItem = new JRadioButtonMenuItem("Grayscale");
        invertItem = new JRadioButtonMenuItem("Invert");
        smoothingItem = new JRadioButtonMenuItem("Smoothing");
        sharpenItem = new JRadioButtonMenuItem("Sharpen");
        embossItem = new JRadioButtonMenuItem("Emboss");
        gammaItem = new JRadioButtonMenuItem("Gamma");
        robertsEdgeItem = new JRadioButtonMenuItem("Robert Edge");
        sobelsEdgeItem = new JRadioButtonMenuItem("Sobel Edge");
        ditheringItem = new JRadioButtonMenuItem("Dithering");
        orderedDitheringItem = new JRadioButtonMenuItem("Ordered Dithering");
        waterColorItem = new JRadioButtonMenuItem("Water Color");

        filterButtonGroup.add(grayscaleItem);
        filterButtonGroup.add(invertItem);
        filterButtonGroup.add(smoothingItem);
        filterButtonGroup.add(sharpenItem);
        filterButtonGroup.add(embossItem);
        filterButtonGroup.add(gammaItem);
        filterButtonGroup.add(robertsEdgeItem);
        filterButtonGroup.add(sobelsEdgeItem);
        filterButtonGroup.add(ditheringItem);
        filterButtonGroup.add(orderedDitheringItem);
        filterButtonGroup.add(waterColorItem);

        add(grayscaleItem);
        add(invertItem);
        add(smoothingItem);
        add(sharpenItem);
        add(embossItem);
        add(gammaItem);
        add(robertsEdgeItem);
        add(sobelsEdgeItem);
        add(ditheringItem);
        add(orderedDitheringItem);
        add(waterColorItem);
    }

    public JMenuItem getGrayscaleItem() {
        return grayscaleItem;
    }

    public JMenuItem getInvertItem() {
        return invertItem;
    }

    public JMenuItem getSmoothingItem() {
        return smoothingItem;
    }

    public JMenuItem getSharpenItem() {
        return sharpenItem;
    }

    public JMenuItem getEmbossItem() {
        return embossItem;
    }

    public JMenuItem getGammaItem() {
        return gammaItem;
    }

    public JMenuItem getRobertsEdgeItem() {
        return robertsEdgeItem;
    }

    public JMenuItem getSobelsEdgeItem() {
        return sobelsEdgeItem;
    }

    public JMenuItem getDitheringItem() {
        return ditheringItem;
    }

    public JMenuItem getOrderedDitheringItem() {
        return orderedDitheringItem;
    }

    public JMenuItem getWaterColorItem() {
        return waterColorItem;
    }

    public ButtonGroup getFilterButtonGroup() {
        return filterButtonGroup;
    }
}
