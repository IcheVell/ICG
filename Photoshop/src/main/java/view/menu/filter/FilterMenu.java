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
    private JRadioButtonMenuItem oilPaintingItem;
    
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
        oilPaintingItem = new JRadioButtonMenuItem("Oil Painting");

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
        filterButtonGroup.add(oilPaintingItem);

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
        add(oilPaintingItem);
    }

    public JRadioButtonMenuItem getGrayscaleItem() {
        return grayscaleItem;
    }

    public JRadioButtonMenuItem getInvertItem() {
        return invertItem;
    }

    public JRadioButtonMenuItem getSmoothingItem() {
        return smoothingItem;
    }

    public JRadioButtonMenuItem getSharpenItem() {
        return sharpenItem;
    }

    public JRadioButtonMenuItem getEmbossItem() {
        return embossItem;
    }

    public JRadioButtonMenuItem getGammaItem() {
        return gammaItem;
    }

    public JRadioButtonMenuItem getRobertsEdgeItem() {
        return robertsEdgeItem;
    }

    public JRadioButtonMenuItem getSobelsEdgeItem() {
        return sobelsEdgeItem;
    }

    public JRadioButtonMenuItem getDitheringItem() {
        return ditheringItem;
    }

    public JRadioButtonMenuItem getOrderedDitheringItem() {
        return orderedDitheringItem;
    }

    public JRadioButtonMenuItem getWaterColorItem() {
        return waterColorItem;
    }

    public JRadioButtonMenuItem getOilPaintingItem() {
        return oilPaintingItem;
    }

    public ButtonGroup getFilterButtonGroup() {
        return filterButtonGroup;
    }
}
