package view.imagepanel;

import enums.Mode;
import model.FilterModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagePanel extends JPanel {
    private FilterModel filterModel;

    public ImagePanel() {
        setLayout(new BorderLayout());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        BufferedImage image = filterModel.getMode() == Mode.ORIGINAL ? filterModel.getOriginalImage() : filterModel.getFilteredImage();

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2d.drawImage(image, 0, 0, null);

        g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{10, 5}, 0));
        g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);

        g2d.dispose();
    }

    public void setFilterModel(FilterModel filterModel) {
        this.filterModel = filterModel;
    }

    @Override
    public Dimension getPreferredSize() {
        if (filterModel.getMode() == Mode.ORIGINAL) {
            return new Dimension(filterModel.getOriginalImage().getWidth(), filterModel.getOriginalImage().getHeight());
        }

        return new Dimension(filterModel.getFilteredImage().getWidth(), filterModel.getFilteredImage().getHeight());
    }
}
