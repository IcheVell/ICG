package model;

import enums.Mode;
import model.filter.interfaces.Filter;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FilterModel {
    private BufferedImage originalImage;
    private BufferedImage filteredImage;
    private int smoothingKernelSize = 3;
    private float gamma = 1;
    private int threshold = 30;
    private int quantsR = 2;
    private int quantsG = 2;
    private int quantsB = 2;
    private int orderedQuantsR = 2;
    private int orderedQuantsG = 2;
    private int orderedQuantsB = 2;
    private int oilBinsCount = 2;
    private Mode mode = Mode.ORIGINAL;
    private int rotateDegrees = 0;
    private Object renderValue = RenderingHints.VALUE_INTERPOLATION_BILINEAR;

    public FilterModel(int height, int width) {
        originalImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        filteredImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }

    public BufferedImage getOriginalImage() {
        return originalImage;
    }

    public Mode getMode() {
        return mode;
    }

    public BufferedImage getFilteredImage() {
        return filteredImage;
    }

    public int getSmoothingKernelSize() {
        return smoothingKernelSize;
    }

    public float getGamma() {
        return gamma;
    }

    public int getThreshold() {
        return threshold;
    }

    public int getQuantsR() {
        return quantsR;
    }

    public int getQuantsG() {
        return quantsG;
    }

    public int getQuantsB() {
        return quantsB;
    }

    public int getOrderedQuantsR() {
        return orderedQuantsR;
    }

    public int getOrderedQuantsG() {
        return orderedQuantsG;
    }

    public int getOrderedQuantsB() {
        return orderedQuantsB;
    }

    public int getRotateDegrees() {
        return rotateDegrees;
    }

    public Object getRenderValue() {
        return renderValue;
    }

    public int getOilBinsCount() {
        return oilBinsCount;
    }

    public void setOriginalImage(BufferedImage image) {
        this.originalImage = image;
    }

    public void setFiltered(BufferedImage image) {
        this.filteredImage = image;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public void setSmoothingKernelSize(int smoothingKernelSize) {
        this.smoothingKernelSize = smoothingKernelSize;
    }

    public void setGamma(float gamma) {
        this.gamma = gamma;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public void setQuantsR(int quants) {
        this.quantsR = quants;
    }

    public void setQuantsG(int quantsG) {
        this.quantsG = quantsG;
    }

    public void setQuantsB(int quantsB) {
        this.quantsB = quantsB;
    }

    public void setOrderedQuantsR(int orderedQuantsR) {
        this.orderedQuantsR = orderedQuantsR;
    }

    public void setOrderedQuantsG(int orderedQuantsG) {
        this.orderedQuantsG = orderedQuantsG;
    }

    public void setOrderedQuantsB(int orderedQuantsB) {
        this.orderedQuantsB = orderedQuantsB;
    }

    public void setRotateDegrees(int rotateDegrees) {
        this.rotateDegrees = rotateDegrees;
    }

    public void setRenderValue(Object renderValue) {
        this.renderValue = renderValue;
    }

    public void setOilBinsCount(int oilBinsCount) {
        this.oilBinsCount = oilBinsCount;
    }

    public void applyFilter(Filter filter) {
        filteredImage = filter.applyFilter(originalImage);
    }

    public void rotateImage() {
        double angle = Math.toRadians(rotateDegrees % 360);

        int w = originalImage.getWidth();
        int h = originalImage.getHeight();

        double cos = Math.cos(angle);
        double sin = Math.sin(angle);

        int newW = (int) Math.ceil(Math.abs(w * cos) + Math.abs(h * sin));
        int newH = (int) Math.ceil(Math.abs(w * sin) + Math.abs(h * cos));

        BufferedImage result = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        double centerX  = (w - 1) / 2.0;
        double centerY  = (h - 1) / 2.0;
        double newCenterX = (newW - 1) / 2.0;
        double newCenterY = (newH - 1) / 2.0;

        int bg = 0xFFFFFFFF;

        for (int y = 0; y < newH; y++) {
            double dy = y - newCenterY;
            for (int x = 0; x < newW; x++) {
                double dx = x - newCenterX;

                double sx =  cos * dx + sin * dy + centerX;
                double sy = -sin * dx + cos * dy + centerY;

                int ix = (int) Math.round(sx);
                int iy = (int) Math.round(sy);

                int rgb = (ix >= 0 && iy >= 0 && ix < w && iy < h) ? originalImage.getRGB(ix, iy) : bg;
                result.setRGB(x, y, rgb);                                                                                                                                                                   
            }
        }

        filteredImage = result;
    }

    public void resizeImage(int imagePanelWidth, int imagePanelHeight) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        double scaleWidth = (double) imagePanelWidth / width;
        double scaleHeight = (double) imagePanelHeight / height;
        double scale = Math.min(scaleWidth, scaleHeight);

        int newWidth = (int) (width * scale);
        int newHeight = (int) (height * scale);


        BufferedImage resized = new BufferedImage(newWidth, newHeight, originalImage.getType());
        Graphics2D g2d = resized.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, renderValue);
        g2d.drawImage(originalImage, 0, 0, newWidth, newHeight, null);

        g2d.dispose();

        filteredImage = resized;
    }
}
