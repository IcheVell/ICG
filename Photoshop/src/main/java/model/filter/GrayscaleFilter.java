package model.filter;

import model.FilterModel;
import model.filter.interfaces.Filter;

import java.awt.image.BufferedImage;

public class GrayscaleFilter implements Filter {
    private final FilterModel filterModel;

    public GrayscaleFilter(FilterModel filterModel) {
        this.filterModel = filterModel;
    }

    public BufferedImage applyFilter(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();

        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int oldRGB = img.getRGB(x, y);

                int alpha = oldRGB >> 24 & 0xFF;
                int r = oldRGB >> 16 & 0xFF;
                int g = oldRGB >> 8 & 0xFF;
                int b = oldRGB & 0xFF;

                int gray = (int) (0.299 * r + 0.587 * g + 0.114 * b);

                int newRGB = (alpha << 24) | (gray << 16) | (gray << 8) | gray;

                result.setRGB(x, y, newRGB);
            }
        }

        return result;
    }
}
