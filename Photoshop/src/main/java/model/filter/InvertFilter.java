package model.filter;

import model.FilterModel;
import model.filter.interfaces.Filter;

import java.awt.image.BufferedImage;

public class InvertFilter implements Filter {
    private final FilterModel filterModel;

    public InvertFilter(FilterModel filterModel) {
        this.filterModel = filterModel;
    }

    public BufferedImage applyFilter(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int oldRGB = image.getRGB(x, y);
                int a = oldRGB >> 24 & 0xFF;

                int newR = 255 - oldRGB >> 16 & 0xFF;
                int newG = 255 - oldRGB >> 8 & 0xFF;
                int newB = 255 - oldRGB & 0xFF;

                int newRGB = (a << 24) | (newR << 16) | (newG << 8) | newB;

                result.setRGB(x, y, newRGB);
            }
        }

        return result;
    }
}
